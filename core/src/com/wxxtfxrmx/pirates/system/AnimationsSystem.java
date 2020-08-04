package com.wxxtfxrmx.pirates.system;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.wxxtfxrmx.pirates.component.Size;

import java.util.HashMap;
import java.util.Map;

public final class AnimationsSystem {

    private static final Size defaultSize = new Size(8, 1);
    private Map<String, Texture> cache = new HashMap<>();

    public Animation<TextureRegion> getAnimation(final String file) {
        return getAnimation(file, defaultSize);
    }

    public Animation<TextureRegion> getAnimation(final String file,
                                                 final Size spriteSheetSize) {
        final int width = spriteSheetSize.getWidth();
        final int height = spriteSheetSize.getHeight();

        final Texture texture = getOrCreate(file);
        final TextureRegion[][] regions = TextureRegion.split(texture,
                texture.getWidth() / width,
                texture.getHeight() / height);

        final TextureRegion[] flattenTextures = new TextureRegion[width * height];
        int currentTexture = 0;
        for (int column = 0; column < width; column++) {
            for (int row = 0; row < height; row++) {
                flattenTextures[currentTexture++] = regions[row][column];
            }
        }
        final Animation<TextureRegion> animation = new Animation<>(0.300f, flattenTextures);
        animation.setPlayMode(Animation.PlayMode.LOOP);

        return animation;
    }

    private Texture getOrCreate(final String file) {
        if (cache.containsKey(file)) {
            return cache.get(file);
        } else {
            final Texture texture = new Texture(file);
            cache.put(file, texture);

            return texture;
        }
    }

    public void release() {
        for(Texture texture: cache.values()) {
            texture.dispose();
        }
    }
}
