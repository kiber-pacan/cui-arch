package com.cui.fabric.client;

import com.cui.CUI_Common;
import com.cui.fabric.client.config.ExampleConfigFabric;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ClientModInitializer;

public final class CUI_FabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        CUI_Common.initializeClient();

        AutoConfig.register(ExampleConfigFabric.class, Toml4jConfigSerializer::new);
    }
}