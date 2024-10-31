package com.cui.forge;

import com.cui.CUI_Common;
import dev.architectury.platform.forge.EventBuses;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import net.minecraftforge.fml.loading.FMLLoader;

@Mod(CUI_Common.MODID)
public final class CUI_Forge {
    public CUI_Forge() {
        EventBuses.registerModEventBus(CUI_Common.MODID, MinecraftForge.EVENT_BUS);
        CUI_Common.initializeServer();

        if (FMLLoader.getDist() == Dist.CLIENT) {
            AutoConfig.register(com.cui.forge.client.config.CUI_ConfigForge.class, Toml4jConfigSerializer::new);
        }
    }
}
