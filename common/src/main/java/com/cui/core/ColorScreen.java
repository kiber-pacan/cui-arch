package com.cui.core;

import com.cui.abs.core.data.ResourceBridge;
import com.cui.abs.core.rendering.data.RenderPipelineBridge;
import com.cui.abs.core.rendering.gui.GuiRenderer;
import com.cui.abs.core.data.Pair;
import com.cui.abs.core.data.Rectangle;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.layouts.*;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.screens.Screen;
#if MC_VER >= V1_21_3
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.RenderType;
#endif
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;

public class ColorScreen extends Screen {
    private final Screen parent;

    private static final Component TITLE = Component.literal("CUI config");

    private float h; // 0..1
    private float s; // 0..1
    private float v; // 0..1
    private float a; // 0..1

    private float desaturation;

    private final HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this, 61, 33);

    public ColorScreen(Screen parent) {
        super(TITLE);
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
        this.desaturation = CUI.cuiConfig.desaturation;
    }

    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        GuiRenderer.blitSprite(
                guiGraphics,
                "GUI_TEXTURED",
                ResourceBridge.withDefaultNamespace("test"),
                new Rectangle(20, 100, 32, 32),
                CUI.cuiConfig.getRGB()
        );
    }

    @Override
    public void onClose() {
        CUI.cuiConfig.saveConfig();
        this.minecraft.setScreen(parent);
    }

    @Override
    protected void init() {
        LinearLayout linearLayout = this.layout.addToHeader(#if MC_VER >= V1_20_4 LinearLayout.vertical().spacing(8) #else new LinearLayout(308, 20, LinearLayout.Orientation.HORIZONTAL) #endif);
        //linearLayout.addChild(new StringWidget(TITLE, this.font), LayoutSettings::alignHorizontallyCenter);

        net.minecraft.client.gui.layouts.GridLayout gridLayout = new GridLayout();
        gridLayout.defaultCellSetting().paddingHorizontal(4).paddingBottom(4).alignHorizontallyCenter();
        GridLayout.RowHelper rowHelper = gridLayout.createRowHelper(1);

        int width = 150;
        int height = 20;

        // Hue (0..360)
        rowHelper.addChild(new AbstractSliderButton(0, 0, width, height, Component.literal("Hue"), h) {
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
        rowHelper.addChild(new AbstractSliderButton(0, 0, width, height, Component.literal("Saturation"), s) {
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
        rowHelper.addChild(new AbstractSliderButton(0, 0, width, height, Component.literal("Value"), v) {
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
        rowHelper.addChild(new AbstractSliderButton(0, 0, width, height, Component.literal("Alpha"), a) {
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

        // Desaturation (1..2)
        rowHelper.addChild(new AbstractSliderButton(0, 0, width, height, Component.literal("Text desaturation"), desaturation - 1) {
            @Override
            protected void updateMessage() {
                this.setMessage(Component.literal(String.format("Text desaturation: %.2f", value + 1)));
            }

            @Override
            protected void applyValue() {
                desaturation = (float) (value + 1);
                updateRGB();
            }
        });


        // Enable button
        /*
        rowHelper.addChild(
                Checkbox.builder(Component.literal("Enable CUI button"), this.font)
                        #if MC_VER >= V1_21 .maxWidth(width) #endif
                        .selected(CUI.cuiConfig.enableButton)
                        .onValueChange((cb, state) -> {
                            CUI.cuiConfig.enableButton = state;
                            CUI.cuiConfig.saveConfig();
                        })
                        .build());

         */

        this.layout.addToContents(gridLayout);
        this.layout.addToFooter(
                Button.builder(Component.literal("Done"), b -> this.onClose()).width(200).build()
        );
        this.layout.visitWidgets(this::addRenderableWidget);

        this.repositionElements();
    }

    @Override
    protected void repositionElements() {
        this.layout.arrangeElements();
    }


    private void updateRGB() {
        int rgb = Color.HSBtoRGB(h, s, v);
        CUI.cuiConfig.r = ((rgb >> 16) & 0xFF) / 255f;
        CUI.cuiConfig.g = ((rgb >> 8) & 0xFF) / 255f;
        CUI.cuiConfig.b = (rgb & 0xFF) / 255f;
        CUI.cuiConfig.a = a;
        CUI.cuiConfig.desaturation = desaturation;

        CUI.cuiConfig.color = new Color(CUI.cuiConfig.r, CUI.cuiConfig.g, CUI.cuiConfig.b, CUI.cuiConfig.a);
    }
}
