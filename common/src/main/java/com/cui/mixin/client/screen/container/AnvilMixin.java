package com.cui.mixin.client.screen.container;

#if MC_VER >= V1_21_6 import com.cui.core.CUI;
import com.mojang.blaze3d.pipeline.RenderPipeline; #endif
import com.cui.abs.core.rendering.gui.GuiRenderer;
import com.cui.core.CUI;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AnvilScreen;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
        import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(#if MC_VER >= V1_21_6 || MC_VER <= V1_21_5 AnvilScreen.class #else Minecraft.class #endif)
public class AnvilMixin {
    #if MC_VER >= V1_21_6
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/ResourceLocation;IIII)V"), method = "renderBg")
    private static void injected(GuiGraphics instance, RenderPipeline pipeline, ResourceLocation sprite, int x, int y, int width, int height) {
        instance.blitSprite(pipeline, sprite, x, y, width, height, CUI.cuiConfig.getRGB());
    }
    #elif MC_VER <= V1_21_5
    @Inject(at = @At(value = "HEAD"), method = "renderBg")
    private static void injected1(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY, CallbackInfo ci) {
        GuiRenderer.setShaderColor(guiGraphics, CUI.cuiConfig.getRGB());
    }


    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit" #if MC_VER >= V1_20_4 + "Sprite" #endif + "(" #if MC_VER >= V1_21_3 + "Ljava/util/function/Function;" #endif + "Lnet/minecraft/resources/ResourceLocation;IIII" #if MC_VER <= V1_20_1 + "II" #endif + ")V", shift = At.Shift.AFTER, ordinal = 0), method = "renderBg")
    private static void injected2(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY, CallbackInfo ci) {
        GuiRenderer.clearShaderColor(guiGraphics);
    }
    #endif
}
