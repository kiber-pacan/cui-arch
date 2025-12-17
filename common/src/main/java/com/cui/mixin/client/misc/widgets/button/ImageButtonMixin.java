package com.cui.mixin.client.misc.widgets.button;

import com.cui.abs.core.rendering.gui.GuiRenderer;
import com.cui.core.CUI;



#if MC_VER <= V1_21_6
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.components.ImageButton;
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
@Mixin(#if MC_VER <= V1_21_6 ImageButton.class #else Minecraft.class #endif)
public class ImageButtonMixin {
    #if MC_VER <= V1_20_1
    @Inject(at = @At(value = "HEAD"), method = "renderWidget")
    private void setShaderColor(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        GuiRenderer.setShaderColor(guiGraphics, CUI.cuiConfig.getRGB());
    }

    @Inject(at = @At(value = "TAIL"), method = "renderWidget")
    private void clearShaderColor(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        GuiRenderer.clearShaderColor(guiGraphics);
    }
    #endif
}