package com.cui.neoforge;

import com.cui.CUI;
import com.cui.ColorScreen;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

#if MC_VER <= V1_20_4
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import com.cui.neoforge.client.config.ClothConfigImpl;
import com.cui.neoforge.client.config.CUI_ConfigNeoForge;
#endif

import static com.cui.CUI.*;

@Mod(MOD_ID)
public final class CUI_NeoForge {

    public CUI_NeoForge() {
        // Initialization
        CUI.initializeServer();

        ModLoadingContext.get().registerExtensionPoint(
                IConfigScreenFactory.class,
                () -> (client, parent) -> new ColorScreen(parent)
        );

        if (FMLEnvironment.dist.isClient()) {
            CUI.initializeClient();
        }
    }
}
