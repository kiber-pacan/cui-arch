package com.cui.abs.core.data;

import net.minecraft.resources.ResourceLocation;

public class ResourceBridge {
    public static ResourceLocation withDefaultNamespace(String location) {
        return #if MC_VER >= V1_21 ResourceLocation.withDefaultNamespace(location) #else new ResourceLocation(location) #endif;
    }

    public static ResourceLocation spriteDefaultNamespace(String location) {
        String formattedLocation = #if MC_VER <= V1_20_1 "textures/gui/sprites/" + #endif location #if MC_VER <= V1_20_1 + ".png" #endif;
        return #if MC_VER >= V1_21 ResourceLocation.withDefaultNamespace(formattedLocation) #else new ResourceLocation(formattedLocation) #endif;
    }

    public static ResourceLocation spriteNamespace(String namespace, String location) {
        String formattedLocation = #if MC_VER <= V1_20_1 "textures/gui/sprites/" + #endif location #if MC_VER <= V1_20_1 + ".png" #endif;
        return #if MC_VER >= V1_21 ResourceLocation #if MC_VER >= V1_21_3 .fromNamespaceAndPath #else .fromNamespaceAndPath #endif(namespace, formattedLocation) #else new ResourceLocation(namespace, formattedLocation) #endif;
    }
}
