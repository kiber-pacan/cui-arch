package com.cui.mixin.client;

import com.cui.CUI;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
#if MC_VER >= V1_21_6 import net.minecraft.client.renderer.RenderPipelines; #endif
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(RecipeBookComponent.class)
public class BookMixin {
    // Background
    #if MC_VER >= V1_21_6

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/ResourceLocation;IIFFIIII)V"))
    private void injected1(GuiGraphics instance, RenderPipeline pipeline, ResourceLocation atlas, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight) {
        instance.blit(pipeline, atlas, x, y, u, v, width , height, textureWidth, textureHeight, CUI.cuiConfig.getRGB());
    }

    #else
    @Inject(at = @At(value = "HEAD"), method = "render")
    private void renderHead(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        #if MC_VER >= V1_21_3


        guiGraphics.flush();
        RenderSystem.setShaderColor(CUI.cuiConfig.r, CUI.cuiConfig.g, CUI.cuiConfig.b, 1);
        #endif
        #if MC_VER <= V1_21_1 guiGraphics.setColor(CUI.cuiConfig.r, CUI.cuiConfig.g, CUI.cuiConfig.b, 1); #endif
    }

    @Inject(at = @At(value = "INVOKE", target = #if MC_VER >= V1_21_3 #if MC_VER >= V1_21_6 "Lnet/minecraft/client/gui/GuiGraphics;blit(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/ResourceLocation;IIFFIIII)V" #else "Lnet/minecraft/client/gui/GuiGraphics;blit(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIFFIIII)V" #endif #else "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIIIII)V" #endif, shift = At.Shift.AFTER), method = "render")
    private void renderTail(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        #if MC_VER >= V1_21_3
        guiGraphics.flush();
        RenderSystem.setShaderColor(1, 1, 1, 1);
        #endif
        #if MC_VER <= V1_21_1 guiGraphics.setColor(1, 1, 1, 1); #endif
    }
    #endif
}

