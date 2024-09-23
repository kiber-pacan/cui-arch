package com.cui.mixin.client;

import com.cui.CUI_Common;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InventoryScreen.class)
public class InvScreenMixin {
	// Background

	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V", shift = At.Shift.AFTER), method = "drawBackground")
	private void renderHead(DrawContext context, float delta, int mouseX, int mouseY, CallbackInfo ci) {
		context.setShaderColor(1, 1, 1, 1);
	}

	@Inject(at = @At(value = "TAIL"), method = "drawBackground")
	private void renderTail(DrawContext context, float delta, int mouseX, int mouseY, CallbackInfo ci) {
		context.setShaderColor(CUI_Common.RED + CUI_Common.CBONUS, CUI_Common.GREEN + CUI_Common.CBONUS, CUI_Common.BLUE + CUI_Common.CBONUS, 1);
	}
}