package com.cui.mixin.client.post1_21_6.misc;

import com.cui.CUI;
#if MC_VER >= V1_21_6 import com.mojang.blaze3d.pipeline.RenderPipeline; #endif
import net.minecraft.client.gui.GuiGraphics;
#if MC_VER >= V1_21_4 import net.minecraft.client.gui.components.AbstractScrollArea; #endif
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.minecraft.client.Minecraft;

// Scroller element in screens

@Mixin(#if MC_VER >= V1_21_6 AbstractScrollArea.class #else Minecraft.class #endif)
public class ScrollerMixin {
    #if MC_VER >= V1_21_6
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/ResourceLocation;IIII)V"), method = "renderScrollbar")
    private static void injected(GuiGraphics instance, RenderPipeline pipeline, ResourceLocation sprite, int x, int y, int width, int height) {
        instance.blitSprite(pipeline, sprite, x, y, width, height, CUI.cuiConfig.getRGB());
    }
    #endif
}
