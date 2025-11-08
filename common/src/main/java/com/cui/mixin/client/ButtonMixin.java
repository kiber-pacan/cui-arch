package com.cui.mixin.client;

import com.cui.CUI;

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
        guiGraphics.setColor(CUI.cuiConfig.r, CUI.cuiConfig.g, CUI.cuiConfig.b, 1);
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;setColor(FFFF)V", shift = At.Shift.AFTER), method = "renderWidget")
    private void renderHead1(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        float r = CUI.cuiConfig.r;
        float g = CUI.cuiConfig.g;
        float b = CUI.cuiConfig.b;

        float mid = (r + g + b) / 3;

        guiGraphics.setColor(r + 0.25f * (3 - mid), g + 0.25f * (3 - mid), b + 0.25f * (3 - mid), 1);
    }
}