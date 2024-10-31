package com.cui.forge.mixin.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.recipebook.RecipeGroupButtonWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.cui.CUI_Common.colors;


@Mixin(RecipeGroupButtonWidget.class)
public class RecipeBookTabsMixin {

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/recipebook/RecipeGroupButtonWidget;isSelected()Z", shift = At.Shift.AFTER), method = "renderButton")
    private void renderHead(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        context.setShaderColor(colors.r, colors.g, colors.b, 1);
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V", shift = At.Shift.AFTER), method = "renderButton")
    private void renderTail(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        context.setShaderColor(1, 1, 1, 1);
    }
}
