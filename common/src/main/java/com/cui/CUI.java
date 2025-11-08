package com.cui;


import com.cui.config.CUI_Config;
import com.mojang.blaze3d.platform.InputConstants;
import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import net.minecraft.client.KeyMapping;

import java.io.IOException;
import java.util.logging.Logger;

public final class CUI {
    public static final String MOD_ID = "cui";
    public static final Logger LOGGER = Logger.getLogger(MOD_ID);

    public static KeyMapping OPEN_CUI_CONFIG;

    public static CUI_Config cuiConfig = new CUI_Config();

    public static void initializeServer() {}

    public static void initializeClient() {
        OPEN_CUI_CONFIG = new KeyMapping(
                "key.cui.open_config",
                InputConstants.Type.KEYSYM,
                InputConstants.KEY_V,
                "key.categories.cui"
        );

        KeyMappingRegistry.register(OPEN_CUI_CONFIG);

        #if MC_VER >= V1_20_4
        ClientLifecycleEvent.CLIENT_STARTED.register((minecraft) -> {
            try {
                cuiConfig.loadConfig();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        ClientLifecycleEvent.CLIENT_STOPPING.register((minecraft) -> {
            cuiConfig.saveConfig();
        });
        #endif
    }
}
