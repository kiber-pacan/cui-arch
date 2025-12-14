package com.cui.mixin.client.screen.misc;


import com.cui.core.CUI;
import net.minecraft.client.gui.GuiGraphics;

import net.minecraft.client.gui.screens.Screen;

import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

#if MC_VER < V1_21
import net.minecraft.client.gui.screens.SimpleOptionsSubScreen;
#endif

import java.awt.*;


@Mixin(#if MC_VER < V1_21 SimpleOptionsSubScreen.class #else Minecraft.class #endif)
public abstract class SimpleOptionsScreenMixin #if MC_VER < V1_21  extends Screen #endif {
    #if MC_VER < V1_21
    protected SimpleOptionsScreenMixin(Component title) {
        super(title);
    }

    @Inject(at = @At(value = "HEAD"), method = "render")
    private void render3(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        float[] hsv = CUI.cuiConfig.getHSV();
        guiGraphics.fillGradient(0, 0, width, height,
                ((int)(CUI.cuiConfig.a * 255) << 24) | (Color.getHSBColor(hsv[0], hsv[1], hsv[2] / 2.7f).getRGB() & 0x00FFFFFF),
                ((int)(CUI.cuiConfig.a * 255) << 24) | (Color.getHSBColor(hsv[0], hsv[1], hsv[2] / 5.0f).getRGB() & 0x00FFFFFF)
        );
    }
    #endif
}
