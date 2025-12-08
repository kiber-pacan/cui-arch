package com.cui.mixin.client.pre1_21_6;

import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(TitleScreen.class)
public class TitleMixin {
    #if MC_VER >= V1_21_6

    #else
    @Inject(at = @At(value = "HEAD"), method = "render")
    private void renderHead(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        Color color = new Color(CUI.cuiConfig.r, CUI.cuiConfig.g, CUI.cuiConfig.b);
        color.brighter().brighter().brighter();

        #if MC_VER >= V1_21_3
        guiGraphics.flush();
        RenderSystem.setShaderColor(color.getRed(), color.getGreen(), color.getBlue(), 1);
        #endif
        #if MC_VER <= V1_21_1 guiGraphics.setColor(color.getRed(), color.getGreen(), color.getBlue(), 1); #endif

    }

    @Inject(at = @At(value = "TAIL"), method = "render")
    private void renderTail(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        #if MC_VER >= V1_21_3

        guiGraphics.flush();
        RenderSystem.setShaderColor(1, 1, 1, 1);
        #endif
        #if MC_VER <= V1_21_1 guiGraphics.setColor(1, 1, 1, 1); #endif
    }
    #endif
}
