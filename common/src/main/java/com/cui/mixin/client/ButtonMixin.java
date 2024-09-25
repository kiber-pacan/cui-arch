package com.cui.mixin.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.PressableWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.cui.CUI_Common.colors;

@Mixin(PressableWidget.class)
public class ButtonMixin {

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;setShaderColor(FFFF)V", shift = At.Shift.AFTER, ordinal = 0), method = "renderWidget")
    private void renderHead(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        context.setShaderColor(colors.r + 0.3f, colors.g + 0.3f, colors.b + 0.3f, 1);
    }
}