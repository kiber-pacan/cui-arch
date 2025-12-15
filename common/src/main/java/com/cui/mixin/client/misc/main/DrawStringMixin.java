package com.cui.mixin.client.misc.main;

import com.cui.core.CUI;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
#if MC_VER >= V1_21_6 import net.minecraft.client.gui.render.state.GuiRenderState;
import net.minecraft.client.gui.render.state.GuiTextRenderState; #endif
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.FormattedCharSequence;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


// Probably retarded way of coloring white/gray text, but who cares

@Mixin(GuiGraphics.class)
public class DrawStringMixin {
    #if LOADER != FORGE && LOADER != NEOFORGE
    #if MC_VER >= V1_21_6
    @ModifyArg(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/render/state/GuiRenderState;submitText(Lnet/minecraft/client/gui/render/state/GuiTextRenderState;)V"), method = "drawString(Lnet/minecraft/client/gui/Font;Lnet/minecraft/util/FormattedCharSequence;IIIZ)V")
    private GuiTextRenderState injected(GuiTextRenderState renderState) {
        if (((renderState.color) & 0xFF) == ((renderState.color >> 8) & 0xFF) && ((renderState.color >> 8) & 0xFF) == ((renderState.color >> 16) & 0xFF)) {
            return new GuiTextRenderState(renderState.font, renderState.text, renderState.pose, renderState.x, renderState.y, CUI.cuiConfig.getTextColor(renderState.color), renderState.backgroundColor, renderState.dropShadow, renderState.scissor);
        }

        return renderState;
    }
    #else
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Font;drawInBatch(Lnet/minecraft/util/FormattedCharSequence;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/client/gui/Font$DisplayMode;II)I"), method = "drawString(Lnet/minecraft/client/gui/Font;Lnet/minecraft/util/FormattedCharSequence;IIIZ)I")
    private int drawInBatch(Font instance, FormattedCharSequence text, float x, float y, int color, boolean dropShadow, Matrix4f matrix, MultiBufferSource buffer, Font.DisplayMode displayMode, int backgroundColor, int packedLightCoords)
    {
        if (((color) & 0xFF) == ((color >> 8) & 0xFF) && ((color >> 8) & 0xFF) == ((color >> 16) & 0xFF)) {
            return instance.drawInBatch(text, x, y, CUI.cuiConfig.getTextColor(color), dropShadow, matrix, buffer, displayMode, backgroundColor, packedLightCoords);
        }
        return instance.drawInBatch(text, x, y, color, dropShadow, matrix, buffer, displayMode, backgroundColor, packedLightCoords);
    }

    @Redirect(at = @At(value = "INVOKE", target = #if MC_VER >= V1_21_3 "Lnet/minecraft/client/gui/Font;drawInBatch(Ljava/lang/String;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/client/gui/Font$DisplayMode;II)I" #else "Lnet/minecraft/client/gui/Font;drawInBatch(Ljava/lang/String;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/client/gui/Font$DisplayMode;IIZ)I" #endif), method = "drawString(Lnet/minecraft/client/gui/Font;Ljava/lang/String;IIIZ)I")
    private int drawInBatch1(Font instance, String text, float x, float y, int color, boolean dropShadow, Matrix4f matrix, MultiBufferSource buffer, Font.DisplayMode displayMode, int backgroundColor, int packedLightCoords #if MC_VER < V1_21_3 , boolean bidirectional #endif)
    {
        if (((color) & 0xFF) == ((color >> 8) & 0xFF) && ((color >> 8) & 0xFF) == ((color >> 16) & 0xFF)) {
            return instance.drawInBatch(text, x, y, CUI.cuiConfig.getTextColor(color), dropShadow, matrix, buffer, displayMode, backgroundColor, packedLightCoords);
        }
        return instance.drawInBatch(text, x, y, color, dropShadow, matrix, buffer, displayMode, backgroundColor, packedLightCoords);
    }
    #endif
    #endif
}