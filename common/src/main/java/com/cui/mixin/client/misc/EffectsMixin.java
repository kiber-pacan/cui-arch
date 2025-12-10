package com.cui.mixin.client.misc;

import net.minecraft.client.Minecraft;

#if MC_VER >= V1_21_3
import net.minecraft.client.gui.screens.inventory.EffectsInInventory;

#endif

#if MC_VER >= V1_21_6
import com.mojang.blaze3d.pipeline.RenderPipeline;
#endif

import org.spongepowered.asm.mixin.Mixin;

// Effects background

@Mixin(#if MC_VER >= V1_21_3 EffectsInInventory.class #else Minecraft.class #endif)
public class EffectsMixin {
    #if MC_VER >= V1_21_6
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/ResourceLocation;IIII)V"), method = "renderBackgrounds")
    private void injected(GuiGraphics instance, RenderPipeline pipeline, ResourceLocation sprite, int x, int y, int width, int height) {
        instance.blitSprite(pipeline, sprite, x, y, width, height, CUI.cuiConfig.getRGB());
    }
    #else
    #if MC_VER >= V1_21_3
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIII)V"), method = "renderBackgrounds")
    private static void injected(GuiGraphics instance, Function<ResourceLocation, RenderType> renderTypeGetter, ResourceLocation sprite, int x, int y, int width, int height) {
        RenderSystem.setShaderColor(CUI.cuiConfig.r, CUI.cuiConfig.g, CUI.cuiConfig.b, 1);
        instance.blitSprite(renderTypeGetter, sprite, x, y, width, height);
        RenderSystem.setShaderColor(1, 1, 1, 1);
    }
    #endif
    #endif
}
