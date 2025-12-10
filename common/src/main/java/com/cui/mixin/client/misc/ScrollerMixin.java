package com.cui.mixin.client.misc;

#if MC_VER >= V1_21_6 import com.mojang.blaze3d.pipeline.RenderPipeline; #endif
#if MC_VER >= V1_21_4 import net.minecraft.client.gui.components.AbstractScrollArea; #endif
import org.spongepowered.asm.mixin.Mixin;
        import net.minecraft.client.Minecraft;

// Scroller element in screens

@Mixin(#if MC_VER >= V1_21_6 AbstractScrollArea.class #else Minecraft.class #endif)
public class ScrollerMixin {
    #if MC_VER >= V1_21_6
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/ResourceLocation;IIII)V"), method = "renderScrollbar")
    private static void injected(GuiGraphics instance, RenderPipeline pipeline, ResourceLocation sprite, int x, int y, int width, int height) {
        instance.blitSprite(pipeline, sprite, x, y, width, height, CUI.cuiConfig.getRGB());
    }
    #endif
}
