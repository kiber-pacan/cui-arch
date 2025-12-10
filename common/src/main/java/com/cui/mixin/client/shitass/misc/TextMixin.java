package com.cui.mixin.client.shitass.misc;


import com.cui.core.CUI;
import net.minecraft.client.gui.components.AbstractStringWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractStringWidget.class)
public class TextMixin {
    @Shadow private int color;

    @Inject(at = @At(value = "HEAD"), method = "getColor", cancellable = true)
    private void render(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(CUI.cuiConfig.getTextColor(color));
    }
}
