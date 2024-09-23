package com.cui.fabric;

import net.fabricmc.api.ModInitializer;

import com.cui.CUI_Common;

public final class CUI_Fabric implements ModInitializer {

    @Override
    public void onInitialize() {
        CUI_Common.initializeServer();
    }
}
