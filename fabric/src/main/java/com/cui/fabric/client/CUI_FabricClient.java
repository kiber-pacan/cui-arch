package com.cui.fabric.client;

import com.cui.core.CUI;
import net.fabricmc.api.ClientModInitializer;

public final class CUI_FabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        CUI.initializeClient();

    }
}
