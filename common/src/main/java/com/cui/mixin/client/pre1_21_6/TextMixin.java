package com.cui.mixin.client.pre1_21_6;


import com.cui.CUI;
import net.minecraft.client.gui.components.AbstractStringWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractStringWidget.class)
public class TextMixin {
    // Background
    @Shadow private int color;

    @Inject(at = @At(value = "HEAD"), method = "getColor", cancellable = true)
    private void render(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(CUI.cuiConfig.getRGB());
    }
}
