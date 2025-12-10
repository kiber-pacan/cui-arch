package com.cui.mixin.client.shitass.book;

import com.cui.CUI;

import net.minecraft.client.gui.components.AbstractButton;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

#if MC_VER <= V1_21_6
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.gui.GuiGraphics;
#endif


@Mixin(AbstractButton.class)
public class ButtonMixin {

    #if MC_VER >= V1_21_6
    @ModifyArg(method = "renderWidget", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/ARGB;color(FI)I"), index = 1)
    private int injected(int color) {
        if (((color) & 0xFF) == ((color >> 8) & 0xFF) && ((color >> 8) & 0xFF) == ((color >> 16) & 0xFF)) {
            return CUI.cuiConfig.getTextColor(color);
        }

        return color;
    }

    @ModifyArg(method = "renderWidget", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/ResourceLocation;IIIII)V"), index = 6)
    private int injected1(int c) {
        Color c1 = CUI.cuiConfig.color;
        return ((c >> 24) & 0xFF) << 24 | (c1.getRed() & 0xFF) << 16 | (c1.getGreen() & 0xFF) << 8 | (c1.getBlue() & 0xFF);
    }

    #else
    @Inject(at = @At(value = #if MC_VER >= V1_21_3 "HEAD" #else "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;enableBlend()V", shift = At.Shift.AFTER #endif), method = "renderWidget")
    private void renderHead(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        #if MC_VER >= V1_21_3
        guiGraphics.flush();
        RenderSystem.setShaderColor(CUI.cuiConfig.r, CUI.cuiConfig.g, CUI.cuiConfig.b, 1);
        #endif
        #if MC_VER <= V1_21_1 guiGraphics.setColor(CUI.cuiConfig.r, CUI.cuiConfig.g, CUI.cuiConfig.b, 1); #endif
    }

    @Inject(at = @At(value = "INVOKE", target = #if MC_VER >= V1_21_3 #if MC_VER >= V1_21_6 "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/ResourceLocation;IIIII)V" #else "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIIII)V" #endif #else "Lnet/minecraft/client/gui/GuiGraphics;setColor(FFFF)V" #endif, shift = At.Shift.AFTER), method = "renderWidget")
    private void renderHead1(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        float r = CUI.cuiConfig.r;
        float g = CUI.cuiConfig.g;
        float b = CUI.cuiConfig.b;

        float mid = (r + g + b) / 3;

        #if MC_VER >= V1_21_3
        guiGraphics.flush();
        RenderSystem.setShaderColor(r + 0.25f * (3 - mid), g + 0.25f * (3 - mid), b + 0.25f * (3 - mid), 1);
        #endif
        #if MC_VER <= V1_21_1 guiGraphics.setColor(r + 0.25f * (3 - mid), g + 0.25f * (3 - mid), b + 0.25f * (3 - mid), 1); #endif
    }
    #endif
}