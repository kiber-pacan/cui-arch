package com.cui.neoforge;

import com.cui.CUI;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLLoader;

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

        #if MC_VER <= V1_20_4
        if (FMLLoader.getDist() == net.neoforged.api.distmarker.Dist.CLIENT) {
            AutoConfig.register(CUI_ConfigNeoForge.class, Toml4jConfigSerializer::new);
            ClothConfigImpl.registerModsPage();
        }
        #endif
    }
}
