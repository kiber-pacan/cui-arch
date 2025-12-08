package com.cui.mixin.client.post1_21_6.misc;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Lens icon in the recipe book

@Mixin(#if MC_VER >= V1_21_6 RecipeBookComponent.class #else Minecraft.class #endif)
public class LensMixin {
    #if MC_VER >= V1_21_6
    @Shadow private int getYOrigin() { return 0; }

    @Shadow private int getXOrigin() { return 0; }

    @Shadow public boolean isVisible() { return true; }

    @Inject(at = @At(value = "TAIL"), method = "render")
    private void injected(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        if (this.isVisible()) {
            int i = this.getXOrigin();
            int j = this.getYOrigin();
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.withDefaultNamespace("textures/gui/recipe_book_lens.png"), i, j, 1.0F, 1.0F, 147, 166, 256, 256);
        }
    }
    #endif
}
