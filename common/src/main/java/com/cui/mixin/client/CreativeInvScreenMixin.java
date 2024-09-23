package com.cui.mixin.client;

import com.cui.CUI_Common;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.item.ItemGroup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreativeInventoryScreen.class)
public class CreativeInvScreenMixin {
    @Shadow
    protected void renderTabIcon(DrawContext context, ItemGroup group) {};

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemGroup;getIcon()Lnet/minecraft/item/ItemStack;", shift = At.Shift.AFTER), method = "renderTabIcon")
    private void renderHead(DrawContext context, ItemGroup group, CallbackInfo ci) {
        context.setShaderColor(1, 1, 1, 1);
    }

    @Inject(at = @At(value = "TAIL"), method = "renderTabIcon")
    private void renderTail(DrawContext context, ItemGroup group, CallbackInfo ci) {
        context.setShaderColor(CUI_Common.RED + CUI_Common.CBONUS, CUI_Common.GREEN + CUI_Common.CBONUS, CUI_Common.BLUE + CUI_Common.CBONUS, 1);
    }

}
