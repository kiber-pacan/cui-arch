package com.cui.neoforge;

import com.cui.neoforge.client.config.ClothConfigImpl;
import com.cui.neoforge.client.config.CUI_ConfigNeoForge;
import com.cui.CUI_Common;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLLoader;

import static com.cui.CUI_Common.*;

@Mod(MODID)
public final class CUI_NeoForge {

    public CUI_NeoForge() {
        // Initialization
        CUI_Common.initializeServer();

        if (FMLLoader.getDist() == net.neoforged.api.distmarker.Dist.CLIENT) {
            AutoConfig.register(CUI_ConfigNeoForge.class, Toml4jConfigSerializer::new);
            ClothConfigImpl.registerModsPage();
        }
    }
}
