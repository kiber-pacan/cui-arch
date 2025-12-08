package com.cui.mixin.client.post1_21_6.misc;

import com.cui.CUI;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.render.state.GuiTextRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.awt.*;
import java.util.logging.Logger;

// Probably retarded way of coloring white/gray text, but who cares

@Mixin(#if MC_VER >= V1_21_6 GuiGraphics.class #else Minecraft.class #endif)
public class GuiGraphicsMixin {
    #if MC_VER >= V1_21_6
    @ModifyArg(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/render/state/GuiRenderState;submitText(Lnet/minecraft/client/gui/render/state/GuiTextRenderState;)V"), method = "drawString(Lnet/minecraft/client/gui/Font;Lnet/minecraft/util/FormattedCharSequence;IIIZ)V")
    private GuiTextRenderState injected(GuiTextRenderState renderState) {
        // Checking if r = g = b
        if (((renderState.color) & 0xFF) == ((renderState.color >> 8) & 0xFF) && ((renderState.color >> 8) & 0xFF) == ((renderState.color >> 16) & 0xFF)) {
            float[] hsv = Color.RGBtoHSB((int) (CUI.cuiConfig.r * 255.0f), (int) (CUI.cuiConfig.g * 255.0f), (int) (CUI.cuiConfig.b * 255.0f), null);
            float rawValue = ((renderState.color) & 0xFF) / 255.0f;
            float value = (rawValue <= 0.5f) ? 1 - rawValue : rawValue;

            int color = (Color.getHSBColor(hsv[0], hsv[1] / 2.5f, value).getRGB());

            return new GuiTextRenderState(renderState.font, renderState.text, renderState.pose, renderState.x, renderState.y, color, renderState.backgroundColor, renderState.dropShadow, renderState.scissor);
        }

        return renderState;
    }
    #endif
}
