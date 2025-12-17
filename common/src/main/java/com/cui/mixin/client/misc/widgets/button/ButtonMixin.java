package com.cui.mixin.client.misc.widgets.button;

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

// Buttons
@Mixin(AbstractButton.class)
public class ButtonMixin {
    #if MC_VER >= V1_21_6
    @ModifyArg(method = "renderWidget", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/ResourceLocation;IIIII)V"), index = 6)
    private int injected1(int color) {
        return CUI.cuiConfig.getRGBA(((color >>> 24) & 0xFF) / 255.0f);
    }
    #else
    @Redirect(at = @At(value = "INVOKE", target = #if MC_VER >= V1_21_3 "Lnet/minecraft/util/ARGB;white(F)I" #else "Lnet/minecraft/client/gui/GuiGraphics;setColor(FFFF)V" #endif, ordinal = 0), method = "renderWidget")
    private #if MC_VER >= V1_21_3 int #else void #endif renderHead(#if MC_VER <= V1_21_3 GuiGraphics instance, float red, float green, float blue, #endif float alpha) {
        #if MC_VER >= V1_21_3
        return CUI.cuiConfig.getRGB();
        #else
        GuiRenderer.setShaderColor(instance, CUI.cuiConfig.getRGB());
        #endif
    }
    #endif
}