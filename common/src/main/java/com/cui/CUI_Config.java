package com.cui;

#if MC_VER >= V1_20_4
import com.cui.CUI;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.ColorControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import dev.isxander.yacl3.impl.controller.FloatSliderControllerBuilderImpl;
import dev.isxander.yacl3.platform.YACLPlatform;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;
#elif MC_VER > V1_18_2
import eu.midnightdust.lib.config.MidnightConfig;
#else
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
#endif

#if MC_VER <= V1_18_2 @Config(name = Ipla.MOD_ID) #endif
public class CUI_Config #if  MC_VER > V1_18_2 && MC_VER < V1_20_4 extends MidnightConfig #elif MC_VER <= V1_18_2 implements ConfigData #endif{
    #if MC_VER >= V1_20_4
    public static ConfigClassHandler<CUI_Config> HANDLER = ConfigClassHandler.createBuilder(CUI_Config.class)
            .id(#if MC_VER >= V1_21 ResourceLocation.fromNamespaceAndPath #else new ResourceLocation #endif(CUI.MOD_ID, "ipla_config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(YACLPlatform.getConfigDir().resolve("ipla_config.json5"))
                    .setJson5(true)
                    .build())
            .build();

    @SerialEntry
    public Color color = new Color(153, 204, 133);

    public Screen getScreen(Screen parentScreen) {
        return YetAnotherConfigLib.createBuilder()
                .title(Component.literal("CUI config."))
                .category(ConfigCategory.createBuilder()
                        .name(Component.literal("CUI config"))
                        .tooltip(Component.literal("Main CUI tab."))
                        .group(OptionGroup.createBuilder()
                                .name(Component.literal("Main settings"))
                                .description(OptionDescription.of(Component.literal("Main CUI settings.")))
                                .option(Option.<Color>createBuilder()
                                        .name(Component.literal("Color"))
                                        .description(OptionDescription.of(Component.literal("Color.")))
                                        .binding(new Color(153, 204, 133), () -> this.color, newVal -> this.color = newVal)
                                        .controller(ColorControllerBuilder::create)
                                        .build())
                                .build())
                        .build())
                .build().generateScreen(parentScreen);
    }
    #elif MC_VER > V1_18_2
    #if MC_VER > V1_19_2 public static final String MAIN_CATEGORY = "text"; #endif

    @MidnightConfig.Entry(#if MC_VER > V1_19_2 category = MAIN_CATEGORY #endif)
    public static boolean oldRendering = false;

    @MidnightConfig.Entry(#if MC_VER > V1_19_2 category = MAIN_CATEGORY, #endif isSlider = true, precision = 1000, min = 0.05f, max = 2.0f)
    public static float absSize = 1f;

    @MidnightConfig.Entry(#if MC_VER > V1_19_2 category = MAIN_CATEGORY, #endif isSlider = true, precision = 1000, min = 0.05f, max = 2.0f)
    public static float iSize = 1f;

    @MidnightConfig.Entry(#if MC_VER > V1_19_2 category = MAIN_CATEGORY, #endif isSlider = true, precision = 1000, min = 0.05f, max = 2.0f)
    public static float bSize = 0.75f;
    #else
    public boolean oldRendering = false;
    public float absSize = 1f;
    public float iSize = 1f;
    public float bSize = 0.75f;
    #endif
}
