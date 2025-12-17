package com.cui.abs.core.rendering.gui;

import com.cui.abs.core.data.Rectangle;
import com.cui.abs.core.mixin.rendering.data.MinecraftInterfaceMixin;
import com.cui.abs.core.mixin.rendering.gui.GuiGraphicsAccessor;
import com.cui.abs.core.rendering.data.RenderPipelineBridge;
import com.cui.core.CUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
#if MC_VER >= V1_20_4
import net.minecraft.client.resources.metadata.gui.GuiSpriteScaling;
#endif
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

#if MC_VER >= V1_21_6
import com.mojang.blaze3d.pipeline.RenderPipeline;
#endif


#if MC_VER <= V1_21_5
import com.mojang.blaze3d.systems.RenderSystem;

import java.util.Currency;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.logging.Logger;
#endif

#if MC_VER >= V1_21_3
import net.minecraft.client.renderer.RenderType;

import java.util.Map;
import java.util.Objects;
#endif

public class GuiRenderer {
    //region Helper
    public record Dimensions(int width, int height) {}

    #if MC_VER >= V1_21_3
    private static final Map<String, #if MC_VER >= V1_21_6 RenderPipeline #elif MC_VER >= V1_21_3 Function<ResourceLocation, RenderType> #else Runnable #endif > ACTIONS = Map.of(
            "GUI_TEXTURED", RenderPipelineBridge.GUI_TEXTURED()
    );
    #endif

    #if MC_VER >= V1_20_4
    public static GuiSpriteScaling getSpriteScaling(GuiGraphics guiGraphics, ResourceLocation sprite) {
        TextureAtlasSprite textureAtlasSprite = ((GuiGraphicsAccessor) guiGraphics).getGuiSprites().getSprite(sprite);

        #if MC_VER >= V1_21_9
        return GuiGraphicsAccessor.getSpriteScaling(textureAtlasSprite);
        #else
        return ((GuiGraphicsAccessor) guiGraphics).getGuiSprites().getSpriteScaling(textureAtlasSprite);
        #endif
    }

    // Tile
    @Nullable
    public static GuiSpriteScaling.Tile getSpriteTile(GuiGraphics guiGraphics, ResourceLocation sprite) {
        TextureAtlasSprite textureAtlasSprite = ((GuiGraphicsAccessor) guiGraphics).getGuiSprites().getSprite(sprite);

        return getSpriteTile(#if MC_VER <= V1_21_8 guiGraphics, #endif textureAtlasSprite);
    }

    @Nullable
    public static GuiSpriteScaling.Tile getSpriteTile(#if MC_VER <= V1_21_8 GuiGraphics guiGraphics, #endif TextureAtlasSprite textureAtlasSprite) {
        GuiSpriteScaling spriteScaling = #if MC_VER >= V1_21_9 GuiGraphicsAccessor #else ((GuiGraphicsAccessor) guiGraphics).getGuiSprites() #endif .getSpriteScaling(textureAtlasSprite);

        Objects.requireNonNull(spriteScaling);

        return (spriteScaling.type() == GuiSpriteScaling.Type.TILE) ? (GuiSpriteScaling.Tile) spriteScaling : null;
    }

    /**
     * <h5> Sprite dimensions </h5>
     * Basically returns sprite image size
     * @since 1.20.4
     */
    protected static Dimensions getSpriteContentsDimensions(GuiGraphics guiGraphics, ResourceLocation sprite) {
        TextureAtlasSprite textureAtlasSprite = ((GuiGraphicsAccessor) guiGraphics).getGuiSprites().getSprite(sprite);

        return getSpriteContentsDimensions(#if MC_VER <= V1_21_8 guiGraphics, #endif textureAtlasSprite);
    }

    public static Dimensions getSpriteContentsDimensions(#if MC_VER <= V1_21_8 GuiGraphics guiGraphics, #endif TextureAtlasSprite textureAtlasSprite) {
        #if MC_VER >= V1_20_4 GuiSpriteScaling.Tile spriteTile = getSpriteTile(#if MC_VER <= V1_21_8 guiGraphics, #endif textureAtlasSprite); #endif

        return new Dimensions(
                #if MC_VER >= V1_20_4 (spriteTile != null) ? spriteTile.width() : #endif textureAtlasSprite.contents().width(),
                #if MC_VER >= V1_20_4 (spriteTile != null) ? spriteTile.height() : #endif textureAtlasSprite.contents().height()
        );
    }

    /**
     * <h5> Texture dimensions </h5>
     * Returns texture dimensions in atlas
     * @since 1.20.4
     */
    protected static Dimensions getTextureDimensions(GuiGraphics guiGraphics, ResourceLocation sprite, Dimensions spriteDimensions) {
        TextureAtlasSprite textureAtlasSprite = #if MC_VER >= V1_20_4 ((GuiGraphicsAccessor) guiGraphics).getGuiSprites().getSprite(sprite); #else ((MinecraftInterfaceMixin) Minecraft.getInstance()).getModelManager().getAtlas(new ResourceLocation("textures/atlas/decorated_pot.png")).getSprite(sprite); #endif

        return getTextureDimensions(#if MC_VER <= V1_21_8 guiGraphics, #endif textureAtlasSprite, spriteDimensions);
    }

    public static Dimensions getTextureDimensions(#if MC_VER <= V1_21_8 GuiGraphics guiGraphics, #endif TextureAtlasSprite textureAtlasSprite, Dimensions spriteDimensions) {
        Dimensions spriteContentsDimensions = getSpriteContentsDimensions(#if MC_VER <= V1_21_8 guiGraphics, #endif textureAtlasSprite);

        return new Dimensions(
                (int) (((float) spriteDimensions.width / spriteContentsDimensions.width) * spriteContentsDimensions.width + 0.9f),
                (int) (((float) spriteDimensions.height / spriteContentsDimensions.height) * spriteContentsDimensions.height + 0.9f)
        );
    }
    #endif
    //endregion


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
        #if MC_VER < V1_21_5
        RenderSystem.enableBlend();
        RenderSystem.depthMask(false);
        #endif

        RenderSystem.setShaderColor(red, green, blue, alpha);
        guiGraphics.flush();

        #if MC_VER < V1_21_5
        RenderSystem.disableBlend();
        RenderSystem.depthMask(true);
        #endif
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
        #if MC_VER < V1_21_5
        RenderSystem.enableBlend();
        RenderSystem.depthMask(false);
        #endif

        guiGraphics.flush();
        RenderSystem.setShaderColor(
                ((color >> 16) & 0xFF) / 255.0f,
                ((color >> 8) & 0xFF) / 255.0f,
                ((color) & 0xFF) / 255.0f,
                ((color >> 24) & 0xFF) / 255.0f
        );

        #if MC_VER < V1_21_5
        RenderSystem.disableBlend();
        RenderSystem.depthMask(true);
        #endif
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
        #if MC_VER < V1_21_5
        RenderSystem.enableBlend();
        RenderSystem.depthMask(false);
        #endif

        guiGraphics.flush();
        RenderSystem.setShaderColor(1, 1, 1, alpha);

        #if MC_VER < V1_21_5
        RenderSystem.disableBlend();
        RenderSystem.depthMask(true);
        #endif
    }

    /**
     * <h5> Clear shader color </h5>
     * ts pmo, get tf out!
     * <p>
     * Not to be used in versions after 1.21.5
     */
    public static void clearShaderColor(GuiGraphics guiGraphics) {
        #if MC_VER < V1_21_5
        RenderSystem.enableBlend();
        RenderSystem.depthMask(false);
        #endif

        guiGraphics.flush();
        RenderSystem.setShaderColor(1, 1, 1, 1);

        #if MC_VER < V1_21_5
        RenderSystem.disableBlend();
        RenderSystem.depthMask(true);
        #endif
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
     * <p>
     * Warning! do not call without any sizes in versions before 1.20.4
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
     * <p>
     * Warning! do not call without any sizes in versions before 1.20.4
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

            blitSprite(guiGraphics, #if MC_VER >= V1_21_3 ACTIONS.get(pipeline), #endif sprite, u, v, textureWidth, textureHeight, x, y, color);
        }

        int width = blitRectangle.dimensions.first;
        int height = blitRectangle.dimensions.second;

        if (textureRectangle.isPositionFilled()) {
            int u = textureRectangle.position.first;
            int v = textureRectangle.position.second;

            if (textureRectangle.isDimensionsFilled()) {
                int textureWidth = textureRectangle.dimensions.first;
                int textureHeight = textureRectangle.dimensions.second;

                blitSprite(guiGraphics, #if MC_VER >= V1_21_3 ACTIONS.get(pipeline), #endif sprite, u, v, textureWidth, textureHeight, x, y, width, height, color);
            } else {
                blitSprite(guiGraphics, #if MC_VER >= V1_21_3 ACTIONS.get(pipeline), #endif sprite, u, v, x, y, width, height, color);
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
     * <p>
     * Warning! do not call without any sizes in versions before 1.20.4
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
     * <p>
     * Warning! do not call without any sizes in versions before 1.20.4
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

            blitSprite(guiGraphics, #if MC_VER >= V1_21_3 ACTIONS.get(pipeline), #endif sprite, x, y, width, height, color);
        } else {
            blitSprite(guiGraphics, #if MC_VER >= V1_21_3 ACTIONS.get(pipeline), #endif sprite, x, y, color);
        }
    }
    //endregion


    //region blitSprite
    private static void blitSpriteAbstract(GuiGraphics guiGraphics, @Nullable Integer color, Runnable action) {
        #if MC_VER <= V1_21_5
        if (color != null) {
            setShaderColor(guiGraphics, color);

            action.run();

            clearShaderColor(guiGraphics);
        } else {
            action.run();
        }
        #else
        action.run();
        #endif
    }

    #if MC_VER >= V1_20_4 static boolean warned = false; #endif


    protected static void blitSprite(
            GuiGraphics guiGraphics,
            #if MC_VER >= V1_21_6 RenderPipeline pipeline, #elif MC_VER >= V1_21_3 Function<ResourceLocation, RenderType> pipeline, #endif
            ResourceLocation sprite,
            int x, int y,
            @Nullable Integer color
    ) {
        #if MC_VER >= V1_20_4 Dimensions spriteDimensions = getSpriteContentsDimensions(guiGraphics, sprite); #endif
        blitSpriteAbstract(
                guiGraphics, color,
                #if MC_VER >= V1_20_4
                () -> guiGraphics.blitSprite(#if MC_VER >= V1_21_3 pipeline, #endif sprite, x, y, spriteDimensions.width, spriteDimensions.height #if MC_VER >= V1_21_6 , (color != null) ? color : -1 #endif));
                #else
                () -> guiGraphics.blit(sprite, x, y, 0, 0, 16, 16));
        #endif
    }

    protected static void blitSprite(
            GuiGraphics guiGraphics,
            #if MC_VER >= V1_21_6 RenderPipeline pipeline, #elif MC_VER >= V1_21_3 Function<ResourceLocation, RenderType> pipeline, #endif
            ResourceLocation sprite,
            int x, int y,
            int width, int height,
            @Nullable Integer color
    ) {
        blitSpriteAbstract(
                guiGraphics, color,
                #if MC_VER >= V1_20_4
                () -> guiGraphics.blitSprite(#if MC_VER >= V1_21_3 pipeline, #endif sprite, x, y, width, height #if MC_VER >= V1_21_6 , (color != null) ? color : -1 #endif));
                #else
                () -> guiGraphics.blit(sprite, x, y, 0, 0, width, height, width, height)
        );
        #endif
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
        #if MC_VER >= V1_20_4 Dimensions textureDimensions = getTextureDimensions(guiGraphics, sprite, new Dimensions(width, height)); #endif
        blitSpriteAbstract(
                guiGraphics, color,
                #if MC_VER >= V1_20_4
                () -> guiGraphics.blitSprite(#if MC_VER >= V1_21_3 pipeline, #endif sprite, textureDimensions.width, textureDimensions.height, u, v, x, y, width, height #if MC_VER >= V1_21_6 , (color != null) ? color : -1 #endif));
                #else
                () -> guiGraphics.blit(sprite, x, y, u, v, width, height)
        );
        #endif
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
        blitSpriteAbstract(
                guiGraphics, color,
                #if MC_VER >= V1_20_4
                () -> guiGraphics.blitSprite(#if MC_VER >= V1_21_3 pipeline, #endif sprite, textureWidth, textureHeight, u, v, x, y, width, height #if MC_VER >= V1_21_6 , (color != null) ? color : -1 #endif));
                #else
                () -> guiGraphics.blit(sprite, x, y, u, v, width, height, 256, 256)
        );
        #endif
    }
    //endregion


    //region blit

    //endregion
}
