package com.cui.mixin.client;


import com.cui.CUI_Config;
import net.minecraft.client.gui.components.AbstractStringWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.awt.*;

@Mixin(AbstractStringWidget.class)
public class TextMixin {
    // Background
    @Shadow private int color;

    @Inject(at = @At(value = "HEAD"), method = "getColor", cancellable = true)
    private void render(CallbackInfoReturnable<Integer> cir) {
        Color colors = CUI_Config.HANDLER.instance().color;
        cir.setReturnValue(colors.getRGB());
    }
}
