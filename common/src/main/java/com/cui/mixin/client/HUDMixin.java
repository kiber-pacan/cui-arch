package com.cui.mixin.client;


import com.cui.CUI;
import com.cui.CUI_Config;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;


@Mixin(Gui.class)
public class HUDMixin {

    @Unique private static  final ResourceLocation cui$detail = ResourceLocation.withDefaultNamespace("hud/heart/detail");
    @Unique private static final ResourceLocation cui$detailBlinking = ResourceLocation.withDefaultNamespace("hud/heart/detail_blinking");
    @Unique private static  final ResourceLocation cui$detailHardcoreFull = ResourceLocation.withDefaultNamespace("hud/heart/detail_hardcore_full");
    @Unique private static  final ResourceLocation cui$detailHardcoreFullBlinking = ResourceLocation.withDefaultNamespace("hud/heart/detail_hardcore_full_blinking");
    @Unique private static  final ResourceLocation cui$detailHardcoreHalf = ResourceLocation.withDefaultNamespace("hud/heart/detail_hardcore_half");
    @Unique private static  final ResourceLocation cui$detailHardcoreHalfBlinking = ResourceLocation.withDefaultNamespace("hud/heart/detail_hardcore_half_blinking");

    @Unique
    private static ResourceLocation cui$getDetail(boolean hardcore, boolean halfHeart, boolean blinking) {
        if (!hardcore) {
            return (blinking) ? cui$detailBlinking : cui$detail ;
        } else {
            if (!halfHeart) {
                return (blinking) ? cui$detailHardcoreFullBlinking : cui$detailHardcoreFull ;
            } else {
                return (blinking) ? cui$detailHardcoreHalfBlinking : cui$detailHardcoreHalf ;
            }
        }
    }

    // Hotbar
	@Inject(at = @At(value = "HEAD"), method = #if MC_VER >= V1_21 "renderItemHotbar" #else "renderHotbar" #endif)
	#if MC_VER >= V1_21 private void renderHead(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) #else private void renderHead(float tickDelta, DrawContext context, CallbackInfo ci)#endif {
        Color colors = CUI_Config.HANDLER.instance().color;
		guiGraphics.setColor((float) colors.getRed() / 255, (float) colors.getGreen() / 255, (float) colors.getBlue() / 255, 1);
	}

	@Inject(at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;popPose()V", shift = At.Shift.AFTER), method = #if MC_VER >= V1_21 "renderItemHotbar" #else "renderHotbar" #endif)
	#if MC_VER >= V1_21 private void renderTail(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) #else private void renderTail(float tickDelta, DrawContext context, CallbackInfo ci) #endif {
		guiGraphics.setColor(1, 1, 1, 1);
	}

    @Inject(at =  @At(value = "HEAD"), method = #if MC_VER >= V1_21 "renderHeart" #else "renderHotbar" #endif)
	private void renderHead(GuiGraphics guiGraphics, Gui.HeartType heartType, int x, int y, boolean hardcore, boolean halfHeart, boolean blinking, CallbackInfo ci) {
        if (heartType == Gui.HeartType.NORMAL) {
            Color colors = CUI_Config.HANDLER.instance().color;
            guiGraphics.setColor((float) colors.getRed() / 255, (float) colors.getGreen() / 255, (float) colors.getBlue() / 255, 1);
        }
    }

    @Inject(at =  @At(value = "TAIL"), method = #if MC_VER >= V1_21 "renderHeart" #else "renderHotbar" #endif)
    private void renderTail(GuiGraphics guiGraphics, Gui.HeartType heartType, int x, int y, boolean hardcore, boolean halfHeart, boolean blinking, CallbackInfo ci) {
        if (heartType == Gui.HeartType.NORMAL) {
            guiGraphics.setColor(1, 1, 1, 1);
            RenderSystem.enableBlend();
            guiGraphics.blitSprite(cui$getDetail(hardcore, blinking, halfHeart), x, y, 9, 9);
            RenderSystem.disableBlend();
        }
    }
}