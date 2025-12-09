package com.cui.mixin.client.pre1_21_6;

import com.cui.CUI;
#if MC_VER >= V1_21_6 import com.mojang.blaze3d.pipeline.RenderPipeline; #endif
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

#if MC_VER <= V1_21_6
import net.minecraft.client.renderer.RenderType;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import com.mojang.blaze3d.systems.RenderSystem;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.gui.GuiGraphics;

import java.awt.*;
#endif

import java.awt.*;


@Mixin(Screen.class)
public abstract class ScreenMixin {
    #if MC_VER >= V1_21_6
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/ResourceLocation;IIFFIIII)V"), method = "renderMenuBackgroundTexture")
    private static void injected(GuiGraphics instance, RenderPipeline pipeline, ResourceLocation atlas, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight) {
        instance.blit(pipeline, atlas, x, y, u, v, width, height, textureWidth, textureHeight, CUI.cuiConfig.getRGB());
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

	@Inject(at = @At(value = "TAIL"), method = "render")
	private void renderTail(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        #if MC_VER >= V1_21_3
        guiGraphics.flush();
        RenderSystem.setShaderColor(1, 1, 1, 1);
        #endif
        #if MC_VER <= V1_21_1 guiGraphics.setColor(1, 1, 1, 1); #endif
    }


	@Inject(at = @At(value = "HEAD"), method = "renderBackground")
	private void renderHead1(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        #if MC_VER >= V1_21_3
        guiGraphics.flush();
        RenderSystem.setShaderColor(CUI.cuiConfig.r, CUI.cuiConfig.g, CUI.cuiConfig.b, 1);
        #endif
        #if MC_VER <= V1_21_1 guiGraphics.setColor(CUI.cuiConfig.r, CUI.cuiConfig.g, CUI.cuiConfig.b, 1); #endif
    }

	@Inject(at = @At(value = "TAIL"), method = "renderBackground")
	private void renderTail1(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        #if MC_VER >= V1_21_3
        guiGraphics.flush();
        RenderSystem.setShaderColor(1, 1, 1, 1);
        #endif
        #if MC_VER <= V1_21_1 guiGraphics.setColor(1, 1, 1, 1); #endif
    }
    #endif

    @Shadow public int width;
    @Shadow public int height;

    @Inject(at = @At(value = "INVOKE", target = #if MC_VER >= V1_21_3 #if MC_VER >= V1_21_6 "Lnet/minecraft/client/gui/screens/Screen;renderBlurredBackground(Lnet/minecraft/client/gui/GuiGraphics;)V" #else "Lnet/minecraft/client/gui/screens/Screen;renderBlurredBackground()V" #endif #else "Lnet/minecraft/client/gui/screens/Screen;renderBlurredBackground(F)V" #endif, shift = At.Shift.BEFORE), method = "renderBackground")
    private void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        Color colora = new Color(CUI.cuiConfig.r, CUI.cuiConfig.g, CUI.cuiConfig.b, CUI.cuiConfig.a);
        Color colorb = new Color(CUI.cuiConfig.r, CUI.cuiConfig.g, CUI.cuiConfig.b, CUI.cuiConfig.a);

        colora = colora.darker().darker().darker();
        colorb = colorb.darker().darker().darker().darker().darker().darker();

        guiGraphics.fillGradient(0, 0, width, height, colorb.getRGB(), colora.getRGB());
    }

    @Inject(at = @At(value = "HEAD"), cancellable = true, method = "renderTransparentBackground")
    private void render(GuiGraphics guiGraphics, CallbackInfo ci) {
        Color colora = new Color(CUI.cuiConfig.r, CUI.cuiConfig.g, CUI.cuiConfig.b, CUI.cuiConfig.a);
        Color colorb = new Color(CUI.cuiConfig.r, CUI.cuiConfig.g, CUI.cuiConfig.b, CUI.cuiConfig.a);

        colora = colora.darker().darker().darker();
        colorb = colorb.darker().darker().darker().darker().darker().darker();

        guiGraphics.fillGradient(0, 0, width, height, colorb.getRGB(), colora.getRGB());

        ci.cancel();
    }
}