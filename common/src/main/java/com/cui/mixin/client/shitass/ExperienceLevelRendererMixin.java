package com.cui.mixin.client.pre1_21_6;

#if MC_VER >= V1_21_6
import com.cui.CUI;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.contextualbar.ExperienceBarRenderer;
#endif

import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(#if MC_VER >= V1_21_6 ExperienceBarRenderer.class #else Minecraft.class #endif)
public class ExperienceLevelRendererMixin {
    #if MC_VER >= V1_21_6
    // XP text
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/ResourceLocation;IIII)V"), method = "renderBackground")
    private static void injected(GuiGraphics instance, RenderPipeline pipeline, ResourceLocation sprite, int x, int y, int width, int height) {
        instance.blitSprite(pipeline, sprite, x, y, width, height, CUI.cuiConfig.getRGB());
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/ResourceLocation;IIIIIIII)V"), method = "renderBackground")
    private static void injected(GuiGraphics instance, RenderPipeline pipeline, ResourceLocation sprite, int textureWidth, int textureHeight, int u, int v, int x, int y, int width, int height) {
        instance.blitSprite(pipeline, sprite, textureWidth, textureHeight, u, v, x, y, width, height, CUI.cuiConfig.getRGB());
    }
    #endif
}
