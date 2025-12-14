package com.cui.mixin.client.screen.misc;

import com.cui.core.CUI;
import com.cui.core.ColorScreen;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
#if MC_VER >= V1_20_4
import net.minecraft.client.gui.components.SpriteIconButton;
#else
import net.minecraft.client.gui.components.ImageButton;
#endif
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

// Title screen mixin

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {
    protected TitleScreenMixin(Component title) {
        super(title);
    }
    /*
    @Unique
    private static #if MC_VER >= V1_20_4 SpriteIconButton #else ImageButton #endif cui$cui(int width, Button.OnPress onPress, boolean iconOnly) {
        return #if MC_VER >= V1_20_4 SpriteIconButton #else ImageButton #endif.builder(Component.translatable("options.cui"), onPress, iconOnly)
                .width(width)
                .sprite(#if MC_VER < V1_21 new #endif ResourceLocation #if MC_VER >= V1_21 .withDefaultNamespace #endif("cui_button"), 16, 16)
                .build();
    }
    */

    // I think it looks better when we have colorful gradient
    @Inject(at = @At(value = "INVOKE", target = #if MC_VER >= V1_21 "Lnet/minecraft/client/gui/screens/TitleScreen;renderPanorama(Lnet/minecraft/client/gui/GuiGraphics;F)V" #else "Lnet/minecraft/client/renderer/PanoramaRenderer;render(FF)V" #endif, shift = At.Shift.AFTER), method = "render")
    private void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        float[] hsv = Color.RGBtoHSB((int) (CUI.cuiConfig.r * 255.0f), (int) (CUI.cuiConfig.g * 255.0f), (int) (CUI.cuiConfig.b * 255.0f), null);
        guiGraphics.fillGradient(0, 0, width, height,
                ((int)(CUI.cuiConfig.a * 255) << 24) | (Color.getHSBColor(hsv[0], hsv[1], hsv[2] / 2.7f).getRGB() & 0x00FFFFFF),
                ((int)(CUI.cuiConfig.a * 255) << 24) | (Color.getHSBColor(hsv[0], hsv[1], hsv[2] / 5.0f).getRGB() & 0x00FFFFFF)
        );
    }


    /*
    #if MC_VER >= V1_21_6
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/SpriteIconButton;setPosition(II)V", ordinal = 0), method = "init")
    private void init(SpriteIconButton instance, int x, int y) {
        instance.setPosition(x, y);

        if (CUI.cuiConfig.enableButton) {

            SpriteIconButton spriteIconButton = this.addRenderableWidget(
                    cui$cui(
                            20, button -> this.minecraft.setScreen(new ColorScreen(this)), true
                    )
            );
            spriteIconButton.setPosition(this.width / 2 + 124 + 4, y);
        }
    }
    #else
    // CUI button init
    @Redirect(at = @At(value = "INVOKE", target = #if MC_VER >= V1_20_4 "Lnet/minecraft/client/gui/components/SpriteIconButton;setPosition(II)V" #else "" #endif, ordinal = 0), method = "init")
    private void init(#if MC_VER >= V1_20_4 SpriteIconButton #else ImageButton #endif instance, int x, int y) {
        instance.setPosition(x, y);

        if (CUI.cuiConfig.enableButton) {

            #if MC_VER >= V1_20_4 SpriteIconButton #else ImageButton #endif spriteIconButton = this.addRenderableWidget(
                    cui$cui(
                            20, button -> this.minecraft.setScreen(new ColorScreen(this)), true
                    )
            );
            spriteIconButton.setPosition(this.width / 2 + 124 + 4, y);
        }
    }

    // Degenerate logo hack
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/SplashRenderer;render(Lnet/minecraft/client/gui/GuiGraphics;ILnet/minecraft/client/gui/Font;I)V", shift = At.Shift.AFTER), method = "render")
    private void render2(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        RenderSystem.setShaderColor(1, 1, 1, 1);
    }
    #endif

     */
}
