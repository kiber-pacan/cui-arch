package com.cui.mixin.client.post1_21_6.misc;

import com.cui.CUI;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.render.state.GuiTextRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

// Probably retarded way of coloring white text, but who cares

@Mixin(#if MC_VER >= V1_21_6 GuiGraphics.class #else Minecraft.class #endif)
public class GuiGraphicsMixin {
    #if MC_VER >= V1_21_6
    @ModifyArg(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/render/state/GuiRenderState;submitText(Lnet/minecraft/client/gui/render/state/GuiTextRenderState;)V"), method = "drawString(Lnet/minecraft/client/gui/Font;Lnet/minecraft/util/FormattedCharSequence;IIIZ)V")
    private GuiTextRenderState injected(GuiTextRenderState renderState) {
        if (((renderState.color >> 8) & 0xFF) == 170 || ((renderState.color >> 8) & 0xFF) == 255) {
            int cuiColor = CUI.cuiConfig.getRGB();
            int r1 = (cuiColor >> 16) & 0xFF;
            int g1 = (cuiColor >> 8) & 0xFF;
            int b1 = cuiColor & 0xFF;

            int r2 = ((renderState.color) >> 16) & 0xFF;
            int g2 = ((renderState.color) >> 8) & 0xFF;
            int b2 = (renderState.color) & 0xFF;

            int a = ((renderState.color) >> 24) & 0xFF;
            int r = (r1 + r2) / 2;
            int g = (g1 + g2) / 2;
            int b = (b1 + b2) / 2;

            int color = (a << 24) | (r << 16) | (g << 8) | b;

            return new GuiTextRenderState(renderState.font, renderState.text, renderState.pose, renderState.x, renderState.y, color, renderState.backgroundColor, renderState.dropShadow, renderState.scissor);
        } else {
            return renderState;
        }
    }
    #endif
}
