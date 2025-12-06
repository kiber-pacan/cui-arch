package com.cui.mixin.client;

import com.cui.CUI;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(TitleScreen.class)
public class TitleMixin {
    #if MC_VER >= V1_21_6

    #else
    @Inject(at = @At(value = "HEAD"), method = "render")
    private void renderHead(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        Color color = new Color(CUI.cuiConfig.r, CUI.cuiConfig.g, CUI.cuiConfig.b);
        color.brighter().brighter().brighter();

        #if MC_VER >= V1_21_3
        guiGraphics.flush();
        RenderSystem.setShaderColor(color.getRed(), color.getGreen(), color.getBlue(), 1);
        #endif
        #if MC_VER <= V1_21_1 guiGraphics.setColor(color.getRed(), color.getGreen(), color.getBlue(), 1); #endif

    }

    @Inject(at = @At(value = "TAIL"), method = "render")
    private void renderTail(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        #if MC_VER >= V1_21_3

        guiGraphics.flush();
        RenderSystem.setShaderColor(1, 1, 1, 1);
        #endif
        #if MC_VER <= V1_21_1 guiGraphics.setColor(1, 1, 1, 1); #endif
    }
    #endif
}
