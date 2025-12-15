package com.cui.neoforge.mixin.client.post1_21_6.misc;


import com.cui.abs.core.data.Rectangle;
import com.cui.abs.core.data.ResourceBridge;
import com.cui.abs.core.rendering.gui.GuiRenderer;
import com.cui.core.CUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Function;

#if MC_VER >= V1_21_6 import com.cui.core.CUI;
import com.mojang.blaze3d.pipeline.RenderPipeline;
#endif

@Debug(export = true)
@Mixin(Gui.class)
public class HUDMixin {
    /**
     * <h5> Air bubbles coloring <h5/>
     * */
    @Redirect(at = @At(value = "INVOKE", target = #if MC_VER >= V1_20_4 "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(" #if MC_VER >= V1_21_6 + "Lcom/mojang/blaze3d/pipeline/RenderPipeline;" #elif MC_VER >= V1_21_3 + "Ljava/util/function/Function;" #endif + "Lnet/minecraft/resources/ResourceLocation;IIII)V" #else "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIIIII)V" #endif, ordinal =  0), method =  #if MC_VER >= V1_21_3 "renderAirBubbles" #else "renderAirLevel" #endif)
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

    @Redirect(at = @At(value = "INVOKE", target = #if MC_VER >= V1_20_4 "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(" #if MC_VER >= V1_21_6 + "Lcom/mojang/blaze3d/pipeline/RenderPipeline;" #elif MC_VER >= V1_21_3 + "Ljava/util/function/Function;" #endif + "Lnet/minecraft/resources/ResourceLocation;IIII)V" #else "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIIIII)V" #endif, ordinal = 1), method = #if MC_VER >= V1_21 "renderAirBubbles" #else "renderAirLevel" #endif)
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

    #if MC_VER >= V1_21_3
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(" #if MC_VER >= V1_21_6 + "Lcom/mojang/blaze3d/pipeline/RenderPipeline;" #elif MC_VER >= V1_21_3 + "Ljava/util/function/Function;" #endif + "Lnet/minecraft/resources/ResourceLocation;IIII)V", ordinal = 2), method = #if MC_VER >= V1_21_3 "renderAirBubbles" #else "renderAirLevel" #endif)
    private void injected8(
            GuiGraphics instance,
            #if MC_VER >= V1_21_6 RenderPipeline pipeline, #elif MC_VER >= V1_21_3 Function<ResourceLocation, RenderType> pipeline, #endif
            ResourceLocation sprite, int x, int y, int width, int height
    ) {
        GuiRenderer.blitSprite(instance, "GUI_TEXTURED", ResourceBridge.spriteNamespace(CUI.MOD_ID, "hud/air_empty_container"), new Rectangle(x, y, width, height), CUI.cuiConfig.getRGB());
        GuiRenderer.blitSprite(instance, "GUI_TEXTURED", sprite, new Rectangle(x, y, width, height), CUI.cuiConfig.getRGB());
    }
    #endif
}