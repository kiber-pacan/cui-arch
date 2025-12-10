package com.cui.mixin.client.misc;

#if MC_VER >= V1_21_6 import com.mojang.blaze3d.pipeline.RenderPipeline; #endif
import net.minecraft.client.gui.components.ImageButton;
        import org.spongepowered.asm.mixin.Mixin;

// Degenerate hook to render normal book image on top of dyed one

@Mixin(ImageButton.class)
public class RecipeBookComponentMixin {
    #if MC_VER >= V1_21_6
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/ResourceLocation;IIII)V"), method = "renderWidget")
    private static void injected(GuiGraphics instance, RenderPipeline pipeline, ResourceLocation sprite, int x, int y, int width, int height) {
        if (sprite.toString().contains("recipe_book")) {
            instance.blitSprite(pipeline, sprite, x, y, width, height, CUI.cuiConfig.getRGB());
        } else {
            instance.blitSprite(pipeline, sprite, x, y, width, height);
        }
    }
    #endif
}
