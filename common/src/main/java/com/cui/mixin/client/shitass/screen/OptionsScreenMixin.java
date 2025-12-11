package com.cui.mixin.client.shitass.screen;


import com.cui.core.CUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.tabs.TabNavigationBar;

import net.minecraft.client.gui.screens.Screen;

import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

#if MC_VER < V1_21
import net.minecraft.client.gui.screens.OptionsSubScreen;
import net.minecraft.client.gui.screens.SimpleOptionsSubScreen;
#endif

import java.awt.*;


@Mixin(#if MC_VER < V1_21 SimpleOptionsSubScreen.class #else Minecraft.class #endif)
public abstract class OptionsScreenMixin #if MC_VER < V1_21  extends Screen #endif {
    #if MC_VER < V1_21
    protected OptionsScreenMixin(Component title) {
        super(title);
    }

    @Inject(at = @At(value = "HEAD"), method = "render")
    private void render3(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        float[] hsv = Color.RGBtoHSB((int) (CUI.cuiConfig.r * 255.0f), (int) (CUI.cuiConfig.g * 255.0f), (int) (CUI.cuiConfig.b * 255.0f), null);
        guiGraphics.fillGradient(0, 0, width, height,
                ((int)(CUI.cuiConfig.a * 255) << 24) | (Color.getHSBColor(hsv[0], hsv[1], hsv[2] / 2.7f).getRGB() & 0x00FFFFFF),
                ((int)(CUI.cuiConfig.a * 255) << 24) | (Color.getHSBColor(hsv[0], hsv[1], hsv[2] / 5.0f).getRGB() & 0x00FFFFFF)
        );
    }
    #endif
}
