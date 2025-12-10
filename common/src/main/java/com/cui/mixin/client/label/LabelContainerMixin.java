package com.cui.mixin.client.label;

import com.cui.core.CUI;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AbstractContainerScreen.class)
public class LabelContainerMixin {
    #if MC_VER >= V1_21_6
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Lnet/minecraft/network/chat/Component;IIIZ)V"), method = "renderLabels")
    private static void injected(GuiGraphics instance, Font font, Component text, int x, int y, int color, boolean drawShadow) {
        instance.drawString(font, text, x, y, CUI.mixColors(-1, CUI.cuiConfig.getRGB()), true);
    }
    #else
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Lnet/minecraft/network/chat/Component;IIIZ)I"), method = "renderLabels")
    private static int injected(GuiGraphics instance, Font font, Component text, int x, int y, int color, boolean drawShadow) {
        instance.drawString(font, text, x, y, CUI.mixColors(-1, CUI.cuiConfig.getRGB()), true);
        return x;
    }
    #endif
}
