package com.cui.mixin.client.post1_21_6.misc;

import com.cui.CUI;
#if MC_VER >= V1_21_6 import com.mojang.blaze3d.pipeline.RenderPipeline; #endif
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.TooltipRenderUtil;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.minecraft.client.Minecraft;

import java.util.function.Function;

// Coloring tool tip (hover thingy)

@Mixin(TooltipRenderUtil.class)
public class TooltipMixin {

    #if MC_VER >= V1_21_6
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/ResourceLocation;IIII)V", ordinal = 1), method = "renderTooltipBackground")
    private static void injected(GuiGraphics instance, RenderPipeline pipeline, ResourceLocation sprite, int x, int y, int width, int height) {
        instance.blitSprite(pipeline, sprite, x, y, width, height, CUI.cuiConfig.getRGB());
    }
    #else
    #if MC_VER >= V1_21_3
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIII)V"), method = "renderTooltipBackground")
    private static void injected(GuiGraphics instance, Function<ResourceLocation, RenderType> renderTypeGetter, ResourceLocation sprite, int x, int y, int width, int height) {
        instance.blitSprite(renderTypeGetter, sprite, x, y, width, height, CUI.cuiConfig.getRGB());
    }
    #else
    @Shadow
    private static void renderFrameGradient(GuiGraphics guiGraphics, int x, int y, int width, int height, int z, int topColor, int bottomColor) {
    }

    @Shadow
    private static void renderVerticalLine(GuiGraphics guiGraphics, int x, int y, int length, int z, int color) {
    }

    @Shadow
    private static void renderVerticalLineGradient(GuiGraphics guiGraphics, int x, int y, int length, int z, int topColor, int bottomColor) {
    }

    @Shadow
    private static void renderHorizontalLine(GuiGraphics guiGraphics, int x, int y, int length, int z, int color) {
    }

    @Shadow
    private static void renderRectangle(GuiGraphics guiGraphics, int x, int y, int width, int height, int z, int color) {
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/tooltip/TooltipRenderUtil;renderHorizontalLine(Lnet/minecraft/client/gui/GuiGraphics;IIIII)V"), method = "renderTooltipBackground")
    private static void injected1(GuiGraphics guiGraphics, int x, int y, int length, int z, int color) {
        renderHorizontalLine(guiGraphics, x, y, length, z, CUI.cuiConfig.getRGB());
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/tooltip/TooltipRenderUtil;renderVerticalLine(Lnet/minecraft/client/gui/GuiGraphics;IIIII)V"), method = "renderTooltipBackground")
    private static void injected2(GuiGraphics guiGraphics, int x, int y, int length, int z, int color) {
        renderVerticalLine(guiGraphics, x, y, length, z, CUI.cuiConfig.getRGB());
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/tooltip/TooltipRenderUtil;renderRectangle(Lnet/minecraft/client/gui/GuiGraphics;IIIIII)V"), method = "renderTooltipBackground")
    private static void injected3(GuiGraphics guiGraphics, int x, int y, int width, int height, int z, int color) {
        renderRectangle(guiGraphics, x, y, width, height, z, CUI.cuiConfig.getRGB());
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/tooltip/TooltipRenderUtil;renderFrameGradient(Lnet/minecraft/client/gui/GuiGraphics;IIIIIII)V"), method = "renderTooltipBackground")
    private static void injected4(GuiGraphics guiGraphics, int x, int y, int width, int height, int z, int topColor, int bottomColor) {
        renderFrameGradient(guiGraphics, x, y, width, height, z, CUI.cuiConfig.getRGB(), CUI.cuiConfig.getRGB());
    }
    #endif
    #endif

}
