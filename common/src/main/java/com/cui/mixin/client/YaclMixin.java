package com.cui.mixin.client;

import com.cui.CUI_Config;
import dev.isxander.yacl3.gui.controllers.ColorPickerWidget;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.recipebook.RecipeBookTabButton;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

#if MC_VER >= V1_20_4 @Mixin(ColorPickerWidget.class) #endif
public class YaclMixin {
    #if MC_VER >= V1_20_4

    @Inject(at = @At(value = "HEAD"), method = "render")
    private void renderHead(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        guiGraphics.setColor(1, 1, 1, 1);
    }
    #endif
}
