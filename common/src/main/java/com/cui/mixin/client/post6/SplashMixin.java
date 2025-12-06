package com.cui.mixin.client.post6;

import com.cui.CUI;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.SplashRenderer;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.awt.*;

@Mixin(#if MC_VER >= V1_21_6 SplashRenderer.class #else Minecraft.class #endif)
public class SplashMixin {
    #if MC_VER >= V1_21_6
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawCenteredString(Lnet/minecraft/client/gui/Font;Ljava/lang/String;III)V"), method = "render")
    private static void injected(GuiGraphics instance, Font font, String text, int x, int y, int color) {
        Color c1 = CUI.cuiConfig.color;
        instance.drawCenteredString(font, text, x, y, ((color >> 24) & 0xFF) << 24 | (c1.getRed() & 0xFF) << 16 | (c1.getGreen() & 0xFF) << 8 | (c1.getBlue() & 0xFF));
    }
    #endif
}
