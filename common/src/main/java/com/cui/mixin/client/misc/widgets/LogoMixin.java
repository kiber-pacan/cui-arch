package com.cui.mixin.client.misc.widgets;

import com.cui.core.CUI;
#if MC_VER >= V1_21_6 import com.mojang.blaze3d.pipeline.RenderPipeline; #endif
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.LogoRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import com.cui.core.CUI;
#if MC_VER >= V1_21_6 import com.cui.mixin.client.shitass.book.RecipeBookMixin;
import com.mojang.blaze3d.pipeline.RenderPipeline; #endif


#if MC_VER >= V1_21_3 import net.minecraft.client.gui.screens.inventory.AbstractRecipeBookScreen; #endif
import net.minecraft.client.gui.screens.inventory.CraftingScreen;
#if MC_VER < V1_21_1
#endif
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

#if MC_VER <= V1_21_6

import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
#endif

#if MC_VER >= V1_21_6
import net.minecraft.client.DeltaTracker;
import net.minecraft.core.component.DataComponents;
#endif

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Function;

// Color minecraft logo in title screen

@Mixin(LogoRenderer.class)
public class LogoMixin {
    #if MC_VER >= V1_21_6
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/ResourceLocation;IIFFIIIII)V"), method = "renderLogo(Lnet/minecraft/client/gui/GuiGraphics;IFI)V")
    private static void injected(GuiGraphics instance, RenderPipeline pipeline, ResourceLocation atlas, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight, int color) {
        int rgbWithoutAlpha = CUI.cuiConfig.getRGB() & 0x00FFFFFF;
        int newColor = (color & 0xFF000000) | rgbWithoutAlpha;
        instance.blit(pipeline, atlas, x, y, u, v, width, height, textureWidth, textureHeight, newColor);
    }
    #else
    #if MC_VER >= V1_21_3
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIFFIIIII)V"), method = "renderLogo(Lnet/minecraft/client/gui/GuiGraphics;IFI)V")
    private static void injected1(GuiGraphics instance, Function<ResourceLocation, RenderType> renderTypeGetter, ResourceLocation atlasLocation, int x, int y, float uOffset, float vOffset, int uWidth, int vHeight, int textureWidth, int textureHeight, int color) {
        instance.blit(renderTypeGetter, atlasLocation, x, y, uOffset, vOffset, uWidth, vHeight, textureWidth, textureHeight, CUI.cuiConfig.getRGB());
    }
    #else
    #if MC_VER >= V1_21
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIFFIIII)V"), method = "renderLogo(Lnet/minecraft/client/gui/GuiGraphics;IFI)V")
    private static void injected1(GuiGraphics instance, ResourceLocation atlasLocation, int x, int y, float uOffset, float vOffset, int width, int height, int textureWidth, int textureHeight) {

        #if MC_VER <= V1_21_1 instance.setColor(CUI.cuiConfig.r, CUI.cuiConfig.g, CUI.cuiConfig.b, 1); #endif

        instance.blit(atlasLocation, x, y, uOffset, vOffset, width, height, textureWidth, textureHeight);

        #if MC_VER <= V1_21_1 instance.setColor(1, 1, 1, 1); #endif
    }
    #else
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;setColor(FFFF)V"), method = "renderLogo(Lnet/minecraft/client/gui/GuiGraphics;IFI)V")
    private void injected1(GuiGraphics instance, float red, float green, float blue, float alpha) {
        instance.setColor(CUI.cuiConfig.r, CUI.cuiConfig.g, CUI.cuiConfig.b, alpha);
    }

    @Inject(at = @At(value = "TAIL"), method = "renderLogo(Lnet/minecraft/client/gui/GuiGraphics;IFI)V")
    private void injected1(GuiGraphics guiGraphics, int screenWidth, float transparency, int height, CallbackInfo ci) {
        guiGraphics.setColor(1, 1, 1, 1);
    }
    #endif
    #endif
    #endif
}
