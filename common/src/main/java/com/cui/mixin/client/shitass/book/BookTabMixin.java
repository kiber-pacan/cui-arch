package com.cui.mixin.client.shitass.book;

#if MC_VER >= V1_21_6
import net.minecraft.client.gui.screens.recipebook.RecipeBookTabButton;
#endif

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(#if MC_VER >= V1_21_6 RecipeBookTabButton.class #else Minecraft.class #endif)
public class BookTabMixin {
    #if MC_VER >= V1_21_6
    /*
    @Redirect(method = "renderWidget", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/ResourceLocation;IIII)V"))
    private void injected1(GuiGraphics instance, RenderPipeline pipeline, ResourceLocation sprite, int x, int y, int width, int height) {
        instance.blitSprite(pipeline, sprite, x, y, width, height, CUI.cuiConfig.getRGB());
    }
    */
    #endif
}
