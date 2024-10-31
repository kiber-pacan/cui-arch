package com.cui;


import dev.architectury.event.events.client.ClientLifecycleEvent;

import java.awt.*;
import java.util.logging.Logger;

public final class CUI {
    public static final String MOD_ID = "cui";
    public static final Logger LOGGER = Logger.getLogger(MOD_ID);

    public static void initializeServer() {}

    public static void initializeClient() {
        #if MC_VER >= V1_20_4
        ClientLifecycleEvent.CLIENT_STARTED.register((minecraft) -> {
            CUI_Config.HANDLER.load();
        });

        ClientLifecycleEvent.CLIENT_STOPPING.register((minecraft) -> {
            CUI_Config.HANDLER.save();
        });
        #endif
    }
}
