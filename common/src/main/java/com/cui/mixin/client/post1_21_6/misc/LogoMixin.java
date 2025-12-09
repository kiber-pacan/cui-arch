package com.cui.mixin.client.post1_21_6.misc;

import com.cui.CUI;
#if MC_VER >= V1_21_6 import com.mojang.blaze3d.pipeline.RenderPipeline; #endif
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.LogoRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;
import java.util.function.Function;

// Color minecraft logo in title screen

@Mixin(LogoRenderer.class)
public class LogoMixin {
    #if MC_VER >= V1_21_6
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/ResourceLocation;IIFFIIIII)V"), method = "renderLogo(Lnet/minecraft/client/gui/GuiGraphics;IFI)V")
    private static void injected(GuiGraphics instance, RenderPipeline pipeline, ResourceLocation atlas, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight, int color) {
        instance.blit(pipeline, atlas, x, y, u, v, width, height, textureWidth, textureHeight, CUI.cuiConfig.getRGB());
    }
    #else
    #if MC_VER >= V1_21_3
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIFFIIIII)V"), method = "renderLogo(Lnet/minecraft/client/gui/GuiGraphics;IFI)V")
    private static void injected1(GuiGraphics instance, Function<ResourceLocation, RenderType> renderTypeGetter, ResourceLocation atlasLocation, int x, int y, float uOffset, float vOffset, int uWidth, int vHeight, int textureWidth, int textureHeight, int color) {
        instance.blit(renderTypeGetter, atlasLocation, x, y, uOffset, vOffset, uWidth, vHeight, textureWidth, textureHeight, CUI.cuiConfig.getRGB());
    }
    #else
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIFFIIII)V"), method = "renderLogo(Lnet/minecraft/client/gui/GuiGraphics;IFI)V")
    private static void injected1(GuiGraphics instance, ResourceLocation atlasLocation, int x, int y, float uOffset, float vOffset, int width, int height, int textureWidth, int textureHeight) {
        #if MC_VER <= V1_21_1 instance.setColor(CUI.cuiConfig.r, CUI.cuiConfig.g, CUI.cuiConfig.b, 1); #endif

        instance.blit(atlasLocation, x, y, uOffset, vOffset, width, height, textureWidth, textureHeight);

        #if MC_VER <= V1_21_1 instance.setColor(1, 1, 1, 1); #endif
    }
    #endif
    #endif
}
