package com.cui.mixin.client.book;

import com.cui.abs.core.rendering.gui.GuiRenderer;
import com.cui.core.CUI;
#if MC_VER >= V1_21_6 import com.mojang.blaze3d.pipeline.RenderPipeline; #endif
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.recipebook.RecipeBookTabButton;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
#if MC_VER <= V1_21_6

import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
#endif

import net.minecraft.client.gui.components.AbstractButton;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.GrindstoneScreen;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.injection.At;
import com.cui.core.CUI;

@Mixin(RecipeBookTabButton.class)
public class RecipeBookTabsMixin {
    #if MC_VER >= V1_21_6
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/ResourceLocation;IIII)V"), method = "renderWidget")
    private static void injected(GuiGraphics instance, RenderPipeline pipeline, ResourceLocation sprite, int x, int y, int width, int height) {
        instance.blitSprite(pipeline, sprite, x, y, width, height, CUI.cuiConfig.getRGB());
    }
    #else
    @Inject(at = @At(value = "HEAD"), method = "renderWidget")
    private void renderHead(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        GuiRenderer.setShaderColor(guiGraphics, CUI.cuiConfig.getRGB());
    }

    @Inject(at = @At(value = "INVOKE", target = #if MC_VER >= V1_20_4 "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(" #if MC_VER >= V1_21_6 + "Lcom/mojang/blaze3d/pipeline/RenderPipeline;" #elif MC_VER >= V1_21_3 + "Ljava/util/function/Function;" #endif + "Lnet/minecraft/resources/ResourceLocation;IIII)V" #else "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIIIII)V" #endif, shift = At.Shift.AFTER), method = "renderWidget")
    private void renderTail(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        GuiRenderer.clearShaderColor(guiGraphics);
    }
    #endif
}
