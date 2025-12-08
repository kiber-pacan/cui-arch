package com.cui.mixin.client.post1_21_6.misc;

import com.cui.CUI;
import com.cui.ColorScreen;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.SplashRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.awt.*;

// Splashes next to the logo in main menu

@Mixin(#if MC_VER >= V1_21_6 SplashRenderer.class #else Minecraft.class #endif)
public class SplashMixin {
    #if MC_VER >= V1_21_6
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawCenteredString(Lnet/minecraft/client/gui/Font;Ljava/lang/String;III)V"), method = "render")
    private static void injected(GuiGraphics instance, Font font, String text, int x, int y, int color) {
        instance.drawCenteredString(font, text, x, y, CUI.cuiConfig.getTextColor(new Color(255, 255, 255, (int) ((color >> 24) & 0xFF)).getRGB()));
    }
    #endif
}
