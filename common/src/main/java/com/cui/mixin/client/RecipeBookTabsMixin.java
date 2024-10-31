package com.cui.mixin.client;

import com.cui.CUI_Config;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.recipebook.RecipeBookTabButton;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(RecipeBookTabButton.class)
public class RecipeBookTabsMixin {

    @Inject(at = @At(value = "HEAD"), method = "renderWidget")
    private void renderHead(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        Color colors = CUI_Config.HANDLER.instance().color;
        guiGraphics.setColor((float) colors.getRed() / 255, (float) colors.getGreen() / 255, (float) colors.getBlue() / 255, 1);
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lnet/minecraft/resources/ResourceLocation;IIII)V", shift = At.Shift.AFTER), method = "renderWidget")
    private void renderTail(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        guiGraphics.setColor(1, 1, 1, 1);
    }
}
