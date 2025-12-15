package com.cui.mixin.client.misc.main;


import com.cui.abs.core.data.Pair;
import com.cui.abs.core.data.ResourceBridge;
import com.cui.abs.core.rendering.data.RenderPipelineBridge;
import com.cui.abs.core.rendering.gui.GuiRenderer;
import com.cui.abs.core.data.Rectangle;
import com.cui.core.CUI;
#if MC_VER >= V1_21_6
import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.ChatFormatting;
#endif

#if MC_VER >= V1_21
import net.minecraft.ChatFormatting;
import net.minecraft.client.DeltaTracker;
import net.minecraft.core.component.DataComponents;
#endif

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

#if MC_VER <= V1_21_6
#endif

#if MC_VER <= V1_21_5
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.RenderType;
import java.util.function.Function;
#endif

import org.spongepowered.asm.mixin.injection.At;


@Debug(export = true)
@Mixin(Gui.class)
public class HUDMixin {
    @Unique private static final ResourceLocation cui$detail = ResourceBridge.spriteNamespace(CUI.MOD_ID, "hud/heart/detail");
    @Unique private static final ResourceLocation cui$detailBlinking = ResourceBridge.spriteNamespace(CUI.MOD_ID, "hud/heart/detail_blinking");
    @Unique private static final ResourceLocation cui$detailHardcoreFull = ResourceBridge.spriteNamespace(CUI.MOD_ID, "hud/heart/detail_hardcore_full");
    @Unique private static final ResourceLocation cui$detailHardcoreFullBlinking = ResourceBridge.spriteNamespace(CUI.MOD_ID, "hud/heart/detail_hardcore_full_blinking");
    @Unique private static final ResourceLocation cui$detailHardcoreHalf = ResourceBridge.spriteNamespace(CUI.MOD_ID, "hud/heart/detail_hardcore_half");
    @Unique private static final ResourceLocation cui$detailHardcoreHalfBlinking = ResourceBridge.spriteNamespace(CUI.MOD_ID, "hud/heart/detail_hardcore_half_blinking");

    @Shadow @Final private Minecraft minecraft;

    @Shadow public Font getFont() {
        return this.minecraft.font;
    }

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

