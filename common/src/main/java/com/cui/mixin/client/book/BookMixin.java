package com.cui.mixin.client.book;

import com.cui.abs.core.data.Pair;
import com.cui.abs.core.data.Rectangle;
import com.cui.abs.core.data.ResourceBridge;

import com.cui.abs.core.data.data.GuiGraphicsMethods;
import com.cui.abs.core.rendering.gui.GuiRenderer;
import com.cui.core.CUI;
#if MC_VER >= V1_21_6 import com.mojang.blaze3d.pipeline.RenderPipeline; #endif
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
#if MC_VER >= V1_21_11
import net.minecraft.resources.Identifier;
#else
import net.minecraft.resources.ResourceLocation;
#if MC_VER >= V1_21_3
import net.minecraft.client.renderer.RenderType;
#endif
#endif
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;


#if MC_VER <= V1_21_6
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
#endif


@Mixin(RecipeBookComponent.class)
public class BookMixin {
    // Recipe book background
    #if MC_VER >= V1_21_6
    @Redirect(at = @At(value = "INVOKE", target = GuiGraphicsMethods.blit8), method = "render")
    private void blitRecipeBookBackground(GuiGraphics instance, RenderPipeline pipeline, #if MC_VER >= V1_21_11 Identifier #else ResourceLocation #endif atlas, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight) {
        GuiRenderer.blitSprite(instance, "GUI_TEXTURED", ResourceBridge.spriteDefaultNamespace("recipe_book"), new Rectangle(x, y, width, height), new Rectangle((int) u, (int) v, textureWidth, textureHeight), CUI.cuiConfig.getRGB());
    }
    #else
    @Inject(at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;pushPose()V", shift = At.Shift.AFTER), method = "render")
    private void setRecipeBookBackgorundColor(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        GuiRenderer.setShaderColor(guiGraphics, CUI.cuiConfig.getRGB());
    }

    @Inject(at = @At(value = "INVOKE", target = #if MC_VER >= V1_21_3 #if MC_VER >= V1_21_6 "Lnet/minecraft/client/gui/GuiGraphics;blit(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/ResourceLocation;IIFFIIII)V" #else "Lnet/minecraft/client/gui/GuiGraphics;blit(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIFFIIII)V" #endif #else "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIIIII)V" #endif, shift = At.Shift.AFTER), method = "render")
    private void clearRecipeBookBackgorundColor(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        GuiRenderer.clearShaderColor(guiGraphics);
    }
    #endif
}

