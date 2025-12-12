package com.cui.abs.core.mixin.rendering.gui;

import net.minecraft.client.gui.GuiGraphics;
#if MC_VER <= V1_21_5 import net.minecraft.client.gui.GuiSpriteManager; #endif
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.metadata.gui.GuiSpriteScaling;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;


#if MC_VER >= V1_21_5
#endif

/**<h1> version differences list </h1>
 * <li> 1.21.6 - 1.21.10 </li>
 * <li> 1.21.3 - 1.21.5 </li>
*/
@Mixin(GuiGraphics.class)
public interface GuiGraphicsMixin {
    #if MC_VER >= V1_21_6
    @Accessor("guiSprites")
    TextureAtlas getGuiSprites();
    #elif MC_VER >= V1_21_3
    @Accessor("sprites")
    GuiSpriteManager getGuiSprites();
    #endif

    #if MC_VER >= V1_21_6
    @Invoker("getSpriteScaling")
    static GuiSpriteScaling getSpriteScaling(TextureAtlasSprite sprite) {
        return null;
    }
    #endif
}
