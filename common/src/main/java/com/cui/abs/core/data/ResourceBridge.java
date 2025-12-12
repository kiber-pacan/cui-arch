package com.cui.abs.core.data;

import net.minecraft.resources.ResourceLocation;

public class ResourceBridge {
    public static ResourceLocation withDefaultNamespace(String namespace) {
        return #if MC_VER >= V1_21 ResourceLocation.withDefaultNamespace(namespace) #else new ResourceLocation(namespace) #endif;
    }
}
