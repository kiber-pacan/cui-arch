package com.cui.mixin.client.post1_21_6.misc;

import com.cui.CUI;
import com.mojang.blaze3d.systems.RenderSystem;
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
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.Minecraft;

import java.util.function.Function;

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

            guiGraphics.blit(#if MC_VER >= V1_21_3 RenderType::guiTextured, #endif ResourceLocation.withDefaultNamespace("textures/gui/recipe_book_lens.png"), i, j, 1.0F, 1.0F, 147, 166, 256, 256);
        }
    }
    #endif
}
