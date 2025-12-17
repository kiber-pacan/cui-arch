package com.cui.mixin.client.misc.widgets.scroll;


import com.cui.abs.core.data.Rectangle;
import com.cui.abs.core.rendering.gui.GuiRenderer;
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
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
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

    @Shadow protected int width;
    @Shadow protected int height;
    @Shadow private boolean renderTopAndBottom;
    #endif

    @Shadow public double getScrollAmount() {
        return 0;
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/AbstractSelectionList;enableScissor(Lnet/minecraft/client/gui/GuiGraphics;)V", shift = At.Shift.AFTER), method = #if MC_VER == V1_20_4 "renderWidget" #else "render" #endif)
    private void fillGradient1(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        float[] hsv = CUI.cuiConfig.getHSV();
        guiGraphics.fillGradient(
                #if MC_VER == V1_20_4
                this.getX(), this.getY(), this.getRight(), this.getBottom(),
                #else
                this.x0, this.y0, (int) (this.x1), (int) ((this.y1 + (int)this.getScrollAmount())),
                #endif
                ((int)(CUI.cuiConfig.a * 255) << 24) | (Color.getHSBColor(hsv[0], hsv[1], hsv[2] / 2.7f).getRGB() & 0x00FFFFFF),
                ((int)(CUI.cuiConfig.a * 255) << 24) | (Color.getHSBColor(hsv[0], hsv[1], hsv[2] / 5.0f).getRGB() & 0x00FFFFFF)
        );
    }

    #if MC_VER <= V1_20_1
    @Inject(at = @At(value = "TAIL"), method = #if MC_VER == V1_20_4 "renderWidget" #else "render" #endif)
    private void fillGradient2(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        if (this.renderTopAndBottom) {
        float[] hsv = Color.RGBtoHSB((int) (CUI.cuiConfig.r * 255.0f), (int) (CUI.cuiConfig.g * 255.0f), (int) (CUI.cuiConfig.b * 255.0f), null);
        guiGraphics.fillGradient(
                #if MC_VER == V1_20_4
                this.getX(), 0, this.getRight(), this.getY(),
                #else
                this.x0, 0, this.x1, this.y0,
                #endif

                ((int) (CUI.cuiConfig.a * 255) << 24) | (Color.getHSBColor(hsv[0], hsv[1], hsv[2] / 2.7f).getRGB() & 0x00FFFFFF),
                ((int) (CUI.cuiConfig.a * 255) << 24) | (Color.getHSBColor(hsv[0], hsv[1], hsv[2] / 5.0f).getRGB() & 0x00FFFFFF)
        );

        guiGraphics.fillGradient(
                #if MC_VER == V1_20_4
                this.getX(), this.getBottom(), this.getRight(), this.getHeight(),
                #else
                this.x0, this.y1, this.x1, this.height,
                #endif
                ((int) (CUI.cuiConfig.a * 255) << 24) | (Color.getHSBColor(hsv[0], hsv[1], hsv[2] / 2.7f).getRGB() & 0x00FFFFFF),
                ((int) (CUI.cuiConfig.a * 255) << 24) | (Color.getHSBColor(hsv[0], hsv[1], hsv[2] / 5.0f).getRGB() & 0x00FFFFFF)
        );
        }
    }
    #endif

    #if MC_VER >= V1_20_4
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lnet/minecraft/resources/ResourceLocation;IIII)V"), method = "renderWidget")
    private static void fill1(GuiGraphics instance, ResourceLocation sprite, int x, int y, int width, int height) {
        GuiRenderer.blitSprite(instance, "GUI_TEXTURED", sprite, new Rectangle(x, y, width, height), CUI.cuiConfig.getRGB());
    }
    #else
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;fill(IIIII)V"), method = "render")
    private static void fill1(GuiGraphics instance, int minX, int minY, int maxX, int maxY, int color) {
        instance.fill(minX, minY, maxX, maxY, CUI.cuiConfig.getTextColor(color));
    }
    #endif

    #if MC_VER >= V1_20_4
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;setColor(FFFF)V", ordinal = 0), method = "renderWidget")
    private void setBackgroundColor1(GuiGraphics instance, float red, float green, float blue, float alpha) {
        GuiRenderer.setShaderColor(instance, CUI.cuiConfig.getRGB());
    }

    #else
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;setColor(FFFF)V", ordinal = 2), method = "render")
    private void setBackgroundColor2(GuiGraphics instance, float red, float green, float blue, float alpha) {
        GuiRenderer.setShaderColor(instance, CUI.cuiConfig.getRGB());
    }
    #endif
    #endif
}
