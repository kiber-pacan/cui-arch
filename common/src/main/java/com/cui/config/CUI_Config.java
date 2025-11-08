package com.cui.config;

import dev.architectury.platform.Platform;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Random;

import org.tomlj.Toml;
import org.tomlj.TomlParseResult;

public class CUI_Config {
    public float a;
    public float r;
    public float g;
    public float b;

    public CUI_Config(){}

    public void loadConfig() throws IOException {
        // CP !!!11!!!1
        Path cp = Platform.getConfigFolder();
        Path c = cp.resolve("cui_config.toml");

        if (Files.exists(c)) {
            TomlParseResult result = Toml.parse(c);

            if (result.hasErrors()) {
                result.errors().forEach(error -> System.err.println(error.toString()));
                return;
            }

            Optional<String> rgb = Optional.ofNullable(result.getString("color"));
            if (rgb.isPresent()) {
                this.r = (float) Integer.valueOf(rgb.get().substring(0, 2), 16) / 255;
                this.g = (float) Integer.valueOf(rgb.get().substring(2, 4), 16) / 255;
                this.b = (float) Integer.valueOf(rgb.get().substring(4, 6), 16) / 255;
                this.a = (float) Integer.valueOf(rgb.get().substring(6, 8), 16) / 255;
            }
        } else {
            Color color = randomPastel();

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
                writer.write("color = " + a);
            }

            System.out.println("Config saved to " + c.toAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Color randomPastel() {
        Random random = new Random();
        int r = random.nextInt(50,200);
        int g = random.nextInt(50,200);
        int b = random.nextInt(50,200);
        return new Color(r, g, b, 0.75f);
    }
}
