package com.cui.mixin.client.post1_21_6.misc;

import com.cui.CUI;
import net.minecraft.client.gui.components.AbstractSliderButton;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.awt.*;

// Slider widget

@Mixin(#if MC_VER >= V1_21_6 AbstractSliderButton.class #else Minecraft.class #endif)
public class SliderMixin {
    #if MC_VER >= V1_21_6
    @ModifyArg(method = "renderWidget", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/ARGB;color(FI)I"), index = 1)
    private int injected(int color) {
        return ((AbstractSliderButton)(Object)this).active ? CUI.mixColors(-1, CUI.cuiConfig.getRGB()) : CUI.mixColors(-6250336, CUI.cuiConfig.getRGB());
    }

    @ModifyArg(method = "renderWidget", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/ResourceLocation;IIIII)V"), index = 6)
    private int injected1(int c) {
        Color c1 = CUI.cuiConfig.color;
        return ((c >> 24) & 0xFF) << 24 | (c1.getRed() & 0xFF) << 16 | (c1.getGreen() & 0xFF) << 8 | (c1.getBlue() & 0xFF);
    }
    #endif
}
