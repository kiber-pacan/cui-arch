package com.cui.fabric;

import net.fabricmc.api.ModInitializer;

import com.cui.CUI;

import java.util.logging.Logger;

public final class CUI_Fabric implements ModInitializer {

    @Override
    public void onInitialize() {
        CUI.initializeServer();
    }
}
