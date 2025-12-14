package com.cui.abs.core.mixin.rendering.gui;

import net.minecraft.client.gui.GuiGraphics;
#if MC_VER <= V1_21_5 && MC_VER >= V1_20_4 import net.minecraft.client.gui.GuiSpriteManager; #endif
#if MC_VER >= V1_20_4
import net.minecraft.client.resources.metadata.gui.GuiSpriteScaling;
#endif
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;


#if MC_VER >= V1_21_5
#endif

/**<h1> version differences list </h1>
 * <li> 1.21.6 - 1.21.10 </li>
 * <li> 1.21.3 - 1.21.5 </li>
*/
@Mixin(GuiGraphics.class)
public interface GuiGraphicsAccessor {
    #if MC_VER >= V1_20_4

    #if MC_VER >= V1_21_6
    @Accessor("guiSprites")
    TextureAtlas getGuiSprites();
    #else
    @Accessor("sprites")
    GuiSpriteManager getGuiSprites();
    #endif

    #if MC_VER >= V1_21_6
    @Invoker("getSpriteScaling")
    static GuiSpriteScaling getSpriteScaling(TextureAtlasSprite sprite) {
        return null;
    }
    #endif

    #endif
}
