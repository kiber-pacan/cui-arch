package com.cui.mixin.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

import com.cui.CUI_Common;

@Mixin(InGameHud.class)
public class GuiMixin {
	// Hotbar

	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;translate(FFF)V", shift = At.Shift.AFTER), method = "renderHotbar")
	private void renderHead(float tickDelta, DrawContext context, CallbackInfo ci) {
		context.setShaderColor(CUI_Common.RED + CUI_Common.CBONUS, CUI_Common.GREEN + CUI_Common.CBONUS, CUI_Common.BLUE + CUI_Common.CBONUS, 1);
	}

	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;pop()V", shift = At.Shift.AFTER), method = "renderHotbar")
	private void renderTail(float tickDelta, DrawContext context, CallbackInfo ci) {
		context.setShaderColor(1, 1, 1, 1);
	}
}