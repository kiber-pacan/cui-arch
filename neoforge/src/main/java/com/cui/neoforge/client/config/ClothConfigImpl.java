package com.cui.neoforge.client.config;

#if MC_VER >= V1_21
    import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
#else
#endif

public class ClothConfigImpl {
    public static void registerModsPage() {
        #if MC_VER != V1_20_4
        ModLoadingContext.get().registerExtensionPoint(
                #if MC_VER >= V1_21
                    IConfigScreenFactory.class,
                #else
                    ConfigScreenHandler.ConfigScreenFactory.class,
                #endif
                () -> (mc, screen) -> AutoConfig.getConfigScreen(ShelfModConfigNeoForge.class, screen)#if MC_VER >= V1_21 .get() #endif
        );
        #endif
    }
}
