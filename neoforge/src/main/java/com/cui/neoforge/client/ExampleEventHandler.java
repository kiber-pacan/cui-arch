package com.cui.neoforge.client;

import net.neoforged.api.distmarker.Dist;
#if MC_VER >= V1_21
    import net.neoforged.fml.common.EventBusSubscriber;
#else
    import net.neoforged.fml.common.Mod;
#endif

import static com.cui.CUI_Common.MODID;

#if MC_VER >= V1_21
    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
#else
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
#endif
public class ExampleEventHandler {

}