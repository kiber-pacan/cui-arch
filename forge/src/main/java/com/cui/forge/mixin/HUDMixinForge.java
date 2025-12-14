package com.cui.forge.mixin;


import com.cui.abs.core.data.Rectangle;
import com.cui.abs.core.data.ResourceBridge;
import com.cui.abs.core.rendering.gui.GuiRenderer;
import com.cui.core.CUI;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

// FUCK STUPID FORGE AND ITS STUPID API
@Mixin(ForgeGui.class)
public class HUDMixinForge {

    @Shadow
    @Final
    private static Logger LOGGER;

    /**
     * <h5> Air bubbles coloring <h5/>
     * */
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIIIII)V", ordinal = 0), method = "renderAir")
    private void renderAir1(GuiGraphics instance, ResourceLocation atlasLocation, int x, int y, int uOffset, int vOffset, int textureWidth, int textureHeight) {
        // Degenerate hack because of the degenerate algorithm
        GuiRenderer.blitSprite(instance, "GUI_TEXTURED", ResourceBridge.spriteNamespace(CUI.MOD_ID, (uOffset == 16) ? "hud/air_container" : "hud/air_bursting_container"), new Rectangle(x, y, textureWidth, textureHeight), CUI.cuiConfig.getRGB());
        GuiRenderer.blitSprite(instance, "GUI_TEXTURED", ResourceBridge.spriteNamespace(CUI.MOD_ID,  (uOffset == 16) ? "hud/air" : "hud/air_bursting"), new Rectangle(x, y, textureWidth, textureHeight), CUI.cuiConfig.getRGB());
    }

    /**
     * <h5> Armor coloring <h5/>
     * */
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIIIII)V", ordinal = 0), method = "renderArmor")
    private void renderArmor1(GuiGraphics instance, ResourceLocation atlasLocation, int x, int y, int uOffset, int vOffset, int textureWidth, int textureHeight) {
        GuiRenderer.blitSprite(instance, "GUI_TEXTURED", ResourceBridge.spriteNamespace(CUI.MOD_ID, "hud/armor_container_half"), new Rectangle(x, y, textureWidth, textureHeight), CUI.cuiConfig.getRGB());
        GuiRenderer.blitSprite(instance, "GUI_TEXTURED", ResourceBridge.spriteNamespace(CUI.MOD_ID, "hud/armor_full"), new Rectangle(x, y,  textureWidth, textureHeight), CUI.cuiConfig.getRGB());
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIIIII)V", ordinal = 1), method = "renderArmor")
    private void renderArmor2(GuiGraphics instance, ResourceLocation sprite, int x, int y, int u, int v, int textureWidth, int textureHeight) {
        GuiRenderer.blitSprite(instance, "GUI_TEXTURED", ResourceBridge.spriteNamespace(CUI.MOD_ID, "hud/armor_container_half"), new Rectangle(x, y, textureWidth, textureHeight), CUI.cuiConfig.getRGB());
        GuiRenderer.blitSprite(instance, "GUI_TEXTURED", ResourceBridge.spriteNamespace(CUI.MOD_ID, "hud/armor_half"), new Rectangle(x, y, textureWidth, textureHeight), CUI.cuiConfig.getRGB());
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIIIII)V", ordinal = 2), method = "renderArmor")
    private void renderArmor3(GuiGraphics instance, ResourceLocation sprite, int x, int y, int u, int v, int textureWidth, int textureHeight) {
        GuiRenderer.blitSprite(instance, "GUI_TEXTURED", ResourceBridge.spriteNamespace(CUI.MOD_ID, "hud/armor_container"), new Rectangle(x, y, textureWidth, textureHeight), CUI.cuiConfig.getRGB());
    }

    /**
     * <h5> Food coloring <h5/>
     * */
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIIIII)V", ordinal = 0), method = "renderFood")
    private void renderFood1(GuiGraphics instance, ResourceLocation sprite, int x, int y, int u, int v, int textureWidth, int textureHeight) {
        String hunger = (sprite.toString().contains("hunger")) ? "_hunger" : "";
        GuiRenderer.blitSprite(instance, "GUI_TEXTURED", ResourceBridge.spriteNamespace(CUI.MOD_ID, "hud/food_empty" + hunger), new Rectangle(x, y, textureWidth, textureHeight), CUI.cuiConfig.getRGB());
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIIIII)V", ordinal = 1), method = "renderFood")
    private void renderFood2(GuiGraphics instance, ResourceLocation sprite, int x, int y, int u, int v, int textureWidth, int textureHeight) {
        String hunger = (sprite.toString().contains("hunger")) ? "_hunger" : "";
        GuiRenderer.blitSprite(instance, "GUI_TEXTURED", ResourceBridge.spriteNamespace(CUI.MOD_ID, "hud/food_full" + hunger), new Rectangle(x, y, textureWidth, textureHeight), CUI.cuiConfig.getRGB());
        GuiRenderer.blitSprite(instance, "GUI_TEXTURED", ResourceBridge.spriteNamespace(CUI.MOD_ID, "hud/food_full" + hunger + "_bone"), new Rectangle(x, y, textureWidth, textureHeight), CUI.cuiConfig.getRGB());

    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIIIII)V", ordinal = 2), method = "renderFood")
    private void remderFood3(GuiGraphics instance, ResourceLocation sprite, int x, int y, int u, int v, int textureWidth, int textureHeight) {
        String hunger = (sprite.toString().contains("hunger")) ? "_hunger" : "";
        GuiRenderer.blitSprite(instance, "GUI_TEXTURED", ResourceBridge.spriteNamespace(CUI.MOD_ID, "hud/food_half" + hunger), new Rectangle(x, y, textureWidth, textureHeight), CUI.cuiConfig.getRGB());
        GuiRenderer.blitSprite(instance, "GUI_TEXTURED", ResourceBridge.spriteNamespace(CUI.MOD_ID, "hud/food_half" + hunger + "_bone"), new Rectangle(x, y, textureWidth, textureHeight), CUI.cuiConfig.getRGB());
    }
}