    /**
     *  <h5> Experience bar and text coloring <h5/>
     */
    #if MC_VER < V1_21_6
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiling/ProfilerFiller;pop()V", shift = At.Shift.BEFORE), method = #if MC_VER >= V1_21 "renderExperienceLevel" #else "renderExperienceBar" #endif)
	#if MC_VER >= V1_21 private void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) #else private void render(GuiGraphics guiGraphics, int x, CallbackInfo ci) #endif {
        int i1 = this.minecraft.player.experienceLevel;
        String string1 = "" + i1;
        int j1 = (guiGraphics.guiWidth() - this.getFont().width(string1)) / 2;
        int k1 = guiGraphics.guiHeight() - 31 - 4;
        if (this.minecraft.player.experienceLevel > 0) {
            guiGraphics.drawString(this.getFont(), string1, j1, k1, CUI.cuiConfig.getRGB(), false);
        }
    }

    @Inject(at = @At(value = "HEAD"), method = #if MC_VER >= V1_21 "renderExperienceBar" #else "renderExperienceBar" #endif)
	#if MC_VER >= V1_21 private void renderExperienceBar1(GuiGraphics guiGraphics, int x, CallbackInfo ci) #else private void renderExperienceBarHead(GuiGraphics guiGraphics, int x, CallbackInfo ci)#endif {
        GuiRenderer.setShaderColor(guiGraphics, CUI.cuiConfig.getRGB());
    }

    @Inject(at = @At(value = "TAIL"), method = #if MC_VER >= V1_21 "renderExperienceBar" #else "renderExperienceBar" #endif)
	#if MC_VER >= V1_21 private void renderExperienceBar2(GuiGraphics guiGraphics, int x, CallbackInfo ci) #else private void renderExperienceBarTail(GuiGraphics guiGraphics, int x, CallbackInfo ci)#endif {
        GuiRenderer.clearShaderColor(guiGraphics);
    }
    #endif


    /**
     * <h5> Hotbar coloring <h5/>
     * */
    @Redirect(at = @At(value = "INVOKE", target = #if MC_VER >= V1_20_4 "Lnet/minecraft/client/gui/GuiGraphics;blitSprite("#if MC_VER >= V1_21_6 + "Lcom/mojang/blaze3d/pipeline/RenderPipeline;" #elif MC_VER >= V1_21_3 + "Ljava/util/function/Function;" #endif + "Lnet/minecraft/resources/ResourceLocation;IIIIIIII)V" #else "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIIIII)V" #endif), method = #if MC_VER >= V1_21 "renderItemHotbar" #else "renderHotbar" #endif)
    private void renderHotbar1(
            GuiGraphics instance,
            #if MC_VER >= V1_21_6 RenderPipeline pipeline, #elif MC_VER >= V1_21_3 Function<ResourceLocation, RenderType> pipeline, #endif ResourceLocation sprite,
            #if MC_VER >= V1_20_4
            int textureWidth, int textureHeight, int u, int v, int x, int y, int width, int height
            #else
            int x, int y, int u, int v, int textureWidth, int textureHeight
            #endif
    ) {
        GuiRenderer.blitSprite(instance, "GUI_TEXTURED", sprite, new Rectangle(x, y, #if MC_VER >= V1_20_4 width, height #else textureWidth, textureHeight #endif), new Rectangle(u, v, textureWidth, textureHeight), CUI.cuiConfig.getRGB());
    }

    #if MC_VER > V1_20_1
    @Redirect(at = @At(value = "INVOKE", target = #if MC_VER >= V1_20_4 "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(" #if MC_VER >= V1_21_6 + "Lcom/mojang/blaze3d/pipeline/RenderPipeline;" #elif MC_VER >= V1_21_3 + "Ljava/util/function/Function;" #endif + "Lnet/minecraft/resources/ResourceLocation;IIII)V" #else "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIIIII)V" #endif), method = #if MC_VER >= V1_21 "renderItemHotbar" #else "renderHotbar" #endif)
    private void renderHotbar2(
            GuiGraphics instance,
            #if MC_VER >= V1_21_6 RenderPipeline pipeline, #elif MC_VER >= V1_21_3 Function<ResourceLocation, RenderType> pipeline, #endif
            ResourceLocation sprite,
            #if MC_VER >= V1_20_4
            int x, int y, int width, int height
            #else
            int x, int y, int u, int v, int textureWidth, int textureHeight
            #endif
    ) {
        GuiRenderer.blitSprite(instance, "GUI_TEXTURED", sprite, new Rectangle(x, y, #if MC_VER >= V1_20_4 width, height #else textureWidth, textureHeight #endif), #if MC_VER <= V1_20_1 new Rectangle(u, v, textureWidth, textureHeight), #endif CUI.cuiConfig.getRGB());
    }
    #endif


    /**
     * <h5> Crosshair coloring <h5/>
     * */
    @Redirect(at = @At(value = "INVOKE", target = #if MC_VER >= V1_20_4 "Lnet/minecraft/client/gui/GuiGraphics;blitSprite("#if MC_VER >= V1_21_6 + "Lcom/mojang/blaze3d/pipeline/RenderPipeline;" #elif MC_VER >= V1_21_3 + "Ljava/util/function/Function;" #endif + "Lnet/minecraft/resources/ResourceLocation;IIIIIIII)V" #else "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIIIII)V" #endif), method = "renderCrosshair")
    private void injected2(
            GuiGraphics instance,
            #if MC_VER >= V1_21_6 RenderPipeline pipeline, #elif MC_VER >= V1_21_3 Function<ResourceLocation, RenderType> pipeline, #endif
            ResourceLocation sprite,
            #if MC_VER >= V1_20_4
            int textureWidth, int textureHeight, int u, int v, int x, int y, int width, int height
            #else
            int x, int y, int u, int v, int textureWidth, int textureHeight
            #endif
    ) {
        GuiRenderer.blitSprite(instance, "GUI_TEXTURED", sprite, new Rectangle(x, y, #if MC_VER >= V1_20_4 width, height #else textureWidth, textureHeight #endif), new Rectangle(u, v, textureWidth, textureHeight), CUI.cuiConfig.getRGB());
    }

    #if MC_VER > V1_20_1
    @Redirect(at = @At(value = "INVOKE", target = #if MC_VER >= V1_20_4 "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(" #if MC_VER >= V1_21_6 + "Lcom/mojang/blaze3d/pipeline/RenderPipeline;" #elif MC_VER >= V1_21_3 + "Ljava/util/function/Function;" #endif + "Lnet/minecraft/resources/ResourceLocation;IIII)V" #else "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIIIII)V" #endif), method = "renderCrosshair")
    private void injected3(
            GuiGraphics instance,
            #if MC_VER >= V1_21_6 RenderPipeline pipeline, #elif MC_VER >= V1_21_3 Function<ResourceLocation, RenderType> pipeline, #endif
            ResourceLocation sprite,
            #if MC_VER >= V1_20_4 int x, int y, int width, int height
            #else
            int x, int y, int u, int v, int textureWidth, int textureHeight
            #endif
    ) {
        GuiRenderer.blitSprite(instance, "GUI_TEXTURED", sprite, new Rectangle(x, y, #if MC_VER >= V1_20_4 width, height #else textureWidth, textureHeight #endif), #if MC_VER <= V1_20_1 new Rectangle(u, v, textureWidth, textureHeight), #endif CUI.cuiConfig.getRGB());
    }
    #endif

    /**
     * <h5> Hearts coloring <h5/>
     * */

    #if MC_VER <= V1_20_1 @Shadow  #if LOADER != FORGE @Final #endif private static ResourceLocation GUI_ICONS_LOCATION = new ResourceLocation("textures/gui/icons.png"); #endif

    @Inject(at = @At(value = "HEAD"), method = "renderHeart", cancellable = true)
    private void injected4(
            GuiGraphics guiGraphics,
            Gui.HeartType heartType,
            int x, int y,
            #if MC_VER >= V1_20_4
            boolean hardcore,
            #else
            int yOffset,
            #endif
            boolean blinking, boolean halfHeart,
            CallbackInfo ci
    ) {
        #if MC_VER >= V1_20_4
        if (heartType == Gui.HeartType.NORMAL || heartType == Gui.HeartType.CONTAINER) {
            GuiRenderer.blitSprite(guiGraphics, "GUI_TEXTURED", heartType.getSprite(hardcore, halfHeart, blinking), new Rectangle(x, y, 9, 9), CUI.cuiConfig.getRGB());
            if (heartType != Gui.HeartType.CONTAINER) {
                GuiRenderer.blitSprite(guiGraphics, "GUI_TEXTURED", cui$getDetail(hardcore, halfHeart, blinking), new Rectangle(x, y, 9, 9));
            }
        } else {
            GuiRenderer.blitSprite(guiGraphics, "GUI_TEXTURED", heartType.getSprite(hardcore, halfHeart, blinking), new Rectangle(x, y, 9, 9), CUI.cuiConfig.getRGB());
        }

        ci.cancel();
        #else
        if (heartType == Gui.HeartType.NORMAL) {
            String texture = (halfHeart) ? ((blinking) ? "hud/heart/half_blinking" : "hud/heart/half") : ((blinking) ? "hud/heart/full_blinking" : "hud/heart/full");
            GuiRenderer.blitSprite(guiGraphics, "GUI_TEXTURED", ResourceBridge.spriteNamespace(CUI.MOD_ID, texture), new Rectangle(x, y, 9, 9), CUI.cuiConfig.getRGB());
            GuiRenderer.blitSprite(guiGraphics, "GUI_TEXTURED", cui$getDetail(Minecraft.getInstance().player.level().getLevelData().isHardcore(), halfHeart, blinking), new Rectangle(x, y, 9, 9));
        } else if (heartType == Gui.HeartType.CONTAINER) {
            GuiRenderer.blitSprite(guiGraphics, "GUI_TEXTURED", ResourceBridge.spriteNamespace(CUI.MOD_ID, "hud/heart/container"), new Rectangle(x, y, 9, 9), CUI.cuiConfig.getRGB());
        } else {
            guiGraphics.blit(GUI_ICONS_LOCATION, x, y, heartType.getX(halfHeart, blinking), yOffset, 9, 9);
        }

        ci.cancel();
        #endif
    }

    #if MC_VER >= V1_20_4
    @Redirect(at = @At(value = "INVOKE", target = #if MC_VER >= V1_20_4 "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(" #if MC_VER >= V1_21_6 + "Lcom/mojang/blaze3d/pipeline/RenderPipeline;" #elif MC_VER >= V1_21_3 + "Ljava/util/function/Function;" #endif + "Lnet/minecraft/resources/ResourceLocation;IIII)V" #else "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIIIII)V" #endif), method = "renderHeart")
    private void injected5(GuiGraphics instance, #if MC_VER >= V1_21_6 RenderPipeline pipeline, #elif MC_VER >= V1_21_3 Function<ResourceLocation, RenderType> pipeline, #endif ResourceLocation sprite, #if MC_VER >= V1_20_4 int x, int y, int width, int height #else int x, int y, int uOffset, int vOffset, int uWidth, int vHeight #endif) {
        GuiRenderer.blitSprite(instance, "GUI_TEXTURED", sprite, new Rectangle(x, y, width, height), CUI.cuiConfig.getRGB());
    }
    #endif

    #if LOADER != FORGE
    /**
     * <h5> Air bubbles coloring <h5/>
     * */
    #if LOADER != NEOFORGE
    @Redirect(at = @At(value = "INVOKE", target = #if MC_VER >= V1_20_4 "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(" #if MC_VER >= V1_21_6 + "Lcom/mojang/blaze3d/pipeline/RenderPipeline;" #elif MC_VER >= V1_21_3 + "Ljava/util/function/Function;" #endif + "Lnet/minecraft/resources/ResourceLocation;IIII)V" #else "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIIIII)V" #endif, ordinal = #if MC_VER >= V1_20_4 0 #else 6 #endif), method = #if MC_VER >= V1_21_3 "renderAirBubbles" #else "renderPlayerHealth" #endif)
    private void injected6(
            GuiGraphics instance,
            #if MC_VER >= V1_21_6 RenderPipeline pipeline, #elif MC_VER >= V1_21_3 Function<ResourceLocation, RenderType> pipeline, #endif
            ResourceLocation sprite,
            #if MC_VER >= V1_20_4
            int x, int y, int width, int height
            #else
            int x, int y, int u, int v, int textureWidth, int textureHeight
            #endif
    ) {
        GuiRenderer.blitSprite(instance, "GUI_TEXTURED", ResourceBridge.spriteNamespace(CUI.MOD_ID, "hud/air_container"), new Rectangle(x, y, #if MC_VER >= V1_20_4 width, height #else textureWidth, textureHeight #endif), CUI.cuiConfig.getRGB());
        GuiRenderer.blitSprite(instance, "GUI_TEXTURED", ResourceBridge.spriteNamespace(CUI.MOD_ID, "hud/air"), new Rectangle(x, y, #if MC_VER >= V1_20_4 width, height #else textureWidth, textureHeight #endif), CUI.cuiConfig.getRGB());
    }

    @Redirect(at = @At(value = "INVOKE", target = #if MC_VER >= V1_20_4 "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(" #if MC_VER >= V1_21_6 + "Lcom/mojang/blaze3d/pipeline/RenderPipeline;" #elif MC_VER >= V1_21_3 + "Ljava/util/function/Function;" #endif + "Lnet/minecraft/resources/ResourceLocation;IIII)V" #else "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIIIII)V" #endif, ordinal = #if MC_VER >= V1_20_4 1 #else 7 #endif), method = #if MC_VER >= V1_21_3 "renderAirBubbles" #else "renderPlayerHealth" #endif)
    private void injected7(
            GuiGraphics instance,
            #if MC_VER >= V1_21_6 RenderPipeline pipeline, #elif MC_VER >= V1_21_3 Function<ResourceLocation, RenderType> pipeline, #endif
            ResourceLocation sprite,
            #if MC_VER >= V1_20_4
            int x, int y, int width, int height
            #else
            int x, int y, int u, int v, int textureWidth, int textureHeight
            #endif
    ) {
        GuiRenderer.blitSprite(instance, "GUI_TEXTURED", ResourceBridge.spriteNamespace(CUI.MOD_ID, "hud/air_bursting_container"), new Rectangle(x, y, #if MC_VER >= V1_20_4 width, height #else textureWidth, textureHeight #endif), CUI.cuiConfig.getRGB());
        GuiRenderer.blitSprite(instance, "GUI_TEXTURED", ResourceBridge.spriteNamespace(CUI.MOD_ID, "hud/air_bursting"), new Rectangle(x, y, #if MC_VER >= V1_20_4 width, height #else textureWidth, textureHeight #endif), CUI.cuiConfig.getRGB());
    }
    #endif

    #if MC_VER >= V1_21_3
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(" #if MC_VER >= V1_21_6 + "Lcom/mojang/blaze3d/pipeline/RenderPipeline;" #elif MC_VER >= V1_21_3 + "Ljava/util/function/Function;" #endif + "Lnet/minecraft/resources/ResourceLocation;IIII)V", ordinal = 2), method = #if MC_VER >= V1_21_3 "renderAirBubbles" #else "renderPlayerHealth" #endif)
    private void injected8(
            GuiGraphics instance,
            #if MC_VER >= V1_21_6 RenderPipeline pipeline, #elif MC_VER >= V1_21_3 Function<ResourceLocation, RenderType> pipeline, #endif
            ResourceLocation sprite, int x, int y, int width, int height
    ) {
        GuiRenderer.blitSprite(instance, "GUI_TEXTURED", ResourceBridge.spriteNamespace(CUI.MOD_ID, "hud/air_empty_container"), new Rectangle(x, y, width, height), CUI.cuiConfig.getRGB());
        GuiRenderer.blitSprite(instance, "GUI_TEXTURED", sprite, new Rectangle(x, y, width, height), CUI.cuiConfig.getRGB());
    }
    #endif

    /**
     * <h5> Armor coloring <h5/>
     * */
    @Redirect(at = @At(value = "INVOKE", target = #if MC_VER >= V1_20_4 "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(" #if MC_VER >= V1_21_6 + "Lcom/mojang/blaze3d/pipeline/RenderPipeline;" #elif MC_VER >= V1_21_3 + "Ljava/util/function/Function;" #endif + "Lnet/minecraft/resources/ResourceLocation;IIII)V" #else "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIIIII)V" #endif, ordinal = 0), method = #if MC_VER >= V1_21 "renderArmor" #else "renderPlayerHealth" #endif)
    private #if MC_VER >= V1_20_4 static #endif void renderArmor1(
            GuiGraphics instance,
            #if MC_VER >= V1_21_6 RenderPipeline pipeline, #elif MC_VER >= V1_21_3 Function<ResourceLocation, RenderType> pipeline, #endif
            ResourceLocation sprite,
            #if MC_VER >= V1_20_4
            int x, int y, int width, int height
            #else
            int x, int y, int u, int v, int textureWidth, int textureHeight
            #endif
    ) {
        GuiRenderer.blitSprite(instance, "GUI_TEXTURED", ResourceBridge.spriteNamespace(CUI.MOD_ID, "hud/armor_container_half"), new Rectangle(x, y, #if MC_VER >= V1_20_4 width, height #else textureWidth, textureHeight #endif), CUI.cuiConfig.getRGB());
        GuiRenderer.blitSprite(instance, "GUI_TEXTURED", ResourceBridge.spriteNamespace(CUI.MOD_ID, "hud/armor_full"), new Rectangle(x, y, #if MC_VER >= V1_20_4 width, height #else textureWidth, textureHeight #endif), CUI.cuiConfig.getRGB());
    }

    @Redirect(at = @At(value = "INVOKE", target = #if MC_VER >= V1_20_4 "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(" #if MC_VER >= V1_21_6 + "Lcom/mojang/blaze3d/pipeline/RenderPipeline;" #elif MC_VER >= V1_21_3 + "Ljava/util/function/Function;" #endif + "Lnet/minecraft/resources/ResourceLocation;IIII)V" #else "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIIIII)V" #endif, ordinal = 1), method = #if MC_VER >= V1_21 "renderArmor" #else "renderPlayerHealth" #endif)
    private #if MC_VER >= V1_20_4 static #endif void renderArmor2(
            GuiGraphics instance,
            #if MC_VER >= V1_21_6 RenderPipeline pipeline, #elif MC_VER >= V1_21_3 Function<ResourceLocation, RenderType> pipeline, #endif
            ResourceLocation sprite,
            #if MC_VER >= V1_20_4
            int x, int y, int width, int height
            #else
            int x, int y, int u, int v, int textureWidth, int textureHeight
            #endif
    ) {
        GuiRenderer.blitSprite(instance, "GUI_TEXTURED", ResourceBridge.spriteNamespace(CUI.MOD_ID, "hud/armor_container_half"), new Rectangle(x, y, #if MC_VER >= V1_20_4 width, height #else textureWidth, textureHeight #endif), CUI.cuiConfig.getRGB());
        GuiRenderer.blitSprite(instance, "GUI_TEXTURED", ResourceBridge.spriteNamespace(CUI.MOD_ID, "hud/armor_half"), new Rectangle(x, y, #if MC_VER >= V1_20_4 width, height #else textureWidth, textureHeight #endif), CUI.cuiConfig.getRGB());
    }

    @Redirect(at = @At(value = "INVOKE", target = #if MC_VER >= V1_20_4 "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(" #if MC_VER >= V1_21_6 + "Lcom/mojang/blaze3d/pipeline/RenderPipeline;" #elif MC_VER >= V1_21_3 + "Ljava/util/function/Function;" #endif + "Lnet/minecraft/resources/ResourceLocation;IIII)V" #else "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIIIII)V" #endif, ordinal = 2), method = #if MC_VER >= V1_21 "renderArmor" #else "renderPlayerHealth" #endif)
    private #if MC_VER >= V1_20_4 static #endif void renderArmor3(
            GuiGraphics instance,
            #if MC_VER >= V1_21_6 RenderPipeline pipeline, #elif MC_VER >= V1_21_3 Function<ResourceLocation, RenderType> pipeline, #endif
            ResourceLocation sprite,
            #if MC_VER >= V1_20_4
            int x, int y, int width, int height
            #else
            int x, int y, int u, int v, int textureWidth, int textureHeight
            #endif
    ) {
        GuiRenderer.blitSprite(instance, "GUI_TEXTURED", ResourceBridge.spriteNamespace(CUI.MOD_ID, "armor_container"), new Rectangle(x, y, #if MC_VER >= V1_20_4 width, height #else textureWidth, textureHeight #endif), CUI.cuiConfig.getRGB());
    }

    /**
     * <h5> Food coloring <h5/>
     * */
    @Redirect(at = @At(value = "INVOKE", target = #if MC_VER >= V1_20_4 "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(" #if MC_VER >= V1_21_6 + "Lcom/mojang/blaze3d/pipeline/RenderPipeline;" #elif MC_VER >= V1_21_3 + "Ljava/util/function/Function;" #endif + "Lnet/minecraft/resources/ResourceLocation;IIII)V" #else "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIIIII)V" #endif, ordinal = #if MC_VER >= V1_20_4 0 #else 3 #endif), method = #if MC_VER >= V1_21 "renderFood" #else "renderPlayerHealth" #endif)
    private  void renderFood1(
            GuiGraphics instance,
            #if MC_VER >= V1_21_6 RenderPipeline pipeline, #elif MC_VER >= V1_21_3 Function<ResourceLocation, RenderType> pipeline, #endif
            ResourceLocation sprite,
            #if MC_VER >= V1_20_4
            int x, int y, int width, int height
            #else
            int x, int y, int u, int v, int textureWidth, int textureHeight
            #endif
    ) {
        String hunger = (sprite.toString().contains("hunger")) ? "_hunger" : "";
        GuiRenderer.blitSprite(instance, "GUI_TEXTURED", ResourceBridge.spriteNamespace(CUI.MOD_ID, "hud/food_empty" + hunger), new Rectangle(x, y, #if MC_VER >= V1_20_4 width, height #else textureWidth, textureHeight #endif), CUI.cuiConfig.getRGB());
    }

    @Redirect(at = @At(value = "INVOKE", target = #if MC_VER >= V1_20_4 "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(" #if MC_VER >= V1_21_6 + "Lcom/mojang/blaze3d/pipeline/RenderPipeline;" #elif MC_VER >= V1_21_3 + "Ljava/util/function/Function;" #endif + "Lnet/minecraft/resources/ResourceLocation;IIII)V" #else "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIIIII)V" #endif, ordinal = #if MC_VER >= V1_20_4 1 #else 4 #endif), method = #if MC_VER >= V1_21 "renderFood" #else "renderPlayerHealth" #endif)
    private void renderFood2(
            GuiGraphics instance,
            #if MC_VER >= V1_21_6 RenderPipeline pipeline, #elif MC_VER >= V1_21_3 Function<ResourceLocation, RenderType> pipeline, #endif
            ResourceLocation sprite,
            #if MC_VER >= V1_20_4
            int x, int y, int width, int height
            #else
            int x, int y, int u, int v, int textureWidth, int textureHeight
            #endif
    ) {
        String hunger = (sprite.toString().contains("hunger")) ? "_hunger" : "";
        GuiRenderer.blitSprite(instance, "GUI_TEXTURED", ResourceBridge.spriteNamespace(CUI.MOD_ID, "hud/food_full" + hunger), new Rectangle(x, y, #if MC_VER >= V1_20_4 width, height #else textureWidth, textureHeight #endif), CUI.cuiConfig.getRGB());
        GuiRenderer.blitSprite(instance, "GUI_TEXTURED", ResourceBridge.spriteNamespace(CUI.MOD_ID, "hud/food_full" + hunger + "_bone"), new Rectangle(x, y, #if MC_VER >= V1_20_4 width, height #else textureWidth, textureHeight #endif), CUI.cuiConfig.getRGB());

    }

    @Redirect(at = @At(value = "INVOKE", target = #if MC_VER >= V1_20_4 "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(" #if MC_VER >= V1_21_6 + "Lcom/mojang/blaze3d/pipeline/RenderPipeline;" #elif MC_VER >= V1_21_3 + "Ljava/util/function/Function;" #endif + "Lnet/minecraft/resources/ResourceLocation;IIII)V" #else "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIIIII)V" #endif, ordinal = #if MC_VER >= V1_20_4 2 #else 5 #endif), method = #if MC_VER >= V1_21 "renderFood" #else "renderPlayerHealth" #endif)
    private void renderFood3(
            GuiGraphics instance,
            #if MC_VER >= V1_21_6 RenderPipeline pipeline, #elif MC_VER >= V1_21_3 Function<ResourceLocation, RenderType> pipeline, #endif
            ResourceLocation sprite,
            #if MC_VER >= V1_20_4
            int x, int y, int width, int height
             #else
            int x, int y, int u, int v, int textureWidth, int textureHeight
            #endif
    ) {
        String hunger = (sprite.toString().contains("hunger")) ? "_hunger" : "";
        GuiRenderer.blitSprite(instance, "GUI_TEXTURED", ResourceBridge.spriteNamespace(CUI.MOD_ID, "hud/food_half" + hunger), new Rectangle(x, y, #if MC_VER >= V1_20_4 width, height #else textureWidth, textureHeight #endif), CUI.cuiConfig.getRGB());
        GuiRenderer.blitSprite(instance, "GUI_TEXTURED", ResourceBridge.spriteNamespace(CUI.MOD_ID, "hud/food_half" + hunger + "_bone"), new Rectangle(x, y, #if MC_VER >= V1_20_4 width, height #else textureWidth, textureHeight #endif), CUI.cuiConfig.getRGB());
    }
    #endif
}