package com.cui.core.config;

import com.cui.core.CUI;
import dev.architectury.platform.Platform;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Random;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;


public class CUI_Config {
    public float a;
    public float r;
    public float g;
    public float b;
    public float desaturation;
    public boolean enableButton;

    public Color color;

    public CUI_Config(){}

    public void loadConfig() throws IOException {
        // CP !!!11!!!1
        Path cp = Platform.getConfigFolder();
        Path c = cp.resolve("cui_config.toml");

        if (Files.exists(c)) {

            CommentedFileConfig fileConfig = CommentedFileConfig.builder(c)
                    .concurrent()
                    .build();

            fileConfig.load();

            System.out.println("CUI CONFIG EXISTS");

            Optional<String> rgb = fileConfig.getOptional("color");
            Optional<String> alpha = fileConfig.getOptional("alpha");
            Optional<String> enableButton = fileConfig.getOptional("button");
            Optional<String> desaturation = fileConfig.getOptional("desaturation");

            color = randomPastel();

            try {
                this.r = rgb.map((hex) -> Integer.parseInt(hex.substring(0, 2), 16) / 255.0f).orElse(1.0f);
            }
            catch(Exception exception) {
                this.r = (float) color.getRed() / 255;
                saveConfig();
                CUI.LOGGER.info(exception.getMessage());
            }

            try {
                this.g = rgb.map((hex) -> Integer.parseInt(hex.substring(2, 4), 16) / 255.0f).orElse(1.0f);
            }
            catch(Exception exception) {
                this.g = (float) color.getGreen() / 255;
                saveConfig();
                CUI.LOGGER.info(exception.getMessage());
            }

            try {
                this.b = rgb.map((hex) -> Integer.parseInt(hex.substring(4, 6), 16) / 255.0f).orElse(1.0f);
            }
            catch(Exception exception) {
                this.b = (float) color.getBlue() / 255;
                saveConfig();
                CUI.LOGGER.info(exception.getMessage());
            }

            try {
                this.a = alpha.map(Float::parseFloat).orElse(1.0f);
            }
            catch(Exception exception) {
                this.a = (float) color.getAlpha() / 255;
                saveConfig();
                CUI.LOGGER.info(exception.getMessage());
            }

            try {
                this.enableButton = enableButton.map(Boolean::parseBoolean).orElse(true);
            }
            catch(Exception exception) {
                this.enableButton = true;
                saveConfig();
                CUI.LOGGER.info(exception.getMessage());
            }

            try {

                this.desaturation = desaturation.map(Float::parseFloat).orElse(1.5f);
            }
            catch(Exception exception) {
                this.desaturation = 1.5f;
                saveConfig();
                CUI.LOGGER.info(exception.getMessage());
            }

            this.color = new Color(r, g, b, a);
        } else {
            System.out.println("CUI CONFIG NOT EXISTS");

            color = randomPastel();

            this.r = (float) color.getRed() / 255;
            this.g = (float) color.getGreen() / 255;
            this.b = (float) color.getBlue() / 255;
            this.a = (float) color.getAlpha() / 255;
            this.enableButton = true;
            this.desaturation = 1.5f;
        }
    }

    public void saveConfig() {
        Path c = Platform.getConfigFolder().resolve("cui_config.toml");

        try {
            String hexColor = String.format("%02X%02X%02X", (int) (this.r * 255), (int) (this.g * 255), (int) (this.b * 255));

            try (BufferedWriter writer = Files.newBufferedWriter(c)) {
                writer.write("# CUI config\n");
                writer.write("color = \"" + hexColor + "\"\n");
                writer.write("alpha = \"" + a + "\"\n");
                writer.write("desaturation = \"" + desaturation + "\"\n");
                writer.write("button = \"" + enableButton + "\"\n");
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

    public int getRGBA() {
        return (this.color.getAlpha() << 24) | (this.color.getRed() << 16) | (this.color.getGreen() << 8) | this.color.getBlue();
    }

    public int getTextColor(int originalColor) {
        float[] hsv = Color.RGBtoHSB((int) (CUI.cuiConfig.r * 255.0f), (int) (CUI.cuiConfig.g * 255.0f), (int) (CUI.cuiConfig.b * 255.0f), null);
        float avg = (((originalColor) & 0xFF) + ((originalColor >> 8) & 0xFF) + ((originalColor >> 16) & 0xFF)) / 3.0f;

        return ((Color.getHSBColor(hsv[0], hsv[1] / desaturation, avg / 255.0f).getRGB()) & 0x00FFFFFF) | (((originalColor >> 24) & 0xFF) << 24);
    }
}