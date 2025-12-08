package com.cui.mixin.client.pre1_21_6;


#if MC_VER >= V1_21_3
import net.minecraft.client.gui.screens.inventory.AbstractRecipeBookScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
#endif

@Mixin(#if MC_VER >= V1_21_3 AbstractRecipeBookScreen.class #else Minecraft.class #endif)
public interface RecipeBookMixin {
    #if MC_VER >= V1_21_3
    @Accessor("recipeBookComponent")
    RecipeBookComponent<?> getRecipeBookComponent();
    #if MC_VER >= V1_21_6

    #endif
    #endif
}