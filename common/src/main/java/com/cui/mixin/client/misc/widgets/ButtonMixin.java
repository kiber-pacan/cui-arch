package com.cui.mixin.client.misc.widgets;

import com.cui.abs.core.rendering.gui.GuiRenderer;
import com.cui.core.CUI;



#if MC_VER <= V1_21_6
import com.mojang.blaze3d.systems.RenderSystem;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.gui.GuiGraphics;
#endif
import net.minecraft.client.gui.components.AbstractButton;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.GrindstoneScreen;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.injection.At;
import com.cui.core.CUI;

import java.awt.*;


@Mixin(AbstractButton.class)
public class ButtonMixin {
    #if MC_VER >= V1_21_6
    @ModifyArg(method = "renderWidget", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/ARGB;color(FI)I"), index = 1)
    private int injected(int color) {
        if (((color) & 0xFF) == ((color >> 8) & 0xFF) && ((color >> 8) & 0xFF) == ((color >> 16) & 0xFF)) {
            return CUI.cuiConfig.getTextColor(color);
        }

        return color;
    }

    @ModifyArg(method = "renderWidget", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/ResourceLocation;IIIII)V"), index = 6)
    private int injected1(int c) {
        Color c1 = CUI.cuiConfig.color;
        return ((c >> 24) & 0xFF) << 24 | (c1.getRed() & 0xFF) << 16 | (c1.getGreen() & 0xFF) << 8 | (c1.getBlue() & 0xFF);
    }

    #else
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;setColor(FFFF)V", ordinal = 0), method = "renderWidget")
    private void renderHead(GuiGraphics instance, float red, float green, float blue, float alpha) {
        GuiRenderer.setShaderColor(instance, CUI.cuiConfig.getRGBA(alpha));
    }
    #endif
}