package com.cui.mixin.client;

import com.cui.CUI_Common;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RecipeBookWidget.class)
public class BookMixin {

    // Background

    @Inject(at = @At(value = "HEAD"), method = "render")
    private void renderHead(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        //context.setShaderColor(CUI_Common.RED + CUI_Common.CBONUS, CUI_Common.GREEN + CUI_Common.CBONUS, CUI_Common.BLUE + CUI_Common.CBONUS, 1);
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V", shift = At.Shift.AFTER), method = "render")
    private void renderTail(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        //context.setShaderColor(1, 1, 1, 1);
    }


    // Tabs

    @Inject(at = @At(value = "INVOKE", target = "Ljava/util/Iterator;hasNext()Z", shift = At.Shift.AFTER), method = "render")
    private void renderHead1(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        //context.setShaderColor(CUI_Common.RED + CUI_Common.CBONUS, CUI_Common.GREEN + CUI_Common.CBONUS, CUI_Common.BLUE + CUI_Common.CBONUS, 1);
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/ToggleButtonWidget;render(Lnet/minecraft/client/gui/DrawContext;IIF)V", shift = At.Shift.AFTER), method = "render")
    private void renderTail1(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        //context.setShaderColor(1, 1, 1, 1);
    }
}
