package com.cui.mixin.client;

import com.cui.CUI;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;


@Mixin(Screen.class)
public abstract class ScreenMixin {
	@Inject(at = @At(value = "HEAD"), method = "render")
	private void renderHead(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        guiGraphics.setColor(CUI.cuiConfig.r, CUI.cuiConfig.g, CUI.cuiConfig.b, 1);
	}

	@Inject(at = @At(value = "TAIL"), method = "render")
	private void renderTail(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
		guiGraphics.setColor(1, 1, 1, 1);
	}



	@Inject(at = @At(value = "HEAD"), method = "renderBackground")
	private void renderHead1(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        guiGraphics.setColor(CUI.cuiConfig.r, CUI.cuiConfig.g, CUI.cuiConfig.b, 1);
	}

	@Inject(at = @At(value = "TAIL"), method = "renderBackground")
	private void renderTail1(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
		guiGraphics.setColor(1, 1, 1, 1);
	}

    @Shadow public int width;
    @Shadow public int height;

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;renderBlurredBackground(F)V", shift = At.Shift.BEFORE), method = "renderBackground")
    private void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        Color colora = new Color(CUI.cuiConfig.r, CUI.cuiConfig.g, CUI.cuiConfig.b, CUI.cuiConfig.a);
        Color colorb = new Color(CUI.cuiConfig.r, CUI.cuiConfig.g, CUI.cuiConfig.b, CUI.cuiConfig.a);

        colora = colora.darker().darker().darker();
        colorb = colorb.darker().darker().darker().darker().darker().darker();

        guiGraphics.fillGradient(0, 0, width, height, colorb.getRGB(), colora.getRGB());
    }

    @Inject(at = @At(value = "HEAD"), cancellable = true, method = "renderTransparentBackground")
    private void render(GuiGraphics guiGraphics, CallbackInfo ci) {
        Color colora = new Color(CUI.cuiConfig.r, CUI.cuiConfig.g, CUI.cuiConfig.b, CUI.cuiConfig.a);
        Color colorb = new Color(CUI.cuiConfig.r, CUI.cuiConfig.g, CUI.cuiConfig.b, CUI.cuiConfig.a);

        colora = colora.darker().darker().darker();
        colorb = colorb.darker().darker().darker().darker().darker().darker();

        guiGraphics.fillGradient(0, 0, width, height, colorb.getRGB(), colora.getRGB());

        ci.cancel();
    }
}