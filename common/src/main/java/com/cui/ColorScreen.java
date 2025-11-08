package com.cui;

import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.awt.Color;

public class ColorScreen extends Screen {
    private final Screen parent;

    private float h; // 0..1
    private float s; // 0..1
    private float v; // 0..1
    private float a; // 0..1

    public ColorScreen(Screen parent) {
        super(Component.literal("Color Config (HSV + Alpha)"));
        this.parent = parent;

        float[] hsv = Color.RGBtoHSB(
                (int) (CUI.cuiConfig.r * 255),
                (int) (CUI.cuiConfig.g * 255),
                (int) (CUI.cuiConfig.b * 255),
                null
        );
        this.h = hsv[0];
        this.s = hsv[1];
        this.v = hsv[2];
        this.a = CUI.cuiConfig.a;
    }

    @Override
    public void onClose() {
        CUI.cuiConfig.saveConfig();
        this.minecraft.setScreen(parent);
    }

    @Override
    protected void init() {
        int x = 40;
        int y = 40;
        int width = 200;
        int height = 20;
        int spacing = 25;

        // Hue (0..360)
        this.addRenderableWidget(new AbstractSliderButton(x, y, width, height, Component.literal("Hue"), h) {
            @Override
            protected void updateMessage() {
                this.setMessage(Component.literal("Hue: " + (int)(value * 360)));
            }

            @Override
            protected void applyValue() {
                h = (float) value;
                updateRGB();
            }
        });

        // Saturation (0..1)
        this.addRenderableWidget(new AbstractSliderButton(x, y + spacing, width, height, Component.literal("Saturation"), s) {
            @Override
            protected void updateMessage() {
                this.setMessage(Component.literal(String.format("Saturation: %.2f", value)));
            }

            @Override
            protected void applyValue() {
                s = (float) value;
                updateRGB();
            }
        });

        // Value (Brightness) (0..1)
        this.addRenderableWidget(new AbstractSliderButton(x, y + spacing * 2, width, height, Component.literal("Value"), v) {
            @Override
            protected void updateMessage() {
                this.setMessage(Component.literal(String.format("Value: %.2f", value)));
            }

            @Override
            protected void applyValue() {
                v = (float) value;
                updateRGB();
            }
        });

        // Alpha (0..1)
        this.addRenderableWidget(new AbstractSliderButton(x, y + spacing * 3, width, height, Component.literal("Alpha"), a) {
            @Override
            protected void updateMessage() {
                this.setMessage(Component.literal(String.format("Alpha: %.2f", value)));
            }

            @Override
            protected void applyValue() {
                a = (float) value;
                updateRGB();
            }
        });

        // Back
        this.addRenderableWidget(Button.builder(Component.literal("Back"), btn -> onClose())
                .bounds(x, y + spacing * 4, width, height).build());
    }

    private void updateRGB() {
        int rgb = Color.HSBtoRGB(h, s, v);
        CUI.cuiConfig.r = ((rgb >> 16) & 0xFF) / 255f;
        CUI.cuiConfig.g = ((rgb >> 8) & 0xFF) / 255f;
        CUI.cuiConfig.b = (rgb & 0xFF) / 255f;
        CUI.cuiConfig.a = a;
    }
}
