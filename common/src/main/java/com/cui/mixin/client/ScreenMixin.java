package com.cui.mixin.client;

import com.cui.CUI_Common;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public abstract class ScreenMixin {
	@Inject(at = @At(value = "HEAD"), method = "render")
	private void renderHead(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
		context.setShaderColor(CUI_Common.RED + CUI_Common.CBONUS, CUI_Common.GREEN + CUI_Common.CBONUS, CUI_Common.BLUE + CUI_Common.CBONUS, 1);
	}

	@Inject(at = @At(value = "TAIL"), method = "render")
	private void renderTail(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
		context.setShaderColor(1, 1, 1, 1);
	}

	@Inject(at = @At(value = "HEAD"), method = "renderInGameBackground")
	private void renderHead(DrawContext context, CallbackInfo ci) {
		context.setShaderColor(1, 1, 1, 1);
	}

	@Inject(at = @At(value = "TAIL"), method = "renderInGameBackground")
	private void renderTail(DrawContext context, CallbackInfo ci) {
		context.setShaderColor(CUI_Common.RED + CUI_Common.CBONUS, CUI_Common.GREEN + CUI_Common.CBONUS, CUI_Common.BLUE + CUI_Common.CBONUS, 1);
	}
}