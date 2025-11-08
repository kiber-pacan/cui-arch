package com.cui.mixin.client;


import com.cui.CUI;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


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
        guiGraphics.setColor(CUI.cuiConfig.r, CUI.cuiConfig.g, CUI.cuiConfig.b, 1);
	}

	@Inject(at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;popPose()V", shift = At.Shift.AFTER), method = #if MC_VER >= V1_21 "renderItemHotbar" #else "renderHotbar" #endif)
	#if MC_VER >= V1_21 private void renderTail(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) #else private void renderTail(float tickDelta, DrawContext context, CallbackInfo ci) #endif {
		guiGraphics.setColor(1, 1, 1, 1);
	}

    // Hearts
    @Inject(at =  @At(value = "HEAD"), method = #if MC_VER >= V1_21 "renderHeart" #else "renderHotbar" #endif)
	private void renderHead(GuiGraphics guiGraphics, Gui.HeartType heartType, int x, int y, boolean hardcore, boolean halfHeart, boolean blinking, CallbackInfo ci) {
        if (heartType == Gui.HeartType.NORMAL) {
            guiGraphics.setColor(CUI.cuiConfig.r, CUI.cuiConfig.g, CUI.cuiConfig.b, 1);
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
    @Shadow
    @Final
    private Minecraft minecraft;

    @Shadow
    public Font getFont() {
        return this.minecraft.font;
    }

    // XP text
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiling/ProfilerFiller;pop()V", shift = At.Shift.BEFORE), method = #if MC_VER >= V1_21 "renderExperienceLevel" #else "renderHotbar" #endif)
	#if MC_VER >= V1_21 private void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) #else private void renderTail(float tickDelta, DrawContext context, CallbackInfo ci) #endif {
        int i1 = this.minecraft.player.experienceLevel;
        String string1 = "" + i1;
        int j1 = (guiGraphics.guiWidth() - this.getFont().width(string1)) / 2;
        int k1 = guiGraphics.guiHeight() - 31 - 4;
        guiGraphics.drawString(this.getFont(), string1, j1, k1, ((int)(CUI.cuiConfig.r * 255) << 16) | ((int)(CUI.cuiConfig.g * 255) << 8) | (int)(CUI.cuiConfig.b * 255), false);
    }

    // XP bar
    @Inject(at = @At(value = "HEAD"), method = #if MC_VER >= V1_21 "renderExperienceBar" #else "renderHotbar" #endif)
	#if MC_VER >= V1_21 private void renderHead(GuiGraphics guiGraphics, int x, CallbackInfo ci) #else private void renderHead(float tickDelta, DrawContext context, CallbackInfo ci)#endif {
        guiGraphics.setColor(CUI.cuiConfig.r, CUI.cuiConfig.g, CUI.cuiConfig.b, 1);
    }

    @Inject(at = @At(value = "TAIL"), method = #if MC_VER >= V1_21 "renderExperienceBar" #else "renderHotbar" #endif)
	#if MC_VER >= V1_21 private void renderTail(GuiGraphics guiGraphics, int x, CallbackInfo ci) #else private void renderTail(float tickDelta, DrawContext context, CallbackInfo ci) #endif {
        guiGraphics.setColor(1, 1, 1, 1);
    }
}