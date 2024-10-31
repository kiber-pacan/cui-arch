package com.cui.forge.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.AbstractFurnaceScreen;
import net.minecraft.client.gui.screen.recipebook.AbstractFurnaceRecipeBookScreen;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.cui.CUI_Common.colors;

@Mixin(AbstractFurnaceScreen.class)
public class FurnaceScreenMixin {
	// Background

	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V", shift = At.Shift.AFTER), method = "drawBackground")
	private void renderHead(DrawContext context, float delta, int mouseX, int mouseY, CallbackInfo ci) {
		context.setShaderColor(1, 1, 1, 1);
	}

	@Final
	@Shadow
	public AbstractFurnaceRecipeBookScreen recipeBook;

	@Inject(at = @At(value = "TAIL"), method = "drawBackground")
	private void renderTail(DrawContext context, float delta, int mouseX, int mouseY, CallbackInfo ci) {
		int a = MinecraftClient.getInstance().getWindow().getScaledWidth(), b = MinecraftClient.getInstance().getWindow().getScaledHeight();
		if (recipeBook.isOpen()) {
			context.drawItem(Items.KNOWLEDGE_BOOK.getDefaultStack(), (int) (((float) a / 2) + 11), (int) (((float) b / 2) - 48));
		} else {
			context.drawItem(Items.KNOWLEDGE_BOOK.getDefaultStack(), (int) (((float) a / 2) - 66), (int) (((float) b / 2) - 48));
		}
	}
}