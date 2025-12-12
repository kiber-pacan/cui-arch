package com.cui.abs.core.rendering.gui;

import com.cui.abs.core.data.Rectangle;
import com.cui.abs.core.mixin.rendering.data.MinecraftMixin;
import com.cui.abs.core.mixin.rendering.gui.GuiGraphicsMixin;
import com.cui.abs.core.rendering.data.RenderPipelineBridge;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
#if MC_VER >= V1_20_4
import net.minecraft.client.resources.metadata.gui.GuiSpriteScaling;
#endif
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

#if MC_VER >= V1_21_6
import com.mojang.blaze3d.pipeline.RenderPipeline;
#endif

import java.util.Map;
import java.util.Objects;


#if MC_VER <= V1_21_5
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.RenderType;
import java.util.function.Function;
#endif

public class GuiRenderer {
    //region Helper
    #if MC_VER >= V1_21
    private static final Map<String, #if MC_VER >= V1_21_6 RenderPipeline #elif MC_VER >= V1_21_3 Function<ResourceLocation, RenderType> #else Runnable #endif > ACTIONS = Map.of(
            "GUI_TEXTURED", RenderPipelineBridge.GUI_TEXTURED()
    );
    #endif

    public record Dimensions(int width, int height) {}


    #if MC_VER >= V1_20_4
    public static GuiSpriteScaling getSpriteScaling(GuiGraphics guiGraphics, ResourceLocation sprite) {
        TextureAtlasSprite textureAtlasSprite = ((GuiGraphicsMixin) guiGraphics).getGuiSprites().getSprite(sprite);

        #if MC_VER >= V1_21_6
        return GuiGraphicsMixin.getSpriteScaling(textureAtlasSprite);
        #else
        return ((GuiGraphicsMixin) guiGraphics).getGuiSprites().getSpriteScaling(textureAtlasSprite);
        #endif
    }


    // Tile
    @Nullable
    public static GuiSpriteScaling.Tile getSpriteTile(GuiGraphics guiGraphics, ResourceLocation sprite) {
        TextureAtlasSprite textureAtlasSprite = ((GuiGraphicsMixin) guiGraphics).getGuiSprites().getSprite(sprite);

        return getSpriteTile(#if MC_VER <= V1_21_5 guiGraphics, #endif textureAtlasSprite);
    }

    @Nullable
    public static GuiSpriteScaling.Tile getSpriteTile(#if MC_VER <= V1_21_5 GuiGraphics guiGraphics, #endif TextureAtlasSprite textureAtlasSprite) {
        GuiSpriteScaling spriteScaling = #if MC_VER >= V1_21_6 GuiGraphicsMixin #else ((GuiGraphicsMixin) guiGraphics).getGuiSprites() #endif .getSpriteScaling(textureAtlasSprite);

        Objects.requireNonNull(spriteScaling);

        return (spriteScaling.type() == GuiSpriteScaling.Type.TILE) ? (GuiSpriteScaling.Tile) spriteScaling : null;
    }
    #endif

    /**
     * <h5> Sprite dimensions </h5>
     * Basically returns sprite image size
     */
    protected static Dimensions getSpriteContentsDimensions(GuiGraphics guiGraphics, ResourceLocation sprite) {
        TextureAtlasSprite textureAtlasSprite = #if MC_VER >= V1_20_4 ((GuiGraphicsMixin) guiGraphics).getGuiSprites().getSprite(sprite); #else ((MinecraftMixin) Minecraft.getInstance()).getModelManager().getAtlas(sprite).getSprite(sprite); #endif

        return getSpriteContentsDimensions(#if MC_VER <= V1_21_5 guiGraphics, #endif textureAtlasSprite);
    }

    public static Dimensions getSpriteContentsDimensions(#if MC_VER <= V1_21_5 GuiGraphics guiGraphics, #endif TextureAtlasSprite textureAtlasSprite) {
        #if MC_VER >= V1_20_4 GuiSpriteScaling.Tile spriteTile = getSpriteTile(#if MC_VER <= V1_21_5 guiGraphics, #endif textureAtlasSprite); #endif

        return new Dimensions(
                #if MC_VER >= V1_20_4 (spriteTile != null) ? spriteTile.width() : #endif textureAtlasSprite.contents().width(),
                #if MC_VER >= V1_20_4 (spriteTile != null) ? spriteTile.height() : #endif textureAtlasSprite.contents().height()
        );
    }

    /**
     * <h5> Texture dimensions </h5>
     * Returns texture dimensions in atlas
     */
    protected static Dimensions getTextureDimensions(GuiGraphics guiGraphics, ResourceLocation sprite, Dimensions spriteDimensions) {
        TextureAtlasSprite textureAtlasSprite = #if MC_VER >= V1_20_4 ((GuiGraphicsMixin) guiGraphics).getGuiSprites().getSprite(sprite); #else ((MinecraftMixin) Minecraft.getInstance()).getModelManager().getAtlas(new ResourceLocation("textures/atlas/decorated_pot.png")).getSprite(sprite); #endif

        return getTextureDimensions(#if MC_VER <= V1_21_5 guiGraphics, #endif textureAtlasSprite, spriteDimensions);
    }

    public static Dimensions getTextureDimensions(#if MC_VER <= V1_21_5 GuiGraphics guiGraphics, #endif TextureAtlasSprite textureAtlasSprite, Dimensions spriteDimensions) {
        Dimensions spriteContentsDimensions = getSpriteContentsDimensions(#if MC_VER <= V1_21_5 guiGraphics, #endif textureAtlasSprite);

        return new Dimensions(
                (int) (((float) spriteDimensions.width / spriteContentsDimensions.width) * spriteContentsDimensions.width + 0.9f),
                (int) (((float) spriteDimensions.height / spriteContentsDimensions.height) * spriteContentsDimensions.height + 0.9f)
        );
    }


    //region color
    #if MC_VER <= V1_21_5
    /**
     * <h5> Set shader color </h5>
     * Just sets shader color :/
     * Not to be used in versions after 1.21.5
     * @param guiGraphics Graphics instance
     * @param red Red color value 0 - 1
     * @param green Green color value 0 - 1
     * @param blue Blue color value 0 - 1
     * @param alpha Alpha color value 0 - 1
     * <p>
     * Not to be used in versions after 1.21.5
     */
    public static void setShaderColor(GuiGraphics guiGraphics, float red, float green, float blue, float alpha) {
        guiGraphics.flush();
        RenderSystem.setShaderColor(red, green, blue, alpha);
    }

    /**
     * <h5> Set shader color </h5>
     * I think it's good to have that
     * @param guiGraphics Graphics instance
     * @param color Color value represented as integer
     * <p>
     * Not to be used in versions after 1.21.5
     */
    public static void setShaderColor(GuiGraphics guiGraphics, int color) {
        guiGraphics.flush();
        RenderSystem.setShaderColor(
                ((color >> 16) & 0xFF) / 255.0f,
                ((color >> 8) & 0xFF) / 255.0f,
                ((color) & 0xFF) / 255.0f,
                ((color >> 24) & 0xFF) / 255.0f
        );
    }

    /**
     * <h5> Set shader alpha </h5>
     * Why not?
     * @param guiGraphics Graphics instance
     * @param alpha Alpha color value 0 - 1
     * <p>
     * Not to be used in versions after 1.21.5
     */
    public static void setShaderAlpha(GuiGraphics guiGraphics, int alpha) {
        guiGraphics.flush();
        RenderSystem.setShaderColor(1, 1, 1, alpha);
    }

    /**
     * <h5> Clear shader color </h5>
     * ts pmo, get tf out!
     * <p>
     * Not to be used in versions after 1.21.5
     */
    public static void clearShaderColor(GuiGraphics guiGraphics) {
        guiGraphics.flush();
        RenderSystem.setShaderColor(1, 1, 1, 1);
    }
    #endif
    //endregion


    //region blitSprite abstract
    /**
     * <h5> Blit sprite </h5>
     * @param guiGraphics GUI graphics object
     * <p>
     * pipeline - graphics pipeline
     * @param sprite sprite to render
     * @param blitRectangle Blit rectangle (x, y, width, height)
     * @param textureRectangle Texture rectangle (y, v, texture width, texture height)
     */
    public static void blitSprite(
            GuiGraphics guiGraphics,
            String pipeline,
            ResourceLocation sprite,
            @NotNull Rectangle blitRectangle,
            @NotNull Rectangle textureRectangle
    ) {
        blitSprite(guiGraphics, pipeline, sprite, blitRectangle, textureRectangle, null);
    }


    /**
     * <h5> Blit sprite </h5>
     * @param guiGraphics GUI graphics object
     * <p>
     * pipeline - graphics pipeline
     * @param sprite sprite to render
     * @param blitRectangle Blit rectangle (x, y, width, height)
     * @param textureRectangle Texture rectangle (y, v, texture width, texture height)
     */
    public static void blitSprite(
            GuiGraphics guiGraphics,
            String pipeline,
            ResourceLocation sprite,
            @NotNull Rectangle blitRectangle,
            @NotNull Rectangle textureRectangle,
            @Nullable Integer color
    ) {
        if (blitRectangle.isEmpty()) throw new RuntimeException("blitRectangle position/dimensions is not provided");
        int x = blitRectangle.position.first;
        int y = blitRectangle.position.second;

        if (!blitRectangle.isDimensionsFilled()) {
            int u = textureRectangle.position.first;
            int v = textureRectangle.position.second;

            int textureWidth = textureRectangle.dimensions.first;
            int textureHeight = textureRectangle.dimensions.second;

            blitSprite(guiGraphics, #if MC_VER >= V1_21 ACTIONS.get(pipeline), #endif sprite, u, v, textureWidth, textureHeight, x, y, color);
        }

        int width = blitRectangle.dimensions.first;
        int height = blitRectangle.dimensions.second;

        if (textureRectangle.isPositionFilled()) {
            int u = textureRectangle.position.first;
            int v = textureRectangle.position.second;

            if (textureRectangle.isDimensionsFilled()) {
                int textureWidth = textureRectangle.dimensions.first;
                int textureHeight = textureRectangle.dimensions.second;

                blitSprite(guiGraphics, #if MC_VER >= V1_21 ACTIONS.get(pipeline), #endif sprite, u, v, textureWidth, textureHeight, x, y, width, height, color);
            } else {
                blitSprite(guiGraphics, #if MC_VER >= V1_21 ACTIONS.get(pipeline), #endif sprite, u, v, x, y, width, height, color);
            }
        } else {
            throw new RuntimeException("textureRectangle UV is empty");
        }
    }


    /**
     * <h5> Blit sprite </h5>
     * @param guiGraphics GUI graphics object
     * <p>
     * pipeline - graphics pipeline
     * @param sprite sprite to render
     * @param blitRectangle Blit rectangle (x, y, width, height)
     */
    public static void blitSprite(
            GuiGraphics guiGraphics,
            String pipeline,
            ResourceLocation sprite,
            @NotNull Rectangle blitRectangle
    ) {
        blitSprite(guiGraphics, pipeline, sprite, blitRectangle, (Integer) null);
    }


    /**
     * <h5> Blit sprite </h5>
     * @param guiGraphics GUI graphics object
     * <p>
     * pipeline - graphics pipeline
     * @param sprite sprite to render
     * @param blitRectangle Blit rectangle (x, y, width, height)
     * @param color Color as integer
     */
    public static void blitSprite(
            GuiGraphics guiGraphics,
            String pipeline,
            ResourceLocation sprite,
            @NotNull Rectangle blitRectangle,
            @Nullable Integer color
    ) {
        if (!blitRectangle.isPositionFilled()) throw new RuntimeException("blitRectangle position is not provided");

        int x = blitRectangle.position.first;
        int y = blitRectangle.position.second;

        if (blitRectangle.isDimensionsFilled()) {
            int width = blitRectangle.dimensions.first;
            int height = blitRectangle.dimensions.second;

            blitSprite(guiGraphics, #if MC_VER >= V1_21 ACTIONS.get(pipeline), #endif sprite, x, y, width, height, color);
        } else {
            blitSprite(guiGraphics, #if MC_VER >= V1_21 ACTIONS.get(pipeline), #endif sprite, x, y, color);
        }
    }
    //endregion


    //region blitSprite
    private static void blitSpriteAbstract(GuiGraphics guiGraphics, @Nullable Integer color, Runnable action) {
        if (color != null) {
        #if MC_VER <= V1_21_5
            setShaderColor(guiGraphics, color);
        #endif
            action.run();
        #if MC_VER <= V1_21_5
            clearShaderColor(guiGraphics);
        #endif
        } else {
            action.run();
        }
    }

    protected static void blitSprite(
            GuiGraphics guiGraphics,
            #if MC_VER >= V1_21_6 RenderPipeline pipeline, #elif MC_VER >= V1_21_3 Function<ResourceLocation, RenderType> pipeline, #endif
            ResourceLocation sprite,
            int x, int y,
            @Nullable Integer color
    ) {
        Dimensions spriteDimensions = getSpriteContentsDimensions(guiGraphics, sprite);
        blitSpriteAbstract(guiGraphics, color, () -> guiGraphics #if MC_VER >= V1_20_4 .blitSprite(#if MC_VER >= V1_21_3 pipeline, #endif sprite, x, y, spriteDimensions.width, spriteDimensions.height #if MC_VER >= V1_21_6 , (color != null) ? color : -1 #endif)); #else .blit(sprite, x, y, 0, 0, spriteDimensions.width, spriteDimensions.height, spriteDimensions.width, spriteDimensions.height)); #endif
    }

    protected static void blitSprite(
            GuiGraphics guiGraphics,
            #if MC_VER >= V1_21_6 RenderPipeline pipeline, #elif MC_VER >= V1_21_3 Function<ResourceLocation, RenderType> pipeline, #endif
            ResourceLocation sprite,
            int x, int y,
            int width, int height,
            @Nullable Integer color
    ) {
        #if MC_VER <= V1_20_1
        Dimensions textureDimensions = getTextureDimensions(guiGraphics, sprite, new Dimensions(width, height));
        #endif

        blitSpriteAbstract(guiGraphics, color, () -> guiGraphics #if MC_VER >= V1_20_4 .blitSprite(#if MC_VER >= V1_21_3 pipeline, #endif sprite, x, y, width, height #if MC_VER >= V1_21_6 , (color != null) ? color : -1 #endif)); #else .blit(sprite, x, y, 0, 0, width, height, textureDimensions.width, textureDimensions.height)); #endif
    }

    protected static void blitSprite(
            GuiGraphics guiGraphics,
            #if MC_VER >= V1_21_6 RenderPipeline pipeline, #elif MC_VER >= V1_21_3 Function<ResourceLocation, RenderType> pipeline, #endif
            ResourceLocation sprite,
            int u, int v,
            int x, int y,
            int width, int height,
            @Nullable Integer color
    ) {
        Dimensions textureDimensions = getTextureDimensions(guiGraphics, sprite, new Dimensions(width, height));
        blitSpriteAbstract(guiGraphics, color, () -> guiGraphics #if MC_VER >= V1_20_4 .blitSprite(#if MC_VER >= V1_21_3 pipeline, #endif sprite, textureDimensions.width, textureDimensions.height, u, v, x, y, width, height #if MC_VER >= V1_21_6 , (color != null) ? color : -1 #endif)); #else .blit(sprite, x, y, u, v, width, height, textureDimensions.width, textureDimensions.height)); #endif
    }

    protected static void blitSprite(
            GuiGraphics guiGraphics,
            #if MC_VER >= V1_21_6 RenderPipeline pipeline, #elif MC_VER >= V1_21_3 Function<ResourceLocation, RenderType> pipeline, #endif
            ResourceLocation sprite,
            int u, int v,
            int textureWidth, int textureHeight,
            int x, int y,
            int width, int height,
            @Nullable Integer color
    ) {
        blitSpriteAbstract(guiGraphics, color, () -> guiGraphics #if MC_VER >= V1_20_4 .blitSprite(#if MC_VER >= V1_21_3 pipeline, #endif sprite, textureWidth, textureHeight, u, v, x, y, width, height #if MC_VER >= V1_21_6 , (color != null) ? color : -1 #endif)); #else .blit(sprite, x, y, u, v, width, height, textureWidth, textureHeight)); #endif
    }
    //endregion

    //region blit

    //endregion
}
