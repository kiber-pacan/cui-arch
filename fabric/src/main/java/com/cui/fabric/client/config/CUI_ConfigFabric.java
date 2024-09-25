package com.cui.fabric.client.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

import static com.cui.CUI_Common.MODID;

@Config(name = MODID)
public class CUI_ConfigFabric implements ConfigData {
    public String color = "#389c56";

}