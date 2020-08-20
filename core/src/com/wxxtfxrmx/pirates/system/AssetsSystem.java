package com.wxxtfxrmx.pirates.system;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.wxxtfxrmx.pirates.component.Size;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class AssetsSystem {

    private static final Size defaultSize = new Size(8, 1);
    private Map<String, Texture> cache = new HashMap<>();
    private Map<String, Animation<TextureRegion>> animationCache = new HashMap<>();

    private final AssetManager assets;

    public AssetsSystem(AssetManager assets) {
        this.assets = assets;
    }

    public Animation<TextureRegion> getAnimation(final String file) {
        return getAnimation(file, defaultSize);
    }

    public Animation<TextureRegion> getAnimation(final String file,
                                                 final Size spriteSheetSize) {

        if (animationCache.containsKey(file)) {
            return animationCache.get(file);
        }

        int width = spriteSheetSize.getWidth();
        int height = spriteSheetSize.getHeight();

        Texture texture = getOrCreate(file);
        TextureRegion[][] regions = TextureRegion.split(texture,
                texture.getWidth() / width,
                texture.getHeight() / height);

        TextureRegion[] flattenTextures = new TextureRegion[width * height];
        int currentTexture = 0;
        for (int column = 0; column < width; column++) {
            for (int row = 0; row < height; row++) {
                flattenTextures[currentTexture++] = regions[row][column];
            }
        }
        Animation<TextureRegion> animation = new Animation<>(0.300f, flattenTextures);
        animation.setPlayMode(Animation.PlayMode.LOOP);

        animationCache.put(file, animation);

        return animation;
    }

    public Texture getTexture(final String file) {
        return getOrCreate(file);
    }

    private Texture getOrCreate(final String file) {
        if (cache.containsKey(file)) {
            return cache.get(file);
        } else {
            Texture texture = assets.get(file, Texture.class);
            cache.put(file, texture);

            return texture;
        }
    }

    public void release() {
        for(Texture texture: cache.values()) {
            texture.dispose();
        }
    }


    public void preload(String... textures) {
        for (String texture: textures) {
            assets.load(texture, Texture.class);
        }

        assets.finishLoading();
    }

    public boolean isLoaded() {
        return assets.update();
    }
}
