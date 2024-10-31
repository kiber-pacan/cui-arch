package com.cui.mixin.client;

import com.cui.CUI_Config;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.swing.*;
import java.awt.*;


@Mixin(AbstractButton.class)
public class ButtonMixin {

    @Inject(at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;enableBlend()V", shift = At.Shift.AFTER), method = "renderWidget")
    private void renderHead(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        Color colors = CUI_Config.HANDLER.instance().color;
        guiGraphics.setColor((float) colors.getRed() / 255, (float) colors.getGreen() / 255, (float) colors.getBlue() / 255, 1);
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;setColor(FFFF)V", shift = At.Shift.AFTER), method = "renderWidget")
    private void renderHead1(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        Color colors = CUI_Config.HANDLER.instance().color;
        float r = (float) colors.getRed() / 255;
        float g = (float) colors.getGreen() / 255;
        float b = (float) colors.getBlue() / 255;

        float mid = (r + g + b) / 3;

        guiGraphics.setColor(r + 0.25f * (3 - mid), g + 0.25f * (3 - mid), b + 0.25f * (3 - mid), 1);
    }
}