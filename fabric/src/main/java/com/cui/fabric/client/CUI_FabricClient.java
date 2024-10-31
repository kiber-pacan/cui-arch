package com.cui.fabric.client;

import com.cui.CUI;
import net.fabricmc.api.ClientModInitializer;

#if MC_VER <= V1_20_4
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
#endif

public final class CUI_FabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        CUI.initializeClient();

        #if MC_VER <= V1_20_4
        AutoConfig.register(CUI_ConfigFabric.class, Toml4jConfigSerializer::new);
        #endif
    }
}
