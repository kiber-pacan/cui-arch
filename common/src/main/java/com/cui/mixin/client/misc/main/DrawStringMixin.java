package com.cui.mixin.client.misc.main;

import com.cui.core.CUI;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
#if MC_VER >= V1_21_6 import net.minecraft.client.gui.render.state.GuiTextRenderState; #endif
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.FormattedCharSequence;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.cui.core.CUI;
#if MC_VER >= V1_21_6 import com.cui.mixin.client.shitass.book.RecipeBookMixin;
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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Probably retarded way of coloring white/gray text, but who cares

@Mixin(GuiGraphics.class)
public class DrawStringMixin {
    #if LOADER == FABRIC || LOADER == COMMON
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Font;drawInBatch(Lnet/minecraft/util/FormattedCharSequence;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/client/gui/Font$DisplayMode;II)I"), method = "drawString(Lnet/minecraft/client/gui/Font;Lnet/minecraft/util/FormattedCharSequence;IIIZ)I")
    private int drawInBatch(Font instance, FormattedCharSequence text, float x, float y, int color, boolean dropShadow, Matrix4f matrix, MultiBufferSource buffer, Font.DisplayMode displayMode, int backgroundColor, int packedLightCoords)
    #elif LOADER == FORGE
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Font;drawInBatch(Ljava/lang/String;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/client/gui/Font$DisplayMode;IIZ)I"), method = "drawString(Lnet/minecraft/client/gui/Font;Ljava/lang/String;FFIZ)I")
    private int drawInBatch(Font instance, String text, float x, float y, int color, boolean dropShadow, Matrix4f matrix, MultiBufferSource buffer, Font.DisplayMode displayMode, int backgroundColor, int packedLightCoords, boolean bidirectional)
    #endif
    {
        if (((color) & 0xFF) == ((color >> 8) & 0xFF) && ((color >> 8) & 0xFF) == ((color >> 16) & 0xFF)) {
            return instance.drawInBatch(text, x, y, CUI.cuiConfig.getTextColor(color), dropShadow, matrix, buffer, displayMode, backgroundColor, packedLightCoords);
        }
        return instance.drawInBatch(text, x, y, color, dropShadow, matrix, buffer, displayMode, backgroundColor, packedLightCoords);
    }

    #if LOADER == FABRIC || LOADER == COMMON
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Font;drawInBatch(Ljava/lang/String;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/client/gui/Font$DisplayMode;IIZ)I"), method = "drawString(Lnet/minecraft/client/gui/Font;Ljava/lang/String;IIIZ)I")
    private int drawInBatch1(Font instance, String text, float x, float y, int color, boolean dropShadow, Matrix4f matrix, MultiBufferSource buffer, Font.DisplayMode displayMode, int backgroundColor, int packedLightCoords, boolean bidirectional)
    #elif LOADER == FORGE
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Font;drawInBatch(Lnet/minecraft/util/FormattedCharSequence;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/client/gui/Font$DisplayMode;II)I"), method = "drawString(Lnet/minecraft/client/gui/Font;Lnet/minecraft/util/FormattedCharSequence;FFIZ)I")
    private int drawInBatch1(Font instance, FormattedCharSequence text, float x, float y, int color, boolean dropShadow, Matrix4f matrix, MultiBufferSource buffer, Font.DisplayMode displayMode, int backgroundColor, int packedLightCoords)
    #endif
    {
        if (((color) & 0xFF) == ((color >> 8) & 0xFF) && ((color >> 8) & 0xFF) == ((color >> 16) & 0xFF)) {
            return instance.drawInBatch(text, x, y, CUI.cuiConfig.getTextColor(color), dropShadow, matrix, buffer, displayMode, backgroundColor, packedLightCoords);
        }
        return instance.drawInBatch(text, x, y, color, dropShadow, matrix, buffer, displayMode, backgroundColor, packedLightCoords);
    }
}