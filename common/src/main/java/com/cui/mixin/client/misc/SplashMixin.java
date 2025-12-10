package com.cui.mixin.client.misc;

import com.cui.CUI;
import com.cui.ColorScreen;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.SplashRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.awt.*;
import net.minecraft.client.Minecraft;

// Splashes next to the logo in main menu

@Mixin(SplashRenderer.class)
public class SplashMixin {
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawCenteredString(Lnet/minecraft/client/gui/Font;Ljava/lang/String;III)V"), method = "render")
    private static void injected(GuiGraphics instance, Font font, String text, int x, int y, int color) {
        instance.drawCenteredString(font, text, x, y, CUI.cuiConfig.getTextColor(new Color(255, 255, 255, (int) ((color >> 24) & 0xFF)).getRGB()));
    }
}
