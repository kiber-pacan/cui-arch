package com.cui.mixin.client.shitass.screen;


import com.cui.core.CUI;
import com.mojang.authlib.minecraft.client.MinecraftClient;
#if MC_VER < V1_21
import net.minecraft.client.gui.GuiGraphics;
#if MC_VER == V1_20_4
import net.minecraft.client.gui.components.AbstractContainerWidget;
#endif
import net.minecraft.client.gui.components.AbstractSelectionList;
#endif
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;


@Mixin(#if MC_VER < V1_21 AbstractSelectionList.class #else Minecraft.class #endif)
public abstract class SelectionListMixin #if MC_VER < V1_21 && MC_VER == V1_20_4 extends AbstractContainerWidget #endif {
    #if MC_VER < V1_21
    #if MC_VER == V1_20_4
    public SelectionListMixin(int i, int j, int k, int l, Component component) {
        super(i, j, k, l, component);
    }
    #endif

    #if MC_VER < V1_20_4
    @Shadow protected int y0;
    @Shadow protected int y1;
    @Shadow protected int x1;
    @Shadow protected int x0;
    #endif

    @Shadow public double getScrollAmount() {
        return 0;
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/AbstractSelectionList;enableScissor(Lnet/minecraft/client/gui/GuiGraphics;)V", shift = At.Shift.AFTER), method = #if MC_VER == V1_20_4 "renderWidget" #else "render" #endif)
    private void render3(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        float[] hsv = Color.RGBtoHSB((int) (CUI.cuiConfig.r * 255.0f), (int) (CUI.cuiConfig.g * 255.0f), (int) (CUI.cuiConfig.b * 255.0f), null);
        guiGraphics.fillGradient(
                #if MC_VER == V1_20_4 this.getX(), this.getY(), this.getRight(), this.getBottom(), #else
                this.x0, this.y0, (int) (this.x1), (int) ((this.y1 + (int)this.getScrollAmount())),
                #endif
                ((int)(CUI.cuiConfig.a * 255) << 24) | (Color.getHSBColor(hsv[0], hsv[1], hsv[2] / 2.7f).getRGB() & 0x00FFFFFF),
                ((int)(CUI.cuiConfig.a * 255) << 24) | (Color.getHSBColor(hsv[0], hsv[1], hsv[2] / 5.0f).getRGB() & 0x00FFFFFF)
        );
    }
    #endif
}
