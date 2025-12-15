package com.cui.mixin.client.screen.misc;


import com.cui.core.CUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.OptionsList;
import net.minecraft.client.gui.screens.Screen;

import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

#if MC_VER <= V1_20_1
import net.minecraft.client.gui.screens.SimpleOptionsSubScreen;
import net.minecraft.client.gui.screens.OptionsSubScreen;
#endif


@Mixin(#if MC_VER <= V1_20_1 OptionsSubScreen.class #else Minecraft.class #endif)
public abstract class OptionsScreenMixin #if MC_VER <= V1_20_1  extends Screen #endif {
    #if MC_VER <= V1_20_1
    protected OptionsScreenMixin(Component title) {
        super(title);
    }

    @Inject(at = @At(value = "TAIL", target = "Lnet/minecraft/client/gui/GuiGraphics;drawCenteredString(Lnet/minecraft/client/gui/Font;Lnet/minecraft/network/chat/Component;III)V", shift = At.Shift.AFTER), method = "basicListRender")
    private void render3(GuiGraphics guiGraphics, OptionsList optionsList, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        float[] hsv = CUI.cuiConfig.getHSV();
        guiGraphics.fillGradient(0, 0, width, height,
                ((int)(CUI.cuiConfig.a * 255) << 24) | (Color.getHSBColor(hsv[0], hsv[1], hsv[2] / 2.7f).getRGB() & 0x00FFFFFF),
                ((int)(CUI.cuiConfig.a * 255) << 24) | (Color.getHSBColor(hsv[0], hsv[1], hsv[2] / 5.0f).getRGB() & 0x00FFFFFF)
        );
    }
    #endif
}
