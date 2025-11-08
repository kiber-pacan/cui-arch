package com.cui.mixin.client;


import com.cui.CUI;
import com.mojang.authlib.minecraft.client.MinecraftClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractFurnaceScreen;
import net.minecraft.client.gui.screens.recipebook.AbstractFurnaceRecipeBookComponent;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractFurnaceScreen.class)
public class FurnaceScreenMixin {
	// Background

	@Shadow
    @Final
    public AbstractFurnaceRecipeBookComponent recipeBookComponent;

	@Inject(at = @At(value = "TAIL"), method = "renderBg")
	private void renderTail(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY, CallbackInfo ci) {
        guiGraphics.setColor(1, 1, 1, 1);

		int a = Minecraft.getInstance().getWindow().getGuiScaledWidth(), b = Minecraft.getInstance().getWindow().getGuiScaledHeight();
		if (recipeBookComponent.isVisible()) {
            guiGraphics.renderItem(Items.KNOWLEDGE_BOOK.getDefaultInstance(), (int) (((float) a / 2) + 11), (int) (((float) b / 2) - 48));
		} else {
            guiGraphics.renderItem(Items.KNOWLEDGE_BOOK.getDefaultInstance(), (int) (((float) a / 2) - 66), (int) (((float) b / 2) - 48));
		}
        guiGraphics.setColor(CUI.cuiConfig.r, CUI.cuiConfig.g, CUI.cuiConfig.b, 1);
	}
}