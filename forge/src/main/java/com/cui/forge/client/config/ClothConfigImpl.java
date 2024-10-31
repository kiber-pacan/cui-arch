package com.cui.forge.client.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
#if MC_VER >= V1_19_4
    import net.minecraftforge.client.ConfigScreenHandler;
#else

#endif

import net.minecraft.screen.ScreenHandlerFactory;
import net.minecraftforge.fml.ModLoadingContext;

public class ClothConfigImpl {
    public static void registerModsPage() {
        AutoConfig.register(CUI_ConfigForge.class, Toml4jConfigSerializer::new);
        #if MC_VER != V1_20_4
        ModLoadingContext.get().registerExtensionPoint(
                #if MC_VER >= V1_19_4
                    ConfigScreenHandler.ConfigScreenFactory.class,
                #else
                ScreenHandlerFactory.class,
                #endif
                () -> new ScreenHandlerFactory(
                        (client, parent) -> AutoConfig.getConfigScreen(CUI_ConfigForge.class, parent).get()
                )
        );
        #endif
    }
}