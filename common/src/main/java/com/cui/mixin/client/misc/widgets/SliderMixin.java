package com.cui.mixin.client.misc.widgets;

import com.cui.abs.core.data.Rectangle;
import com.cui.abs.core.data.ResourceBridge;
import com.cui.abs.core.rendering.gui.GuiRenderer;
import com.cui.core.CUI;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

#if MC_VER >= V1_21_3
import net.minecraft.client.renderer.RenderType;
#endif
import com.cui.core.CUI;
#if MC_VER >= V1_21_6 import com.cui.mixin.client.book.RecipeBookMixin;
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


import java.awt.*;
import java.util.function.Function;

// Slider widget

@Mixin(AbstractSliderButton.class)
public class SliderMixin {
    #if MC_VER >= V1_21_3
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(" #if MC_VER >= V1_21_6 + "Lcom/mojang/blaze3d/pipeline/RenderPipeline;" #elif MC_VER >= V1_21_3 + "Ljava/util/function/Function;" #endif + "Lnet/minecraft/resources/ResourceLocation;IIIII)V", ordinal = 0), method = "renderWidget")
    private void blitBackground(GuiGraphics instance, #if MC_VER >= V1_21_6 RenderPipeline #elif MC_VER >= V1_21_3 Function<ResourceLocation, RenderType> #endif pipeline, ResourceLocation sprite, int x, int y, int width, int height, int color) {
        GuiRenderer.blitSprite(instance, "GUI_TEXTURED", sprite, new Rectangle(x, y, width, height), CUI.cuiConfig.getRGB());
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(" #if MC_VER >= V1_21_6 + "Lcom/mojang/blaze3d/pipeline/RenderPipeline;" #elif MC_VER >= V1_21_3 + "Ljava/util/function/Function;" #endif + "Lnet/minecraft/resources/ResourceLocation;IIIII)V", ordinal = 1), method = "renderWidget")
    private void blitHandle(GuiGraphics instance, #if MC_VER >= V1_21_6 RenderPipeline #elif MC_VER >= V1_21_3 Function<ResourceLocation, RenderType> #endif pipeline, ResourceLocation sprite, int x, int y, int width, int height, int color) {
        GuiRenderer.blitSprite(instance, "GUI_TEXTURED", sprite, new Rectangle(x, y, width, height), CUI.cuiConfig.getTextColor(-1));
    }
    #else
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;setColor(FFFF)V", ordinal = 0), method = "renderWidget")
    private void renderHead(GuiGraphics instance, float red, float green, float blue, float alpha) {
        GuiRenderer.setShaderColor(instance, CUI.cuiConfig.getRGBA(alpha));
    }
    #endif
}
