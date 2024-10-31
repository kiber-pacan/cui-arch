package com.cui.neoforge.client;

import me.shedaniel.autoconfig.AutoConfig;
#if MC_VER >= V1_21
#else
    import net.neoforged.fml.common.Mod;
#endif

#if MC_VER >= V1_21
    //@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
#else
    //@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
#endif
public class CUI_EventHandler {
}