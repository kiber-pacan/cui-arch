package com.cui.mixin.client.misc;

import com.cui.core.CUI;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;

#if MC_VER >= V1_21_11
import net.minecraft.client.gui.ActiveTextCollector;
import net.minecraft.client.gui.TextAlignment;
#endif

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.SplashRenderer;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.awt.*;

// Splashes next to the logo in main menu

@Mixin(SplashRenderer.class)
public class SplashMixin {
    #if MC_VER >= V1_21_11
    /*
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/ActiveTextCollector;accept(Lnet/minecraft/client/gui/TextAlignment;IILnet/minecraft/client/gui/ActiveTextCollector$Parameters;Lnet/minecraft/network/chat/Component;)V"), method = "render")
    private void injected(ActiveTextCollector instance, TextAlignment textAlignment, int x, int y, ActiveTextCollector.Parameters parameters, Component text) {
        instance.accept(textAlignment, x, y, parameters, text.getStyle().withColor(CUI.cuiConfig.getRGB()));
    }
    */
    #else
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawCenteredString(Lnet/minecraft/client/gui/Font;Ljava/lang/String;III)V"), method = "render")
    private void injected(GuiGraphics instance, Font font, String text, int x, int y, int color) {
        instance.drawCenteredString(font, text, x, y, CUI.cuiConfig.getTextColor(new Color(255, 255, 255, (int) ((color >> 24) & 0xFF)).getRGB()));
    }
    #endif
}
