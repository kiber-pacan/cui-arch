package com.cui.mixin.client.screen.container;

#if MC_VER >= V1_21_6 import com.cui.abs.core.data.data.GuiGraphicsMethods;
import com.cui.core.CUI;
import com.mojang.blaze3d.pipeline.RenderPipeline; #endif
import net.minecraft.client.gui.GuiGraphics;
#if MC_VER >= V1_21_6
import net.minecraft.client.gui.screens.inventory.CrafterScreen;
#endif
import net.minecraft.client.gui.screens.inventory.GrindstoneScreen;
#if MC_VER >= V1_21_11
import net.minecraft.resources.Identifier;
#else
import net.minecraft.resources.ResourceLocation;
#if MC_VER >= V1_21_3
import net.minecraft.client.renderer.RenderType;
#endif
#endif
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(#if MC_VER >= V1_21_6 CrafterScreen.class #else Minecraft.class #endif)
public class CrafterMixin {
    #if MC_VER >= V1_21_6
    @Redirect(at = @At(value = "INVOKE", target = GuiGraphicsMethods.blit8), method = "renderBg")
    private static void injected(GuiGraphics instance, RenderPipeline pipeline, #if MC_VER >= V1_21_11 Identifier #else ResourceLocation #endif atlas, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight) {
        instance.blit(pipeline, atlas, x, y, u, v, width, height, textureWidth, textureHeight, CUI.cuiConfig.getRGB());
    }

    #endif
}
