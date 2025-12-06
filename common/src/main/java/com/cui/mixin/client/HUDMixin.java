package com.cui.mixin.client;


import com.cui.CUI;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
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

    #if MC_VER >= V1_21_6
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/ResourceLocation;IIIIIIII)V"), method = "renderItemHotbar")
    private static void injected(GuiGraphics instance, RenderPipeline pipeline, ResourceLocation sprite, int textureWidth, int textureHeight, int u, int v, int x, int y, int width, int height) {
        instance.blitSprite(pipeline, sprite, textureWidth, textureHeight, u, v, x, y, width, height, CUI.cuiConfig.getRGB());
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/ResourceLocation;IIII)V"), method = "renderItemHotbar")
    private static void injected(GuiGraphics instance, RenderPipeline pipeline, ResourceLocation sprite, int x, int y, int width, int height) {
        instance.blitSprite(pipeline, sprite, x, y, width, height, CUI.cuiConfig.getRGB());
    }

    @Inject(at = @At(value = "TAIL"), method = "renderHeart")
    private static void injected1(GuiGraphics guiGraphics, Gui.HeartType heartType, int x, int y, boolean hardcore, boolean halfHeart, boolean blinking, CallbackInfo ci) {
        if (heartType == Gui.HeartType.NORMAL) {
            guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, cui$getDetail(hardcore, blinking, halfHeart), x, y, 9, 9);
        }
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/ResourceLocation;IIII)V"), method = "renderHeart")
    private static void injected1(GuiGraphics instance, RenderPipeline pipeline, ResourceLocation sprite, int x, int y, int width, int height) {
        instance.blitSprite(pipeline, sprite, x, y, width, height, CUI.cuiConfig.getRGB());
    }
    #else
    // Hotbar
	@Inject(at = @At(value = "HEAD"), method = #if MC_VER >= V1_21 "renderItemHotbar" #else "renderHotbar" #endif)
	#if MC_VER >= V1_21 private void renderHead(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) #else private void renderHead(float tickDelta, DrawContext context, CallbackInfo ci)#endif {
        #if MC_VER >= V1_21_3
        guiGraphics.flush();
        RenderSystem.setShaderColor(CUI.cuiConfig.r, CUI.cuiConfig.g, CUI.cuiConfig.b, 1);
        #endif
        #if MC_VER <= V1_21_1 guiGraphics.setColor(CUI.cuiConfig.r, CUI.cuiConfig.g, CUI.cuiConfig.b, 1); #endif	}

	@Inject(at = @At(value = "INVOKE", target = #if MC_VER >= V1_21_6 "Lnet/minecraft/client/gui/Gui;renderSlot(Lnet/minecraft/client/gui/GuiGraphics;IILnet/minecraft/client/DeltaTracker;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;I)V" #else "Lcom/mojang/blaze3d/vertex/PoseStack;popPose()V" #endif, shift = At.Shift #if MC_VER >= V1_21_6 .BEFORE #else .AFTER #endif), method = #if MC_VER >= V1_21 "renderItemHotbar" #else "renderHotbar" #endif)
	#if MC_VER >= V1_21 private void renderTail(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) #else private void renderTail(float tickDelta, DrawContext context, CallbackInfo ci) #endif {
        #if MC_VER >= V1_21_3
        guiGraphics.flush();
        RenderSystem.setShaderColor(1, 1, 1, 1);
        #endif
        #if MC_VER <= V1_21_1 guiGraphics.setColor(1, 1, 1, 1); #endif
	}

    // Hearts
    @Inject(at =  @At(value = "HEAD"), method = #if MC_VER >= V1_21 "renderHeart" #else "renderHotbar" #endif)
	private void renderHead(GuiGraphics guiGraphics, Gui.HeartType heartType, int x, int y, boolean hardcore, boolean halfHeart, boolean blinking, CallbackInfo ci) {
        if (heartType == Gui.HeartType.NORMAL) {
            #if MC_VER >= V1_21_3
            guiGraphics.flush();
            RenderSystem.setShaderColor(CUI.cuiConfig.r, CUI.cuiConfig.g, CUI.cuiConfig.b, 1);
            #endif
            #if MC_VER <= V1_21_1 guiGraphics.setColor(CUI.cuiConfig.r, CUI.cuiConfig.g, CUI.cuiConfig.b, 1); #endif
        }
    }

    @Inject(at =  @At(value = "TAIL"), method = #if MC_VER >= V1_21 "renderHeart" #else "renderHotbar" #endif)
    private void renderTail(GuiGraphics guiGraphics, Gui.HeartType heartType, int x, int y, boolean hardcore, boolean halfHeart, boolean blinking, CallbackInfo ci) {
        if (heartType == Gui.HeartType.NORMAL) {
            #if MC_VER >= V1_21_3
            guiGraphics.flush();
            RenderSystem.setShaderColor(1, 1, 1, 1);
            #endif
            #if MC_VER < V1_21_1 guiGraphics.setColor(1, 1, 1, 1); #endif
            #if MC_VER < V1_21_5
            RenderSystem.enableBlend();
            #endif
            #if MC_VER >= V1_21_3
            #if MC_VER >= V1_21_6
            guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, cui$getDetail(hardcore, blinking, halfHeart), x, y, 9, 9);
            #else
            guiGraphics.blitSprite(RenderType::guiTextured, cui$getDetail(hardcore, blinking, halfHeart), x, y, 9, 9);
            #endif
            #else
            guiGraphics.blitSprite(cui$getDetail(hardcore, blinking, halfHeart), x, y, 9, 9);
            #endif
            #if MC_VER < V1_21_5
            RenderSystem.disableBlend();
            #endif
        }
    }
    #endif
    @Shadow
    @Final
    private Minecraft minecraft;

    @Shadow
    public Font getFont() {
        return this.minecraft.font;
    }



    #if MC_VER < V1_21_6
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
        #if MC_VER >= V1_21_3
        #if MC_VER >= V1_21_6

        #else
        guiGraphics.flush();
        RenderSystem.setShaderColor(CUI.cuiConfig.r, CUI.cuiConfig.g, CUI.cuiConfig.b, 1);
        #endif
        #endif
        #if MC_VER <= V1_21_1 guiGraphics.setColor(CUI.cuiConfig.r, CUI.cuiConfig.g, CUI.cuiConfig.b, 1); #endif
    }

    @Inject(at = @At(value = "TAIL"), method = #if MC_VER >= V1_21 "renderExperienceBar" #else "renderHotbar" #endif)
	#if MC_VER >= V1_21 private void renderTail(GuiGraphics guiGraphics, int x, CallbackInfo ci) #else private void renderTail(float tickDelta, DrawContext context, CallbackInfo ci) #endif {
        #if MC_VER >= V1_21_3
        #if MC_VER >= V1_21_6

        #else
        guiGraphics.flush();
        RenderSystem.setShaderColor(1, 1, 1, 1);
        #endif
        #endif
        #if MC_VER <= V1_21_1 guiGraphics.setColor(1, 1, 1, 1); #endif
    }
    #endif
}