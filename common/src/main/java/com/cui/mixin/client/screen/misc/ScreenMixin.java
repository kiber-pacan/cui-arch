package com.cui.mixin.client.screen.misc;

import com.cui.abs.core.rendering.gui.GuiRenderer;
import com.cui.core.CUI;
#if MC_VER >= V1_21_6 import com.mojang.blaze3d.pipeline.RenderPipeline; #endif
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

#if MC_VER <= V1_21_6

import java.awt.*;
#endif

#if MC_VER >= V1_21
import net.minecraft.client.DeltaTracker;
import net.minecraft.core.component.DataComponents;
#endif

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(Screen.class)
public abstract class ScreenMixin {
    #if MC_VER >= V1_21_6
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/ResourceLocation;IIFFIIII)V"), method = "renderMenuBackgroundTexture")
    private static void injected(GuiGraphics instance, RenderPipeline pipeline, ResourceLocation atlas, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight) {
        instance.blit(pipeline, atlas, x, y, u, v, width, height, textureWidth, textureHeight, CUI.cuiConfig.getRGB());
    }
    #else



	@Inject(at = @At(value = "HEAD"), method = "renderBackground")
	private void renderHead1(GuiGraphics guiGraphics, #if MC_VER >= V1_20_4 int mouseX, int mouseY, float partialTick, #endif CallbackInfo ci) {
        GuiRenderer.setShaderColor(guiGraphics, CUI.cuiConfig.getRGB());
    }

	@Inject(at = @At(value = "TAIL"), method = "renderBackground")
	private void renderTail1(GuiGraphics guiGraphics, #if MC_VER >= V1_20_4 int mouseX, int mouseY, float partialTick, #endif CallbackInfo ci) {
        GuiRenderer.clearShaderColor(guiGraphics);
    }
    #endif

    @Shadow public int width;
    @Shadow public int height;
    #if MC_VER >= V1_21
    @Inject(at = @At(value = "TAIL"), method = "renderBackground")
    private void render1(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        float[] hsv = Color.RGBtoHSB((int) (CUI.cuiConfig.r * 255.0f), (int) (CUI.cuiConfig.g * 255.0f), (int) (CUI.cuiConfig.b * 255.0f), null);
        guiGraphics.fillGradient(0, 0, width, height,
                ((int)(CUI.cuiConfig.a * 255) << 24) | (Color.getHSBColor(hsv[0], hsv[1], hsv[2] / 2.7f).getRGB() & 0x00FFFFFF),
                ((int)(CUI.cuiConfig.a * 255) << 24) | (Color.getHSBColor(hsv[0], hsv[1], hsv[2] / 5.0f).getRGB() & 0x00FFFFFF)
        );
    }
    #endif

    #if MC_VER >= V1_20_4
    @Inject(at = @At(value = "HEAD"), cancellable = true, method = "renderTransparentBackground")
    private void render2(GuiGraphics guiGraphics, CallbackInfo ci)
    #else
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;fillGradient(IIIIII)V"), method = "renderBackground")
    private void render2(GuiGraphics guiGraphics, int x1, int y1, int x2, int y2, int colorFrom, int colorTo)
    #endif
    {
        float[] hsv = CUI.cuiConfig.getHSV();
        guiGraphics.fillGradient(0, 0, width, height,
                ((int)(CUI.cuiConfig.a * 255) << 24) | (Color.getHSBColor(hsv[0], hsv[1], hsv[2] / 2.7f).getRGB() & 0x00FFFFFF),
                ((int)(CUI.cuiConfig.a * 255) << 24) | (Color.getHSBColor(hsv[0], hsv[1], hsv[2] / 5.0f).getRGB() & 0x00FFFFFF)
        );

        #if MC_VER >= V1_20_4 ci.cancel(); #endif
    }

    #if MC_VER < V1_21
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;setColor(FFFF)V", ordinal = 0), method = "renderDirtBackground")
    private void setBackgroundColor(GuiGraphics instance, float red, float green, float blue, float alpha) {
        GuiRenderer.setShaderColor(instance, CUI.cuiConfig.getRGB());
    }

    @Inject(at = @At(value = "TAIL"), method = "renderDirtBackground", cancellable = true)
    private void render3(GuiGraphics guiGraphics, CallbackInfo ci) {
        float[] hsv = CUI.cuiConfig.getHSV();
        guiGraphics.fillGradient(0, 0, width, height,
                ((int)(CUI.cuiConfig.a * 255) << 24) | (Color.getHSBColor(hsv[0], hsv[1], hsv[2] / 2.7f).getRGB() & 0x00FFFFFF),
                ((int)(CUI.cuiConfig.a * 255) << 24) | (Color.getHSBColor(hsv[0], hsv[1], hsv[2] / 5.0f).getRGB() & 0x00FFFFFF)
        );

        ci.cancel();
    }
    #endif
}