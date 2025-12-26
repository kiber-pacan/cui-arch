package com.cui.mixin.client.misc.widgets;

import com.cui.abs.core.data.Rectangle;
import com.cui.abs.core.data.ResourceBridge;
import com.cui.abs.core.data.data.GuiGraphicsMethods;
import com.cui.abs.core.rendering.gui.GuiRenderer;
import com.cui.core.CUI;
#if MC_VER >= V1_21_6 import com.mojang.blaze3d.pipeline.RenderPipeline; #endif
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.LogoRenderer;
#if MC_VER >= V1_21_11
import net.minecraft.resources.Identifier;
#else
import net.minecraft.resources.ResourceLocation;
#if MC_VER >= V1_21_3
import net.minecraft.client.renderer.RenderType;
#endif
#endif
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import com.cui.core.CUI;
#if MC_VER >= V1_21_6 import com.cui.mixin.client.book.RecipeBookMixin;
import com.mojang.blaze3d.pipeline.RenderPipeline; #endif


#if MC_VER >= V1_21_3 import net.minecraft.client.gui.screens.inventory.AbstractRecipeBookScreen; #endif
import net.minecraft.client.gui.screens.inventory.CraftingScreen;
#if MC_VER < V1_21_1
#endif
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

#if MC_VER <= V1_21_6

import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
#endif

#if MC_VER >= V1_21_6
import net.minecraft.client.DeltaTracker;
import net.minecraft.core.component.DataComponents;
#endif

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Function;
import java.util.logging.Logger;

// Color minecraft logo in title screen

@Mixin(LogoRenderer.class)
public class LogoMixin {
    #if MC_VER >= V1_21_3
    @Redirect(at = @At(value = "INVOKE", target = GuiGraphicsMethods.blit91, ordinal = 0), method = "renderLogo(Lnet/minecraft/client/gui/GuiGraphics;IFI)V")
    private static void renderLogo(GuiGraphics instance, #if MC_VER >= V1_21_6 RenderPipeline #elif MC_VER >= V1_21_3 Function<ResourceLocation, RenderType> #endif pipeline, #if MC_VER >= V1_21_11 Identifier #else ResourceLocation #endif atlas, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight, int color) {
        GuiRenderer.blitSprite(instance, "GUI_TEXTURED", ResourceBridge.spriteDefaultNamespace("title/minecraft"), new Rectangle(x, y, width, height), new Rectangle((int) u, (int) v, textureWidth, textureHeight), CUI.cuiConfig.getTextColor(color));
    }

    @Redirect(at = @At(value = "INVOKE", target = GuiGraphicsMethods.blit91, ordinal = 1), method = "renderLogo(Lnet/minecraft/client/gui/GuiGraphics;IFI)V")
    private static void renderEdition(GuiGraphics instance, #if MC_VER >= V1_21_6 RenderPipeline #elif MC_VER >= V1_21_3 Function<ResourceLocation, RenderType> #endif pipeline, #if MC_VER >= V1_21_11 Identifier #else ResourceLocation #endif atlas, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight, int color) {
        GuiRenderer.blitSprite(instance, "GUI_TEXTURED", ResourceBridge.spriteDefaultNamespace("title/edition"), new Rectangle(x, y, width, height), new Rectangle((int) u, (int) v, textureWidth, textureHeight), CUI.cuiConfig.getTextColor(color));
    }
    #else
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;setColor(FFFF)V"), method = "renderLogo(Lnet/minecraft/client/gui/GuiGraphics;IFI)V")
    private void color(GuiGraphics guiGraphics, float red, float green, float blue, float alpha) {
        GuiRenderer.setShaderColor(guiGraphics, CUI.cuiConfig.getTextColor(-1, alpha));
    }

    @Inject(at = @At(value = "TAIL"), method = "renderLogo(Lnet/minecraft/client/gui/GuiGraphics;IFI)V")
    private void clearColor(GuiGraphics guiGraphics, int screenWidth, float transparency, int height, CallbackInfo ci) {
        GuiRenderer.clearShaderColor(guiGraphics);
    }
    #endif
}
