package com.cui.mixin.client;

#if MC_VER >= V1_21_6
import com.cui.CUI;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.contextualbar.ContextualBarRenderer;
import net.minecraft.client.gui.contextualbar.ExperienceBarRenderer;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.inventory.AbstractRecipeBookScreen;
#endif
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(#if MC_VER >= V1_21_6 ContextualBarRenderer.class #else Minecraft.class #endif)
public interface ExperienceBarRendererMixin {
    #if MC_VER >= V1_21_6
    // XP bar
    @Inject(at = @At(value = "TAIL"), method = "renderExperienceLevel")
    private static void injected1(GuiGraphics guiGraphics, Font font, int level, CallbackInfo ci) {
        Component component1 = Component.translatable("gui.experience.level", level);
        int i1 = (guiGraphics.guiWidth() - font.width(component1)) / 2;
        int j1 = guiGraphics.guiHeight() - 24 - 9 - 2;
        guiGraphics.drawString(font, component1, i1, j1, CUI.cuiConfig.getRGB(), false);
    }
    #endif
}
