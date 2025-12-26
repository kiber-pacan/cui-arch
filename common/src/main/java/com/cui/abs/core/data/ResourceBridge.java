package com.cui.abs.core.data;


#if MC_VER >= V1_21_11
import net.minecraft.resources.Identifier;
#else
import net.minecraft.resources.ResourceLocation;
#endif

public class ResourceBridge {
    public static #if MC_VER >= V1_21_11 Identifier #else ResourceLocation #endif spriteDefaultNamespace(String location) {
        String formattedLocation =
                #if MC_VER <= V1_20_1
                "textures/gui/sprites/" +
                #endif
                location
                #if MC_VER <= V1_20_1
                + ".png"
                #endif;
        return
                #if MC_VER >= V1_21
                #if MC_VER >= V1_21_11
                Identifier
                #else
                ResourceLocation #endif.withDefaultNamespace(formattedLocation) #else new ResourceLocation(formattedLocation)
                #endif;
    }

    public static #if MC_VER >= V1_21_11 Identifier #else ResourceLocation #endif spriteNamespace(String namespace, String location) {
        String formattedLocation =
                #if MC_VER <= V1_20_1
                "textures/gui/sprites/" +
                #endif
                location
                #if MC_VER <= V1_20_1
                 + ".png"
                 #endif;
        return
                #if MC_VER >= V1_21
                #if MC_VER >= V1_21_11
                Identifier
                #else
                ResourceLocation #endif #if MC_VER >= V1_21_3 .fromNamespaceAndPath #else .fromNamespaceAndPath #endif(namespace, formattedLocation) #else new ResourceLocation(namespace, formattedLocation)
                #endif;
    }
}
