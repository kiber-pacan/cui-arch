package com.cui.forge.mixin.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.cui.CUI_Common.colors;

@Mixin(InGameHud.class)
public class HUDMixin {
	// Hotbar
	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;translate(FFF)V", shift = At.Shift.AFTER), method = #if MC_VER >= V1_21 "renderHotbarVanilla" #else "renderHotbar" #endif)
	#if MC_VER >= V1_21 private void renderHead(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) { #else private void renderHead(float tickDelta, DrawContext context, CallbackInfo ci) { #endif
		context.setShaderColor(colors.r + 0.3f, colors.g + 0.3f, colors.b + 0.3f, 1);
	}

	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;pop()V", shift = At.Shift.AFTER), method = #if MC_VER >= V1_21 "renderHotbarVanilla" #else "renderHotbar" #endif)
	#if MC_VER >= V1_21 private void renderTail(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) { #else private void renderTail(float tickDelta, DrawContext context, CallbackInfo ci) { #endif
		context.setShaderColor(1, 1, 1, 1);
	}
}