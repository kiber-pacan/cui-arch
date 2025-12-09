package com.cui.neoforge.mixin.client;


import com.cui.CUI;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
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
    private static void injected0(GuiGraphics instance, RenderPipeline pipeline, ResourceLocation sprite, int textureWidth, int textureHeight, int u, int v, int x, int y, int width, int height) {
        instance.blitSprite(pipeline, sprite, textureWidth, textureHeight, u, v, x, y, width, height, CUI.cuiConfig.getRGB());
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/ResourceLocation;IIII)V"), method = "renderItemHotbar")
    private static void injected1(GuiGraphics instance, RenderPipeline pipeline, ResourceLocation sprite, int x, int y, int width, int height) {
        instance.blitSprite(pipeline, sprite, x, y, width, height, CUI.cuiConfig.getRGB());
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/ResourceLocation;IIIIIIII)V"), method = "renderCrosshair")
    private static void injected2(GuiGraphics instance, RenderPipeline pipeline, ResourceLocation sprite, int textureWidth, int textureHeight, int u, int v, int x, int y, int width, int height) {
        instance.blitSprite(RenderPipelines.GUI_TEXTURED, sprite, textureWidth, textureHeight, u, v, x, y, width, height, CUI.cuiConfig.getRGBA());
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/ResourceLocation;IIII)V"), method = "renderCrosshair")
    private static void injected3(GuiGraphics instance, RenderPipeline pipeline, ResourceLocation sprite, int x, int y, int width, int height) {
        instance.blitSprite(RenderPipelines.GUI_TEXTURED, sprite, x, y, width, height, CUI.cuiConfig.getRGBA());
    }



    // Health
    @Inject(at = @At(value = "HEAD"), method = "renderHeart", cancellable = true)
    private void injected4(GuiGraphics guiGraphics, Gui.HeartType heartType, int x, int y, boolean hardcore, boolean halfHeart, boolean blinking, CallbackInfo ci) {
        if (heartType == Gui.HeartType.NORMAL || heartType == Gui.HeartType.CONTAINER) {
            guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, heartType.getSprite(hardcore, blinking, halfHeart), x, y, 9, 9, CUI.cuiConfig.getRGB());
            if (heartType != Gui.HeartType.CONTAINER) {
                guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, cui$getDetail(hardcore, blinking, halfHeart), x, y, 9, 9);
            }
        } else {
            guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, heartType.getSprite(hardcore, blinking, halfHeart), x, y, 9, 9);
        }

        ci.cancel();
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/ResourceLocation;IIII)V"), method = "renderHeart")
    private static void injected5(GuiGraphics instance, RenderPipeline pipeline, ResourceLocation sprite, int x, int y, int width, int height) {
        instance.blitSprite(pipeline, sprite, x, y, width, height, CUI.cuiConfig.getRGB());
    }

    // Air
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/ResourceLocation;IIII)V", ordinal = 0), method = "renderAirBubbles")
    private static void injected6(GuiGraphics instance, RenderPipeline pipeline, ResourceLocation sprite, int x, int y, int width, int height) {
        instance.blitSprite(pipeline, ResourceLocation.withDefaultNamespace("hud/air_container"), x, y, width, height);
        instance.blitSprite(pipeline, sprite, x, y, width, height, CUI.cuiConfig.getRGB());
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/ResourceLocation;IIII)V", ordinal = 1), method = "renderAirBubbles")
    private static void injected7(GuiGraphics instance, RenderPipeline pipeline, ResourceLocation sprite, int x, int y, int width, int height) {
        instance.blitSprite(pipeline, ResourceLocation.withDefaultNamespace("hud/air_bursting_container"), x, y, width, height);
        instance.blitSprite(pipeline, sprite, x, y, width, height, CUI.cuiConfig.getRGB());
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/ResourceLocation;IIII)V", ordinal = 2), method = "renderAirBubbles")
    private static void injected8(GuiGraphics instance, RenderPipeline pipeline, ResourceLocation sprite, int x, int y, int width, int height) {
        instance.blitSprite(pipeline, ResourceLocation.withDefaultNamespace("hud/air_empty_container"), x, y, width, height, CUI.cuiConfig.getRGB());
        instance.blitSprite(pipeline, sprite, x, y, width, height, CUI.cuiConfig.getRGB());
    }

    // Armor
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/ResourceLocation;IIII)V", ordinal = 0), method = "renderArmor")
    private static void injected9(GuiGraphics instance, RenderPipeline pipeline, ResourceLocation sprite, int x, int y, int width, int height) {
        instance.blitSprite(pipeline, ResourceLocation.withDefaultNamespace("hud/armor_container"), x, y, width, height, CUI.cuiConfig.getRGB());
        instance.blitSprite(pipeline, sprite, x, y, width, height, CUI.cuiConfig.getRGB());
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/ResourceLocation;IIII)V", ordinal = 1), method = "renderArmor")
    private static void injected10(GuiGraphics instance, RenderPipeline pipeline, ResourceLocation sprite, int x, int y, int width, int height) {
        instance.blitSprite(pipeline, ResourceLocation.withDefaultNamespace("hud/armor_container_half"), x, y, width, height, CUI.cuiConfig.getRGB());
        instance.blitSprite(pipeline, sprite, x, y, width, height, CUI.cuiConfig.getRGB());
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/ResourceLocation;IIII)V", ordinal = 2), method = "renderArmor")
    private static void injected11(GuiGraphics instance, RenderPipeline pipeline, ResourceLocation sprite, int x, int y, int width, int height) {
        instance.blitSprite(pipeline, sprite, x, y, width, height, CUI.cuiConfig.getRGB());
    }

    // Food
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/ResourceLocation;IIII)V", ordinal = 0), method = "renderFood")
    private static void injected12(GuiGraphics instance, RenderPipeline pipeline, ResourceLocation sprite, int x, int y, int width, int height) {
        instance.blitSprite(pipeline, sprite, x, y, width, height, CUI.cuiConfig.getRGB());
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/ResourceLocation;IIII)V", ordinal = 1), method = "renderFood")
    private static void injected13(GuiGraphics instance, RenderPipeline pipeline, ResourceLocation sprite, int x, int y, int width, int height) {
        instance.blitSprite(pipeline, sprite, x, y, width, height, CUI.cuiConfig.getRGB());

        if (sprite.toString().contains("hunger")) {
            instance.blitSprite(pipeline, ResourceLocation.withDefaultNamesRenderSystempace("hud/food_full_hunger_bone"), x, y, width, height);
        } else {
            instance.blitSprite(pipeline, ResourceLocation.withDefaultNamespace("hud/food_full_bone"), x, y, width, height);
        }
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/ResourceLocation;IIII)V", ordinal = 2), method = "renderFood")
    private static void injected14(GuiGraphics instance, RenderPipeline pipeline, ResourceLocation sprite, int x, int y, int width, int height) {
        instance.blitSprite(pipeline, sprite, x, y, width, height, CUI.cuiConfig.getRGB());

        if (sprite.toString().contains("hunger")) {
            instance.blitSprite(pipeline, ResourceLocation.withDefaultNamespace("hud/food_half_hunger_bone"), x, y, width, height);
        } else {
            instance.blitSprite(pipeline, ResourceLocation.withDefaultNamespace("hud/food_half_bone"), x, y, width, height);
        }
    }


    @Shadow private ItemStack lastToolHighlight = ItemStack.EMPTY;

    // Selected item hotbar name
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawStringWithBackdrop(Lnet/minecraft/client/gui/Font;Lnet/minecraft/network/chat/Component;IIII)V"), method = "renderSelectedItemName(Lnet/minecraft/client/gui/GuiGraphics;I)V")
    private void injected15(GuiGraphics instance, Font font, Component component, int x, int y, int width, int color) {
        MutableComponent customComponent;
        int customColor = CUI.cuiConfig.getRGB();

        if (this.lastToolHighlight.getRarity() == Rarity.COMMON) {
            customComponent = Component.empty().append(this.lastToolHighlight.getHoverName()).withColor(customColor);
        } else {
            customComponent = Component.empty().append(this.lastToolHighlight.getHoverName()).withStyle(this.lastToolHighlight.getRarity().color());
        }

        if (this.lastToolHighlight.has(DataComponents.CUSTOM_NAME)) {
            customComponent.withStyle(ChatFormatting.ITALIC);
        }

        instance.drawStringWithBackdrop(font, customComponent, x, y, width, customColor);
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/ResourceLocation;IIII)V"), method = "renderEffects")
    private static void injected16(GuiGraphics instance, RenderPipeline pipeline, ResourceLocation sprite, int x, int y, int width, int height) {
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