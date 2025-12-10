package com.cui.mixin.client.pre1_21_6;
/*
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.cui.CUI.colors;

@Mixin(CreateWorldScreen.class)
public abstract class CreateWorldScreenMixin {
	#if MC_VER < V1_21
	@Inject(at = @At(value = "HEAD"), method = "renderBackgroundTexture")
	private void renderHead(DrawContext context, CallbackInfo ci) {
		context.setShaderColor(1, 1, 1, 1);
	}

	@Inject(at = @At(value = "TAIL"), method = "renderBackgroundTexture")
	private void renderTail(DrawContext context, CallbackInfo ci) {
		context.setShaderColor(colors.r, colors.g, colors.b, 1);
	}
	#endif

	@Inject(at = @At(value = "HEAD"), method = "render")
	private void renderHead(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
		context.setShaderColor(1, 1, 1, 1);
	}

	@Inject(at = @At(value = "TAIL"), method = "render")
	private void renderTail(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
		context.setShaderColor(colors.r, colors.g, colors.b, 1);
	}
}
*/