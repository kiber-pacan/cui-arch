package com.cui.mixin.client.post1_21_6.misc;

import com.cui.CUI;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.render.state.GuiTextRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.awt.*;

// Probably retarded way of coloring white/gray text, but who cares

@Mixin(#if MC_VER >= V1_21_6 GuiGraphics.class #else Minecraft.class #endif)
public class DrawStringMixin {
    #if MC_VER >= V1_21_6
    @ModifyArg(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/render/state/GuiRenderState;submitText(Lnet/minecraft/client/gui/render/state/GuiTextRenderState;)V"), method = "drawString(Lnet/minecraft/client/gui/Font;Lnet/minecraft/util/FormattedCharSequence;IIIZ)V")
    private GuiTextRenderState injected(GuiTextRenderState renderState) {
        // Checking if r = g = b
        if (((renderState.color) & 0xFF) == ((renderState.color >> 8) & 0xFF) && ((renderState.color >> 8) & 0xFF) == ((renderState.color >> 16) & 0xFF)) {
            return new GuiTextRenderState(renderState.font, renderState.text, renderState.pose, renderState.x, renderState.y, CUI.cuiConfig.getTextColor(renderState.color), renderState.backgroundColor, renderState.dropShadow, renderState.scissor);
        }

        return renderState;
    }
    #endif
}
