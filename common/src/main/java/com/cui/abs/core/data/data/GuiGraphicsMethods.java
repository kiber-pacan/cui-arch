package com.cui.abs.core.data.data;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;

//@Mixin(GuiGraphics.class)
public class GuiGraphicsMethods {
    public static final String blitAtlas1 = "Lnet/minecraft/client/gui/GuiGraphics;blit(IIIIILnet/minecraft/client/renderer/texture/TextureAtlasSprite;)V";
    public static final String blitAtlas2 = "Lnet/minecraft/client/gui/GuiGraphics;blit(IIIIILnet/minecraft/client/renderer/texture/TextureAtlasSprite;FFFF)V";

    public static final String blit6 = "Lnet/minecraft/client/gui/GuiGraphics;blit(" #if MC_VER >= V1_21_6 + "Lcom/mojang/blaze3d/pipeline/RenderPipeline;" #elif MC_VER >= V1_21_3 + "Ljava/util/function/Function;" #endif + "Lnet/minecraft/resources/" + #if MC_VER >= V1_21_11 "Identifier" #else "ResourceLocation" #endif + ";IIIIII)V";
    public static final String blit8 = "Lnet/minecraft/client/gui/GuiGraphics;blit(" #if MC_VER >= V1_21_6 + "Lcom/mojang/blaze3d/pipeline/RenderPipeline;" #elif MC_VER >= V1_21_3 + "Ljava/util/function/Function;" #endif + "Lnet/minecraft/resources/" + #if MC_VER >= V1_21_11 "Identifier" #else "ResourceLocation" #endif + ";IIFFIIII)V";
    public static final String blit11 = "Lnet/minecraft/client/gui/GuiGraphics;blit(" #if MC_VER >= V1_21_6 + "Lcom/mojang/blaze3d/pipeline/RenderPipeline;" #elif MC_VER >= V1_21_3 + "Ljava/util/function/Function;" #endif + "Lnet/minecraft/resources/" + #if MC_VER >= V1_21_11 "Identifier" #else "ResourceLocation" #endif + ";IIIIIIIFFII)V";
    public static final String blit10 = "Lnet/minecraft/client/gui/GuiGraphics;blit(" #if MC_VER >= V1_21_6 + "Lcom/mojang/blaze3d/pipeline/RenderPipeline;" #elif MC_VER >= V1_21_3 + "Ljava/util/function/Function;" #endif + "Lnet/minecraft/resources/" + #if MC_VER >= V1_21_11 "Identifier" #else "ResourceLocation" #endif + ";IIIIFFIIII)V";
    public static final String blit9 = "Lnet/minecraft/client/gui/GuiGraphics;blit(" #if MC_VER >= V1_21_6 + "Lcom/mojang/blaze3d/pipeline/RenderPipeline;" #elif MC_VER >= V1_21_3 + "Ljava/util/function/Function;" #endif + "Lnet/minecraft/resources/" + #if MC_VER >= V1_21_11 "Identifier" #else "ResourceLocation" #endif + ";IIIFFIIII)V";
    public static final String blit91 = "Lnet/minecraft/client/gui/GuiGraphics;blit(" #if MC_VER >= V1_21_6 + "Lcom/mojang/blaze3d/pipeline/RenderPipeline;" #elif MC_VER >= V1_21_3 + "Ljava/util/function/Function;" #endif + "Lnet/minecraft/resources/" + #if MC_VER >= V1_21_11 "Identifier" #else "ResourceLocation" #endif + ";IIFFIIIII)V";


    // BlitSprite
    public static final String blitSprite1Rec = #if MC_VER >= V1_20_4 "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(" #if MC_VER >= V1_21_6 + "Lcom/mojang/blaze3d/pipeline/RenderPipeline;" #elif MC_VER >= V1_21_3 + "Ljava/util/function/Function;" #endif + "Lnet/minecraft/resources/" + #if MC_VER >= V1_21_11 "Identifier" #else "ResourceLocation" #endif + ";IIII)V"; #else blit6; #endif
    public static final String blitSprite2Rec = #if MC_VER >= V1_20_4 "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(" #if MC_VER >= V1_21_6 + "Lcom/mojang/blaze3d/pipeline/RenderPipeline;" #elif MC_VER >= V1_21_3 + "Ljava/util/function/Function;" #endif + "Lnet/minecraft/resources/" + #if MC_VER >= V1_21_11 "Identifier" #else "ResourceLocation" #endif + ";IIIIIIII)V"; #else blit6; #endif

    public static final String blitSprite1RecColor = #if MC_VER >= V1_20_4 "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(" #if MC_VER >= V1_21_6 + "Lcom/mojang/blaze3d/pipeline/RenderPipeline;" #elif MC_VER >= V1_21_3 + "Ljava/util/function/Function;" #endif + "Lnet/minecraft/resources/" + #if MC_VER >= V1_21_11 "Identifier" #else "ResourceLocation" #endif + ";IIIII)V"; #else "fuck"; #endif
    public static final String blitSprite2RecColor = #if MC_VER >= V1_20_4 "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(" #if MC_VER >= V1_21_6 + "Lcom/mojang/blaze3d/pipeline/RenderPipeline;" #elif MC_VER >= V1_21_3 + "Ljava/util/function/Function;" #endif + "Lnet/minecraft/resources/" + #if MC_VER >= V1_21_11 "Identifier" #else "ResourceLocation" #endif + ";IIIIIIIII)V"; #else "fuck"; #endif

    public static final String blitSprite1RecAtlas = #if MC_VER >= V1_20_4 "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(" #if MC_VER >= V1_21_6 + "Lcom/mojang/blaze3d/pipeline/RenderPipeline;" #elif MC_VER >= V1_21_3 + "Ljava/util/function/Function;" #endif + "Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;IIIII)V"; #else "fuck"; #endif
    public static final String blitSprite2RecAtlas = #if MC_VER >= V1_20_4 "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(" #if MC_VER >= V1_21_6 + "Lcom/mojang/blaze3d/pipeline/RenderPipeline;" #elif MC_VER >= V1_21_3 + "Ljava/util/function/Function;" #endif + "Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;IIIIIIIII)V"; #else "fuck"; #endif
}
