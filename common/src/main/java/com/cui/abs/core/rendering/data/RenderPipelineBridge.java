package com.cui.abs.core.rendering.data;


#if MC_VER >= V1_21_6
import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.renderer.RenderPipelines;
#endif

#if MC_VER <= V1_21_5
import net.minecraft.client.renderer.RenderType;
import java.util.function.Function;
import net.minecraft.resources.ResourceLocation;
#endif

public class RenderPipelineBridge {
    public static #if MC_VER >= V1_21_6 RenderPipeline #else Function<ResourceLocation, RenderType> #endif GUI_TEXTURED() {
        return #if MC_VER >= V1_21_6 RenderPipelines.GUI_TEXTURED #else RenderType::guiTextured #endif;
    }

    /*
        public static #if MC_VER >= V1_21_6 RenderPipeline #else Function<ResourceLocation, RenderType> #endif GUI() {
        return #if MC_VER >= V1_21_6 RenderPipelines.GUI #else RenderType::gui #endif;
    }
    public static #if MC_VER >= V1_21_6 RenderPipeline #else Function<ResourceLocation, RenderType> #endif GUI_TEXT_HIGHLIGHT() {
        return #if MC_VER >= V1_21_6 RenderPipelines.GUI #else RenderType::guiTextHighlight #endif;
    }

    #if MC_VER >= V1_21_6 @Accessor("GUI_TEXT_HIGHLIGHT") #else @Invoker("guiTextHighlight") #endif
    public static #if MC_VER >= V1_21_6 RenderPipeline #else RenderType #endif GUI_TEXT_HIGHLIGHT() {
        return null;
    }

    #if MC_VER >= V1_21_6 @Accessor("GUI_TEXTURED") #else @Invoker("guiTextured") #endif
    public static #if MC_VER >= V1_21_6 RenderPipeline #else RenderType #endif GUI_TEXTURED(#if MC_VER <= V1_21_5 ResourceLocation location #endif ) {
        return null;
    }


    #if MC_VER <= V1_21_5
    @Invoker("guiTexturedOverlay")
    public static RenderType GUI_TEXTURED_OVERLAY(ResourceLocation location) {
        return null;
    }

    @Invoker("guiOverlay")
    public static RenderType GUI_OVERLAY() {
        return null;
    }
    #endif


    #if MC_VER >= V1_21_6
    @Accessor("GUI_TEXTURED_PREMULTIPLIED_ALPHA")
    public static RenderPipeline GUI_TEXTURED_PREMULTIPLIED_ALPHA() {
        return null;
    }

    @Accessor("GUI_INVERT")
    public static RenderPipeline GUI_INVERT() {
        return null;
    }
    #endif
     */
}
