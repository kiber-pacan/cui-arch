package com.cui.forge.mixin.client;

import com.cui.forge.client.config.CUI_ConfigForge;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.cui.CUI_Common.colors;

@Mixin(MinecraftClient.class)
public abstract class RenderMixin {
	@Unique
    private static CUI_ConfigForge config;

	@Inject(at = @At("TAIL"), method = "<init>")
	private void constructor(RunArgs args, CallbackInfo ci) {
		config = AutoConfig.getConfigHolder(CUI_ConfigForge.class).getConfig();
	}


	@Unique
	float shelfmod$convert(String hex) {
		return (float) Integer.parseInt(hex, 16) / 255;
	}

	@Inject(at = @At(value = "HEAD"), method = "render")
	private void render(boolean tick, CallbackInfo ci) {
		if (config.color.length() == 7) {
			colors.r = shelfmod$convert("" + config.color.charAt(1) + config.color.charAt(2));
			colors.g = shelfmod$convert("" + config.color.charAt(3) + config.color.charAt(4));
			colors.b = shelfmod$convert("" + config.color.charAt(5) + config.color.charAt(6));
		}
	}
}