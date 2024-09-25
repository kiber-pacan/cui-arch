package com.cui.neoforge.client;

import com.cui.neoforge.client.config.CUI_ConfigNeoForge;
import me.shedaniel.autoconfig.AutoConfig;
import net.neoforged.api.distmarker.Dist;
#if MC_VER >= V1_21
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
#else
    import net.neoforged.fml.common.Mod;
#endif

import static com.cui.CUI_Common.MODID;
import static com.cui.CUI_Common.colors;

#if MC_VER >= V1_21
    //@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
#else
    //@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
#endif
public class CUI_EventHandler {
}