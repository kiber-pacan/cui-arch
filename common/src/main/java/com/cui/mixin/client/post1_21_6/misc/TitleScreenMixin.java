package com.cui.mixin.client.post1_21_6.misc;

import com.cui.CUI;
import com.cui.ColorScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.SpriteIconButton;
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

@Mixin(#if MC_VER >= V1_21_6 TitleScreen.class #else Minecraft.class #endif)
public abstract class TitleScreenMixin extends Screen {
    #if MC_VER >= V1_21_6
    protected TitleScreenMixin(Component title) {
        super(title);
    }

    // I think it looks better when we have colorful gradient
    @Inject(at = @At(value = "HEAD"), method = "render")
    private void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        float[] hsv = Color.RGBtoHSB((int) (CUI.cuiConfig.r * 255.0f), (int) (CUI.cuiConfig.g * 255.0f), (int) (CUI.cuiConfig.b * 255.0f), null);
        guiGraphics.fillGradient(0, 0, width, height,
                ((int)(CUI.cuiConfig.a * 255) << 24) | (Color.getHSBColor(hsv[0], hsv[1], hsv[2] / 5.0f).getRGB() & 0x00FFFFFF),
                ((int)(CUI.cuiConfig.a * 255) << 24) | (Color.getHSBColor(hsv[0], hsv[1], hsv[2] / 2.7f).getRGB() & 0x00FFFFFF)
        );
    }

    @Unique
    private static SpriteIconButton cui$cui(int width, Button.OnPress onPress, boolean iconOnly) {
        return SpriteIconButton.builder(Component.translatable("options.cui"), onPress, iconOnly)
                .width(width)
                .sprite(ResourceLocation.withDefaultNamespace("cui_button"), 16, 16)
                .build();
    }

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

    #endif
}
