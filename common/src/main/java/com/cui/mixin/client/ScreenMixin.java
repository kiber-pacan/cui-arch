package com.cui.mixin.client;

import com.cui.CUI_Config;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;


@Mixin(Screen.class)
public abstract class ScreenMixin {
	@Inject(at = @At(value = "HEAD"), method = "render")
	private void renderHead(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
		Color colors = CUI_Config.HANDLER.instance().color;
		guiGraphics.setColor((float) colors.getRed() / 255, (float) colors.getGreen() / 255, (float) colors.getBlue() / 255, 1);
	}

	@Inject(at = @At(value = "TAIL"), method = "render")
	private void renderTail(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
		guiGraphics.setColor(1, 1, 1, 1);
	}



	@Inject(at = @At(value = "HEAD"), method = "renderBackground")
	private void renderHead1(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
		Color colors = CUI_Config.HANDLER.instance().color;
		guiGraphics.setColor((float) colors.getRed() / 255, (float) colors.getGreen() / 255, (float) colors.getBlue() / 255, 1);
	}

	@Inject(at = @At(value = "TAIL"), method = "renderBackground")
	private void renderTail1(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
		guiGraphics.setColor(1, 1, 1, 1);
	}
}