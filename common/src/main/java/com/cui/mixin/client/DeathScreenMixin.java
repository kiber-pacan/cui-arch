package com.cui.mixin.client;


import com.cui.CUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.awt.*;

@Mixin(DeathScreen.class)
public class DeathScreenMixin {
	// Background

	@Inject(at = @At("HEAD"), cancellable = true, method = "renderDeathBackground")
	private static void render(GuiGraphics guiGraphics, int width, int height, CallbackInfo ci) {
        Color colora = new Color(CUI.cuiConfig.r, CUI.cuiConfig.g, CUI.cuiConfig.b, CUI.cuiConfig.a);
        Color colorb = new Color(CUI.cuiConfig.r, CUI.cuiConfig.g, CUI.cuiConfig.b, CUI.cuiConfig.a);

        colora = colora.darker().darker().darker();
        colorb = colorb.darker().darker().darker().darker().darker().darker();

        guiGraphics.fillGradient(0, 0, width, height, colorb.getRGB(), colora.getRGB());
        ci.cancel();
    }
}