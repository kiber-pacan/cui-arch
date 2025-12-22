package com.cui.mixin.client.misc.widgets;

import com.cui.abs.core.data.Rectangle;
import com.cui.abs.core.data.data.GuiGraphicsMethods;
import com.cui.abs.core.rendering.gui.GuiRenderer;
import com.cui.core.CUI;
#if MC_VER >= V1_21_6 import com.cui.mixin.client.book.RecipeBookMixin;
import com.mojang.blaze3d.pipeline.RenderPipeline; #endif


#if MC_VER >= V1_21_3 import net.minecraft.client.gui.screens.inventory.AbstractRecipeBookScreen; #endif
import net.minecraft.client.gui.screens.inventory.CraftingScreen;
#if MC_VER < V1_21_1
#endif
import net.minecraft.client.gui.screens.inventory.tooltip.TooltipRenderUtil;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;

#if MC_VER >= V1_21_11
import net.minecraft.resources.Identifier;
#else
import net.minecraft.resources.ResourceLocation;
#if MC_VER >= V1_21_3
import net.minecraft.client.renderer.RenderType;
#endif
#endif

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


// Coloring tool tip (hover thingy)

@Mixin(TooltipRenderUtil.class)
public class TooltipMixin {
    #if MC_VER >= V1_21_6
    @Redirect(at = @At(value = "INVOKE", target = GuiGraphicsMethods.blitSprite1Rec), method = "renderTooltipBackground")
    private static void injected(GuiGraphics instance, RenderPipeline pipeline, #if MC_VER >= V1_21_11 Identifier #else ResourceLocation #endif sprite, int x, int y, int width, int height) {
        GuiRenderer.blitSprite(instance, "GUI_TEXTURED", sprite, new Rectangle(x, y, width, height), CUI.cuiConfig.getRGB());
    }
    #elif MC_VER >= V1_21_3
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIII)V"), method = "renderTooltipBackground")
    private static void injected(GuiGraphics instance, Function<ResourceLocation, RenderType> renderTypeGetter, ResourceLocation sprite, int x, int y, int width, int height) {
        instance.blitSprite(renderTypeGetter, sprite, x, y, width, height, CUI.cuiConfig.getRGB());
    }
    #elif LOADER == FORGE
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;fill(IIIIII)V"), method = "renderHorizontalLine")
    private static void renderHorizontalLine1(GuiGraphics instance, int minX, int minY, int maxX, int maxY, int z, int color) {
        instance.fill(minX, minY, maxX, maxY, z, CUI.cuiConfig.getThemeColor());
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;fill(IIIIII)V"), method = "renderVerticalLine")
    private static void renderHorizontalLine2(GuiGraphics instance, int minX, int minY, int maxX, int maxY, int z, int color) {
        instance.fill(minX, minY, maxX, maxY, z, CUI.cuiConfig.getThemeColor());
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;fillGradient(IIIIIII)V"), method = "renderVerticalLineGradient")
    private static void renderVerticalLine(GuiGraphics instance, int x1, int y1, int x2, int y2, int z, int colorFrom, int colorTo) {
        instance.fillGradient(x1, y1, x2, y2, z, CUI.cuiConfig.getRGB(), CUI.cuiConfig.getThemeColor());
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;fillGradient(IIIIIII)V"), method = "renderRectangle(Lnet/minecraft/client/gui/GuiGraphics;IIIIIII)V")
    private static void renderRectangle(GuiGraphics instance, int x1, int y1, int x2, int y2, int z, int colorFrom, int colorTo) {
        instance.fillGradient(x1, y1, x2, y2, z, CUI.cuiConfig.getRGB(), CUI.cuiConfig.getRGB());
    }
    #else

    @Shadow
    private static void renderFrameGradient(GuiGraphics guiGraphics, int x, int y, int width, int height, int z, int topColor, int bottomColor) {
    }

    @Shadow
    private static void renderVerticalLine(GuiGraphics guiGraphics, int x, int y, int length, int z, int color) {
    }

    @Shadow
    private static void renderVerticalLineGradient(GuiGraphics guiGraphics, int x, int y, int length, int z, int topColor, int bottomColor) {
    }

    @Shadow
    private static void renderHorizontalLine(GuiGraphics guiGraphics, int x, int y, int length, int z, int color) {
    }

    @Shadow
    private static void renderRectangle(GuiGraphics guiGraphics, int x, int y, int width, int height, int z, int color) {
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/tooltip/TooltipRenderUtil;renderHorizontalLine(Lnet/minecraft/client/gui/GuiGraphics;IIIII)V"), method = "renderTooltipBackground")
    private static void injected1(GuiGraphics guiGraphics, int x, int y, int length, int z, int color) {
        renderHorizontalLine(guiGraphics, x, y, length, z, CUI.cuiConfig.getThemeColor());
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/tooltip/TooltipRenderUtil;renderVerticalLine(Lnet/minecraft/client/gui/GuiGraphics;IIIII)V"), method = "renderTooltipBackground")
    private static void injected2(GuiGraphics guiGraphics, int x, int y, int length, int z, int color) {
        renderVerticalLine(guiGraphics, x, y, length, z, CUI.cuiConfig.getThemeColor());
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/tooltip/TooltipRenderUtil;renderRectangle(Lnet/minecraft/client/gui/GuiGraphics;IIIIII)V"), method = "renderTooltipBackground")
    private static void injected3(GuiGraphics guiGraphics, int x, int y, int width, int height, int z, int color) {
        renderRectangle(guiGraphics, x, y, width, height, z, CUI.cuiConfig.getRGB());
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/tooltip/TooltipRenderUtil;renderFrameGradient(Lnet/minecraft/client/gui/GuiGraphics;IIIIIII)V"), method = "renderTooltipBackground")
    private static void injected4(GuiGraphics guiGraphics, int x, int y, int width, int height, int z, int topColor, int bottomColor) {
        renderFrameGradient(guiGraphics, x, y, width, height, z, CUI.cuiConfig.getThemeColor(), CUI.cuiConfig.getThemeColor());
    }
    #endif
}
