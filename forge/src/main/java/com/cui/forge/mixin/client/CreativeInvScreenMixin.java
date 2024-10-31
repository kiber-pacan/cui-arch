package com.cui.forge.mixin.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.item.ItemGroup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.cui.CUI_Common.colors;

@Mixin(CreativeInventoryScreen.class)
public class CreativeInvScreenMixin {
    @Shadow
    protected void renderTabIcon(DrawContext context, ItemGroup group) {};


    @Inject(at = @At(value = "TAIL"), method = "renderTabIcon")
    private void renderHead(DrawContext context, ItemGroup group, CallbackInfo ci) {
        context.setShaderColor(colors.r, colors.g, colors.b, 1);
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemGroup;getIcon()Lnet/minecraft/item/ItemStack;", shift = At.Shift.AFTER), method = "renderTabIcon")
    private void renderTail(DrawContext context, ItemGroup group, CallbackInfo ci) {
        context.setShaderColor(1, 1, 1, 1);
    }

    @Inject(at = @At(value = "HEAD"), method = "drawBackground")
    private void renderHead(DrawContext context, float delta, int mouseX, int mouseY, CallbackInfo ci) {
        context.setShaderColor(colors.r, colors.g, colors.b, 1);
    }


    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemGroup;getType()Lnet/minecraft/item/ItemGroup$Type;", shift = At.Shift.AFTER), method = "drawBackground")
    private void renderTail(DrawContext context, float delta, int mouseX, int mouseY, CallbackInfo ci) {
        context.setShaderColor(1, 1, 1, 1);
    }
}
