package com.cui.abs.core.mixin.rendering.data;


import net.minecraft.client.Minecraft;
#if MC_VER <= V1_20_4
import net.minecraft.client.resources.model.ModelManager;
#endif
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Minecraft.class)
public interface MinecraftMixin {
    #if MC_VER <= V1_20_4
    @Accessor("modelManager")
    ModelManager getModelManager();
    #endif
}
