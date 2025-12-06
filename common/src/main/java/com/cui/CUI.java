package com.cui;


import com.cui.config.CUI_Config;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.shaders.ShaderType;
import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.ResourceLocation;

import java.io.IOException;
import java.util.logging.Logger;

public final class CUI {
    public static final String MOD_ID = "cui";
    public static final Logger LOGGER = Logger.getLogger(MOD_ID);

    public static KeyMapping OPEN_CUI_CONFIG;

    public static CUI_Config cuiConfig = new CUI_Config();

    public static int mixColors(int color1, int color2) {
        int a1 = (color1 >> 24) & 0xFF;
        int r1 = (color1 >> 16) & 0xFF;
        int g1 = (color1 >> 8) & 0xFF;
        int b1 = color1 & 0xFF;

        int a2 = (color2 >> 24) & 0xFF;
        int r2 = (color2 >> 16) & 0xFF;
        int g2 = (color2 >> 8) & 0xFF;
        int b2 = color2 & 0xFF;

        int a = (a1 + a2) / 2;
        int r = (r1 + r2) / 2;
        int g = (g1 + g2) / 2;
        int b = (b1 + b2) / 2;

        return (a << 24) | (r << 16) | (g << 8) | b;
    }

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
