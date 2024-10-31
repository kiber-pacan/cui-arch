package com.cui.mixin.client;


import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractFurnaceScreen;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractFurnaceScreen.class)
public class FurnaceScreenMixin {
	/*
	// Background
	@Final
	@Shadow
	public AbstractFurnaceRecipeBookScreen recipeBook;

	@Inject(at = @At(value = "HEAD"), method = "renderBg")
	private void renderTail(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY, CallbackInfo ci) {
		int a = MinecraftClient.getInstance().getWindow().getScaledWidth(), b = MinecraftClient.getInstance().getWindow().getScaledHeight();
		if (recipeBook.isOpen()) {
			context.drawItem(Items.KNOWLEDGE_BOOK.getDefaultStack(), (int) (((float) a / 2) + 11), (int) (((float) b / 2) - 48));
		} else {
			context.drawItem(Items.KNOWLEDGE_BOOK.getDefaultStack(), (int) (((float) a / 2) - 66), (int) (((float) b / 2) - 48));
		}
		context.setShaderColor(colors.r, colors.g, colors.b, 1);
	}

	@Inject(at = @At(value = "TAIL"), method = "renderBg")
	private void renderHead(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY, CallbackInfo ci) {
		context.setShaderColor(1, 1, 1, 1);
	}*/
}