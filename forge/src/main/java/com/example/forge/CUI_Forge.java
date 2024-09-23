package com.cui.forge;

import com.cui.forge.client.config.ExampleConfigForge;
import dev.architectury.platform.forge.EventBuses;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import com.cui.ExampleCommon;
import net.minecraftforge.fml.loading.FMLLoader;

@Mod(ExampleCommon.MODID)
public final class ExampleForge {
    public ExampleForge() {
        EventBuses.registerModEventBus(ExampleCommon.MODID, FMLJavaModLoadingContext.get().getModEventBus());
        ExampleCommon.initializeServer();

        if (FMLLoader.getDist() == Dist.CLIENT) {
            AutoConfig.register(ExampleConfigForge.class, Toml4jConfigSerializer::new);
        }
    }
}
