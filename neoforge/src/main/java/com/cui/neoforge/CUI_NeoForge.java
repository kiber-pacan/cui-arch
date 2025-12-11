package com.cui.neoforge;

import com.cui.core.CUI;
import com.cui.core.ColorScreen;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
#if MC_VER >= V1_21
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
#else
import net.neoforged.neoforge.client.ConfigScreenHandler;
#endif

import static com.cui.core.CUI.*;

@Mod(MOD_ID)
public final class CUI_NeoForge {

    public CUI_NeoForge() {
        // Initialization
        CUI.initializeServer();

        ModLoadingContext.get().registerExtensionPoint(
                #if MC_VER >= V1_21
                IConfigScreenFactory.class,
                () -> (client, parent) -> new ColorScreen(parent)
                #else
                ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory(
                (client, parent) -> new ColorScreen(parent))
                #endif
        );

        if (#if MC_VER >= V1_21_9 FMLEnvironment.getDist().isClient() #else FMLEnvironment.dist.isClient() #endif) {
            CUI.initializeClient();
        }
    }
}
