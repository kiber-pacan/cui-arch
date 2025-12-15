package com.cui.forge.mixin;

import com.cui.core.CUI;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.TooltipRenderUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;


// Coloring tool tip (hover thingy)

@Mixin(TooltipRenderUtil.class)
public class TooltipMixinForge {

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;fill(IIIIII)V"), method = "renderHorizontalLine")
    private static void renderHorizontalLine1(GuiGraphics instance, int minX, int minY, int maxX, int maxY, int z, int color) {
        instance.fill(minX, minY, maxX, maxY, z, CUI.cuiConfig.getRGB());
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;fill(IIIIII)V"), method = "renderVerticalLine")
    private static void renderHorizontalLine2(GuiGraphics instance, int minX, int minY, int maxX, int maxY, int z, int color) {
        instance.fill(minX, minY, maxX, maxY, z, CUI.cuiConfig.getRGB());
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;fillGradient(IIIIIII)V"), method = "renderVerticalLineGradient")
    private static void renderVerticalLine(GuiGraphics instance, int x1, int y1, int x2, int y2, int z, int colorFrom, int colorTo) {
        instance.fillGradient(x1, y1, x2, y2, z, CUI.cuiConfig.getRGB(), CUI.cuiConfig.getRGB());
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;fillGradient(IIIIIII)V"), method = "renderRectangle(Lnet/minecraft/client/gui/GuiGraphics;IIIIIII)V")
    private static void renderRectangle(GuiGraphics instance, int x1, int y1, int x2, int y2, int z, int colorFrom, int colorTo) {
        instance.fillGradient(x1, y1, x2, y2, z, CUI.cuiConfig.getRGB(), CUI.cuiConfig.getRGB());
    }
}
