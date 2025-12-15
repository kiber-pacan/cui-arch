package com.cui.forge.mixin;

import com.cui.core.CUI;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.FormattedCharSequence;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

#if MC_VER >= V1_21_6 import com.cui.mixin.client.shitass.book.RecipeBookMixin;
import com.mojang.blaze3d.pipeline.RenderPipeline; #endif


#if MC_VER >= V1_21_3 import net.minecraft.client.gui.screens.inventory.AbstractRecipeBookScreen; #endif
#if MC_VER < V1_21_1
#endif

#if MC_VER <= V1_21_6

#endif

#if MC_VER >= V1_21_6
import net.minecraft.client.DeltaTracker;
import net.minecraft.core.component.DataComponents;
#endif

// Probably retarded way of coloring white/gray text, but who cares

@Mixin(GuiGraphics.class)
public class DrawStringMixinForge {
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Font;drawInBatch(Ljava/lang/String;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/client/gui/Font$DisplayMode;IIZ)I"), method = "drawString(Lnet/minecraft/client/gui/Font;Ljava/lang/String;FFIZ)I")
    private int drawInBatch(Font instance, String text, float x, float y, int color, boolean dropShadow, Matrix4f matrix, MultiBufferSource buffer, Font.DisplayMode displayMode, int backgroundColor, int packedLightCoords, boolean bidirectional)
    {
        if (((color) & 0xFF) == ((color >> 8) & 0xFF) && ((color >> 8) & 0xFF) == ((color >> 16) & 0xFF)) {
            return instance.drawInBatch(text, x, y, CUI.cuiConfig.getTextColor(color), dropShadow, matrix, buffer, displayMode, backgroundColor, packedLightCoords);
        }
        return instance.drawInBatch(text, x, y, color, dropShadow, matrix, buffer, displayMode, backgroundColor, packedLightCoords);
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Font;drawInBatch(Lnet/minecraft/util/FormattedCharSequence;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/client/gui/Font$DisplayMode;II)I"), method = "drawString(Lnet/minecraft/client/gui/Font;Lnet/minecraft/util/FormattedCharSequence;FFIZ)I")
    private int drawInBatch1(Font instance, FormattedCharSequence text, float x, float y, int color, boolean dropShadow, Matrix4f matrix, MultiBufferSource buffer, Font.DisplayMode displayMode, int backgroundColor, int packedLightCoords)
    {
        if (((color) & 0xFF) == ((color >> 8) & 0xFF) && ((color >> 8) & 0xFF) == ((color >> 16) & 0xFF)) {
            return instance.drawInBatch(text, x, y, CUI.cuiConfig.getTextColor(color), dropShadow, matrix, buffer, displayMode, backgroundColor, packedLightCoords);
        }
        return instance.drawInBatch(text, x, y, color, dropShadow, matrix, buffer, displayMode, backgroundColor, packedLightCoords);
    }
}