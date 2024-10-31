package com.cui.forge.mixin.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.cui.CUI_Common.colors;

@Mixin(Screen.class)
public abstract class ScreenMixin {
	@Inject(at = @At(value = "HEAD"), method = "renderBackground")
	private void renderHead1(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
		context.setShaderColor(colors.r, colors.g, colors.b, 1);
	}

	@Inject(at = @At(value = "TAIL"), method = "renderBackground")
	private void renderTail1(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
		context.setShaderColor(1, 1, 1, 1);
	}
}