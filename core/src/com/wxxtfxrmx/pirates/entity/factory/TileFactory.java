package com.wxxtfxrmx.pirates.entity.factory;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.wxxtfxrmx.pirates.component.Size;
import com.wxxtfxrmx.pirates.component.TimeAccumulator;
import com.wxxtfxrmx.pirates.entity.Tile;
import com.wxxtfxrmx.pirates.entity.TileType;
import com.wxxtfxrmx.pirates.system.AnimationsSystem;

import java.util.Locale;

public class TileFactory {

    private final AnimationsSystem animationsSystem;

    public TileFactory(AnimationsSystem animationsSystem) {
        this.animationsSystem = animationsSystem;
    }

    public Tile of(TileType type) {
        return new Tile(animationFor(type), new TimeAccumulator(), type);
    }

    private Animation<TextureRegion> animationFor(TileType type) {
        switch (type) {
            case COIN:
                return animationsSystem.getAnimation("coin_sheet.png");

            case BOMB:
                return animationsSystem.getAnimation("bomb_sheet.png", new Size(9, 1));

            case HELM:
                return animationsSystem.getAnimation("helm_sheet.png", new Size(3, 1));

            case SAMPLE:
                return animationsSystem.getAnimation("sample.png", new Size(1, 1));
        }

        throw new IllegalArgumentException(String.format(Locale.ENGLISH, "Unsupported Tile type %s", type.toString()));
    }
}
