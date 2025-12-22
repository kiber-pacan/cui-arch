package com.cui.mixin.client.book;

import com.cui.abs.core.data.Rectangle;
import com.cui.abs.core.data.data.GuiGraphicsMethods;
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
#if MC_VER >= V1_21_11
import net.minecraft.resources.Identifier;
#else
import net.minecraft.resources.ResourceLocation;
#if MC_VER >= V1_21_3
import net.minecraft.client.renderer.RenderType;
#endif
#endif
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.injection.At;
import com.cui.core.CUI;

@Mixin(RecipeBookTabButton.class)
public class RecipeBookTabsMixin {
    #if MC_VER >= V1_21_6
    @Redirect(at = @At(value = "INVOKE", target = GuiGraphicsMethods.blitSprite2Rec), method = "renderWidget")
    private static void blitRecipeBookTabs(GuiGraphics instance, RenderPipeline pipeline, #if MC_VER >= V1_21_11 Identifier #else ResourceLocation #endif sprite, int x, int y, int width, int height) {
        GuiRenderer.blitSprite(instance, "GUI_TEXTURED", sprite, new Rectangle(x, y, width, height), CUI.cuiConfig.getRGB());
    }
    #else
    @Inject(at = @At(value = "HEAD"), method = "renderWidget")
    private void setRecipeBookTabsColor(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        GuiRenderer.setShaderColor(guiGraphics, CUI.cuiConfig.getRGB());
    }

    @Inject(at = @At(value = "INVOKE", target = #if MC_VER >= V1_20_4 "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(" #if MC_VER >= V1_21_6 + "Lcom/mojang/blaze3d/pipeline/RenderPipeline;" #elif MC_VER >= V1_21_3 + "Ljava/util/function/Function;" #endif + "Lnet/minecraft/resources/ResourceLocation;IIII)V" #else "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIIIII)V" #endif, shift = At.Shift.AFTER), method = "renderWidget")
    private void clearRecipeBookTabsColor(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        GuiRenderer.clearShaderColor(guiGraphics);
    }
    #endif
}
