package com.cui.mixin.client.misc;

import com.cui.CUI;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

#if MC_VER >= V1_21_3
import net.minecraft.client.renderer.RenderType;
#endif

import java.awt.*;
import java.util.function.Function;

// Slider widget

@Mixin(AbstractSliderButton.class)
public class SliderMixin {
    #if MC_VER >= V1_21_6
    @ModifyArg(method = "renderWidget", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/ARGB;color(FI)I"), index = 1)
    private int injected(int color) {
        return ((AbstractSliderButton)(Object)this).active ? CUI.mixColors(-1, CUI.cuiConfig.getRGB()) : CUI.mixColors(-6250336, CUI.cuiConfig.getRGB());
    }
    #else
    #if MC_VER >= V1_21_3
    @Redirect(method = "renderWidget", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIIII)V"))
    private void injected(GuiGraphics instance, Function<ResourceLocation, RenderType> renderTypeGetter, ResourceLocation sprite, int x, int y, int width, int height, int blitOffset) {
        Color color = new Color(CUI.mixColors(-1, CUI.cuiConfig.getRGB()));

        #if MC_VER >= V1_21_3
        instance.flush();
        RenderSystem.setShaderColor(CUI.cuiConfig.r, CUI.cuiConfig.g, CUI.cuiConfig.b, 1);
        #endif
        #if MC_VER <= V1_21_1 instance.setColor(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, 1); #endif

        instance.blitSprite(#if MC_VER >= V1_21_3 renderTypeGetter, #endif sprite, x, y, width, height, blitOffset);

        #if MC_VER >= V1_21_3
        instance.flush();
        RenderSystem.setShaderColor(1, 1, 1, 1);
        #endif
        #if MC_VER <= V1_21_1 instance.setColor(1, 1, 1, 1); #endif
    }
    #else
    @Redirect(method = "renderWidget", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lnet/minecraft/resources/ResourceLocation;IIII)V"))
    private void injected(GuiGraphics instance, ResourceLocation sprite, int x, int y, int width, int height) {
        Color color = new Color(CUI.mixColors(-1, CUI.cuiConfig.getRGB()));

        #if MC_VER >= V1_21_3
        instance.flush();
        RenderSystem.setShaderColor(CUI.cuiConfig.r, CUI.cuiConfig.g, CUI.cuiConfig.b, 1);
        #endif
        #if MC_VER <= V1_21_1 instance.setColor(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, 1); #endif

        instance.blitSprite(#if MC_VER >= V1_21_3 renderTypeGetter, #endif sprite, x, y, width, height);

        #if MC_VER >= V1_21_3
        instance.flush();
        RenderSystem.setShaderColor(1, 1, 1, 1);
        #endif
        #if MC_VER <= V1_21_1 instance.setColor(1, 1, 1, 1); #endif    }
    #endif
    #endif
}
