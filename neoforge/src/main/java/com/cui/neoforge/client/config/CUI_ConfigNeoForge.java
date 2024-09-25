package com.cui.neoforge.client.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

import static com.cui.CUI_Common.MODID;

@Config(name = MODID)
public class CUI_ConfigNeoForge implements ConfigData {
    public String color;
}