package com.cui.mixin.client.pre1_21_6;


import com.cui.CUI;
#if MC_VER >= V1_21_6 import com.mojang.blaze3d.pipeline.RenderPipeline; #endif
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
#if MC_VER >= V1_21_3 import net.minecraft.client.gui.screens.inventory.AbstractRecipeBookScreen; #endif
import net.minecraft.client.gui.screens.inventory.CraftingScreen;
#if MC_VER < V1_21_1
import net.minecraft.client.gui.screens.recipebook.AbstractFurnaceRecipeBookComponent;
#endif
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

#if MC_VER <= V1_21_6
import net.minecraft.client.renderer.RenderType;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import com.mojang.blaze3d.systems.RenderSystem;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.gui.GuiGraphics;

import java.awt.*;
#endif

@Mixin(CraftingScreen.class)
public #if MC_VER >= V1_21_3 abstract #endif class WorkbenchScreenMixin #if MC_VER >= V1_21_3 extends AbstractRecipeBookScreen<CraftingMenu> #endif {
    // Background
    #if MC_VER <= V1_21_1
    @Shadow @Final private RecipeBookComponent recipeBookComponent;
    #endif

    #if MC_VER >= V1_21_3
    public WorkbenchScreenMixin(CraftingMenu menu, RecipeBookComponent<?> recipeBookComponent, Inventory playerInventory, Component title) {
        super(menu, recipeBookComponent, playerInventory, title);
    }
    #endif

    #if MC_VER >= V1_21_6
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/ResourceLocation;IIFFIIII)V"), method = "renderBg")
    private void injected(GuiGraphics instance, RenderPipeline pipeline, ResourceLocation atlas, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight) {
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
        #if MC_VER >= V1_21_3
        guiGraphics.flush();
        RenderSystem.setShaderColor(1, 1, 1, 1);
        #endif
        #if MC_VER <= V1_21_1 guiGraphics.setColor(1, 1, 1, 1); #endif

		int a = Minecraft.getInstance().getWindow().getGuiScaledWidth(), b = Minecraft.getInstance().getWindow().getGuiScaledHeight();
        if (#if MC_VER <= V1_21_1 recipeBookComponent #else ((RecipeBookMixin)(Object)this).getRecipeBookComponent() #endif .isVisible()) {
            guiGraphics.renderItem(Items.KNOWLEDGE_BOOK.getDefaultInstance(), (int) (((float) a / 2) - 4), (int) (((float) b / 2) - 48));
		} else {
            guiGraphics.renderItem(Items.KNOWLEDGE_BOOK.getDefaultInstance(), (int) (((float) a / 2) - 81), (int) (((float) b / 2) - 48));
		}

        #if MC_VER >= V1_21_3
        guiGraphics.flush();
        RenderSystem.setShaderColor(CUI.cuiConfig.r, CUI.cuiConfig.g, CUI.cuiConfig.b, 1);
        #endif
        #if MC_VER <= V1_21_1 guiGraphics.setColor(CUI.cuiConfig.r, CUI.cuiConfig.g, CUI.cuiConfig.b, 1); #endif
    }
    #endif
}