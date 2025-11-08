package com.cui.mixin.client;


import com.cui.CUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.stats.RecipeBook;
import net.minecraft.stats.RecipeBookSettings;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import static com.cui.CUI.LOGGER;

import java.awt.*;

@Mixin(InventoryScreen.class)
public class InvScreenMixin {
	// Background

	@Inject(at = @At(value = "HEAD"), method = "renderBg")
	private void renderHead(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY, CallbackInfo ci) {
        guiGraphics.setColor(CUI.cuiConfig.r, CUI.cuiConfig.g, CUI.cuiConfig.b, 1);
	}

	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIIIII)V", shift = At.Shift.AFTER), method = "renderBg")
	private void renderTail(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY, CallbackInfo ci) {
		guiGraphics.setColor(1, 1, 1, 1);
	}

	@Final
	@Shadow
	private RecipeBookComponent recipeBookComponent;

	@Inject(at = @At(value = "TAIL"), method = "renderBg")
	private void renderTail1(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY, CallbackInfo ci) {

		int a = Minecraft.getInstance().getWindow().getGuiScaledWidth(), b = Minecraft.getInstance().getWindow().getGuiScaledHeight();
		if (this.recipeBookComponent.isVisible()) {
			guiGraphics.renderItem(Items.KNOWLEDGE_BOOK.getDefaultInstance(), (int) (((float) a / 2) + 95), (int) (((float) b / 2) - 21));
		} else {
			guiGraphics.renderItem(Items.KNOWLEDGE_BOOK.getDefaultInstance(), (int) (((float) a / 2) + 18), (int) (((float) b / 2) - 21));
		}

        guiGraphics.setColor(CUI.cuiConfig.r, CUI.cuiConfig.g, CUI.cuiConfig.b, 1);
	}
}