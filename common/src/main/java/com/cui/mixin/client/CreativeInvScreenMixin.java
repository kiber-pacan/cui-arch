package com.cui.mixin.client;

import com.cui.CUI_Config;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.world.item.CreativeModeTab;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(CreativeModeInventoryScreen.class)
public class CreativeInvScreenMixin {
    /*
    @Shadow
    protected void renderTabIcon(DrawContext context, ItemGroup group) {};
    */

    @Inject(at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V", shift = At.Shift.AFTER), method = "renderTabButton")
    private void renderHead(GuiGraphics guiGraphics, CreativeModeTab creativeModeTab, CallbackInfo ci) {
        guiGraphics.setColor(1, 1, 1, 1);
    }

    @Inject(at = @At(value = "TAIL"), method = "renderTabButton")
    private void renderTail(GuiGraphics guiGraphics, CreativeModeTab creativeModeTab, CallbackInfo ci) {
        Color colors = CUI_Config.HANDLER.instance().color;
        guiGraphics.setColor((float) colors.getRed() / 255, (float) colors.getGreen() / 255, (float) colors.getBlue() / 255, 1);
    }



    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/CreativeModeTab;getType()Lnet/minecraft/world/item/CreativeModeTab$Type;", shift = At.Shift.AFTER), method = "renderBg")
    private void renderHead(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY, CallbackInfo ci) {
        guiGraphics.setColor(1, 1, 1, 1);
    }
}
