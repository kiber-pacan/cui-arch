package com.cui.config;

import com.cui.CUI;
import com.mojang.blaze3d.platform.NativeImage;
import dev.architectury.platform.Platform;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Random;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.UnmodifiableConfig;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.ResourceLocation;


public class CUI_Config {
    public float a;
    public float r;
    public float g;
    public float b;

    public Color color;

    private CommentedFileConfig fileConfig;

    public CUI_Config(){}

    public void loadConfig() throws IOException {
        // CP !!!11!!!1
        Path cp = Platform.getConfigFolder();
        Path c = cp.resolve("cui_config.toml");

        if (Files.exists(c)) {

            this.fileConfig = CommentedFileConfig.builder(c)
                    .concurrent()
                    .build();

            this.fileConfig.load();

            System.out.println("EXISTS");

            Optional<String> rgb = this.fileConfig.getOptional("color");
            Optional<Double> alpha = this.fileConfig.getOptional("alpha");

            boolean isrgb = rgb.isPresent();
            boolean isalpha = alpha.isPresent();

            if (isrgb) {
                try {
                    String hex = rgb.get();
                    this.r = (float) Integer.valueOf(hex.substring(0, 2), 16) / 255;
                    this.g = (float) Integer.valueOf(hex.substring(2, 4), 16) / 255;
                    this.b = (float) Integer.valueOf(hex.substring(4, 6), 16) / 255;
                } catch (Exception e) {
                    System.err.println("CUI Config: Invalid color format, using defaults.");
                    Color defaultColor = randomPastel();
                    this.r = (float) defaultColor.getRed() / 255;
                    this.g = (float) defaultColor.getGreen() / 255;
                    this.b = (float) defaultColor.getBlue() / 255;
                }
            }

            if (isalpha) {
                this.a = alpha.get().floatValue();
            } else {
                this.a = 0.75f;
            }

            if (isrgb && isalpha) {
                color = new Color(this.r, this.g, this.b, this.a);
            } else if (isrgb && !isalpha) {
                color = new Color(this.r, this.g, this.b);
            } else if (!isrgb && isalpha) {
                color = new Color(this.a, this.a, this.a, this.a);
            }
        } else {
            System.out.println("NOT EXISTS");

            color = randomPastel();

            this.r = (float) color.getRed() / 255;
            this.g = (float) color.getGreen() / 255;
            this.b = (float) color.getBlue() / 255;
            this.a = (float) color.getAlpha() / 255;
        }
    }

    public void saveConfig() {
        Path c = Platform.getConfigFolder().resolve("cui_config.toml");

        try {
            String hexColor = String.format("%02X%02X%02X", (int) (this.r * 255), (int) (this.g * 255), (int) (this.b * 255));

            try (BufferedWriter writer = Files.newBufferedWriter(c)) {
                writer.write("# CUI config\n");
                writer.write("color = \"" + hexColor + "\"\n");
                writer.write("alpha = " + a);
            }

            System.out.println("Config saved to " + c.toAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Color randomPastel() {
        Random random = new Random();
        int r = random.nextInt(50, 201);
        int g = random.nextInt(50, 201);
        int b = random.nextInt(50, 201);
        return new Color(r, g, b, 191);
    }

    public int getRGB() {
        return (0xFF << 24) | (this.color.getRed() << 16) | (this.color.getGreen() << 8) | this.color.getBlue();
    }
}