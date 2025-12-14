package com.cui.forge;

import com.cui.core.CUI;
import com.cui.core.ColorScreen;
import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import net.minecraftforge.fml.loading.FMLLoader;

@Mod(CUI.MOD_ID)
public final class CUI_Forge {
    public CUI_Forge(FMLJavaModLoadingContext context) {
        EventBuses.registerModEventBus(CUI.MOD_ID, MinecraftForge.EVENT_BUS);
        CUI.initializeServer();

        context.registerExtensionPoint(
                ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory(
                        (client, parent) -> new ColorScreen(parent))
        );

        if (FMLLoader.getDist() == Dist.CLIENT) {
            CUI.initializeClient();

        }
    }
}
