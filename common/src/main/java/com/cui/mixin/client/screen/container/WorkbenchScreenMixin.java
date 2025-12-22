package com.cui.mixin.client.screen.container;


import com.cui.abs.core.data.data.GuiGraphicsMethods;
import com.cui.abs.core.rendering.gui.GuiRenderer;
import com.cui.core.CUI;
#if MC_VER >= V1_21_6 import com.cui.mixin.client.book.RecipeBookMixin;
import com.mojang.blaze3d.pipeline.RenderPipeline; #endif


#if MC_VER >= V1_21_3 import com.cui.mixin.client.book.RecipeBookMixin;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screens.inventory.AbstractRecipeBookScreen; #endif
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


import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
#if MC_VER >= V1_21_11
import net.minecraft.resources.Identifier;
#else
import net.minecraft.resources.ResourceLocation;
#if MC_VER >= V1_21_3
import net.minecraft.client.renderer.RenderType;
#endif
#endif
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CraftingScreen.class)
public #if MC_VER >= V1_21_3 abstract #endif class WorkbenchScreenMixin #if MC_VER >= V1_21_3 extends AbstractRecipeBookScreen<CraftingMenu> #endif {
    // Background
    #if MC_VER <= V1_21_1
    @Shadow
    @Final
    private RecipeBookComponent recipeBookComponent;
    #endif

    #if MC_VER >= V1_21_3
    public WorkbenchScreenMixin(CraftingMenu menu, RecipeBookComponent<?> recipeBookComponent, Inventory playerInventory, Component title) {
        super(menu, recipeBookComponent, playerInventory, title);
    }
    #endif

    #if MC_VER >= V1_21_6
    @Redirect(at = @At(value = "INVOKE", target = GuiGraphicsMethods.blit8), method = "renderBg")
    private void injected(GuiGraphics instance, RenderPipeline pipeline, #if MC_VER >= V1_21_11 Identifier #else ResourceLocation #endif atlas, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight) {
        instance.blit(pipeline, atlas, x, y, u, v, width, height, textureWidth, textureHeight, CUI.cuiConfig.getRGB());
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        int a = Minecraft.getInstance().getWindow().getGuiScaledWidth(), b = Minecraft.getInstance().getWindow().getGuiScaledHeight();
        if (((RecipeBookMixin)(Object)this).getRecipeBookComponent().isVisible()) {
            guiGraphics.renderItem(Items.KNOWLEDGE_BOOK.getDefaultInstance(), (int) (((float) a / 2) - 4), (int) (((float) b / 2) - 48));
        } else {
            guiGraphics.renderItem(Items.KNOWLEDGE_BOOK.getDefaultInstance(), (int) (((float) a / 2) - 81), (int) (((float) b / 2) - 48));
        }
    }
    #else
	@Inject(at = @At(value = "TAIL"), method = "renderBg")
	private void renderTail(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY, CallbackInfo ci) {
        GuiRenderer.clearShaderColor(guiGraphics);

		int a = Minecraft.getInstance().getWindow().getGuiScaledWidth(), b = Minecraft.getInstance().getWindow().getGuiScaledHeight();
        if (#if MC_VER <= V1_21_1 recipeBookComponent #else ((RecipeBookMixin)(Object)this).getRecipeBookComponent() #endif .isVisible()) {
            guiGraphics.renderItem(Items.KNOWLEDGE_BOOK.getDefaultInstance(), (int) (((float) a / 2) - 4), (int) (((float) b / 2) - 48));
		} else {
            guiGraphics.renderItem(Items.KNOWLEDGE_BOOK.getDefaultInstance(), (int) (((float) a / 2) - 81), (int) (((float) b / 2) - 48));
		}

        GuiRenderer.setShaderColor(guiGraphics, CUI.cuiConfig.getRGB());
    }
    #if MC_VER <= V1_21_5
    @Inject(at = @At(value = "HEAD"), method = "renderBg")
    private static void injected1(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY, CallbackInfo ci) {
        GuiRenderer.setShaderColor(guiGraphics, CUI.cuiConfig.getRGB());
    }

    @Inject(at = @At(value = "TAIL"), method = "renderBg")
    private static void injected2(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY, CallbackInfo ci) {
        GuiRenderer.clearShaderColor(guiGraphics);
    }
    #endif
    #endif
}