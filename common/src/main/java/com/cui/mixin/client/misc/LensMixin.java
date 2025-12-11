package com.cui.mixin.client.misc;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
#if MC_VER >= V1_21_6
import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.renderer.RenderPipelines;
#endif
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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

// Lens icon in the recipe book

@Mixin(RecipeBookComponent.class)
public class LensMixin {
    #if MC_VER >= V1_21_3
    @Shadow private int getYOrigin() { return 0; }

    @Shadow private int getXOrigin() { return 0; }
    #else
    @Shadow private int xOffset;
    @Shadow private int width;
    @Shadow private int height;
    #endif

    @Shadow public boolean isVisible() { return true; }

    #if MC_VER >= V1_21_6
    @Inject(at = @At(value = "TAIL"), method = "render")
    private void injected(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        if (this.isVisible()) {
            int i = this.getXOrigin();
            int j = this.getYOrigin();
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.withDefaultNamespace("textures/gui/recipe_book_lens.png"), i, j, 1.0F, 1.0F, 147, 166, 256, 256);
        }
    }
    #else
    @Inject(at = @At(value = "TAIL"), method = "render")
    private void injected(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        if (this.isVisible()) {
            #if MC_VER >= V1_21_3
            int i = this.getXOrigin();
            int j = this.getYOrigin();
            #else
            int i = (this.width - 147) / 2 - this.xOffset;
            int j = (this.height - 166) / 2;
            #endif

            guiGraphics.blit(#if MC_VER >= V1_21_3 RenderType::guiTextured, #endif #if MC_VER < V1_21 new #endif ResourceLocation #if MC_VER >= V1_21 .withDefaultNamespace #endif("textures/gui/recipe_book_lens.png"), i, j, 1.0F, 1.0F, 147, 166, 256, 256);
        }
    }
    #endif
}
