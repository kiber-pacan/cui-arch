package com.cui.mixin.client.experince;

#if MC_VER >= V1_21_6
import com.cui.abs.core.data.Rectangle;
import com.cui.abs.core.data.data.GuiGraphicsMethods;
import com.cui.abs.core.rendering.gui.GuiRenderer;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.contextualbar.ExperienceBarRenderer;
#endif

import net.minecraft.client.Minecraft;
#if MC_VER >= V1_21_11
import net.minecraft.resources.Identifier;
#else
import net.minecraft.resources.ResourceLocation;
#if MC_VER >= V1_21_3
import net.minecraft.client.renderer.RenderType;
#endif
#endif
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.client.gui.components.AbstractButton;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.GrindstoneScreen;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.injection.At;
import com.cui.core.CUI;

@Mixin(#if MC_VER >= V1_21_6 ExperienceBarRenderer.class #else Minecraft.class #endif)
public class ExperienceLevelRendererMixin {
    #if MC_VER >= V1_21_6
    // XP text
    @Redirect(at = @At(value = "INVOKE", target = GuiGraphicsMethods.blitSprite1Rec), method = "renderBackground")
    private static void injected(GuiGraphics instance, RenderPipeline pipeline, #if MC_VER >= V1_21_11 Identifier #else ResourceLocation #endif sprite, int x, int y, int width, int height) {
        GuiRenderer.blitSprite(instance, "GUI_TEXTURED", sprite, new Rectangle(x, y, width, height), CUI.cuiConfig.getRGB());
    }

    @Redirect(at = @At(value = "INVOKE", target = GuiGraphicsMethods.blitSprite2Rec), method = "renderBackground")
    private static void injected(GuiGraphics instance, RenderPipeline pipeline, #if MC_VER >= V1_21_11 Identifier #else ResourceLocation #endif sprite, int textureWidth, int textureHeight, int u, int v, int x, int y, int width, int height) {
        GuiRenderer.blitSprite(instance, "GUI_TEXTURED", sprite, new Rectangle(x, y, width, height), new Rectangle( u, v, textureWidth, textureHeight), CUI.cuiConfig.getRGB());
    }
    #endif
}
