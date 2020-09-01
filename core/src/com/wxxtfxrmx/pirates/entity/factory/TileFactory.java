package com.wxxtfxrmx.pirates.entity.factory;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.wxxtfxrmx.pirates.component.Size;
import com.wxxtfxrmx.pirates.component.TimeAccumulator;
import com.wxxtfxrmx.pirates.entity.Tile;
import com.wxxtfxrmx.pirates.entity.TileType;
import com.wxxtfxrmx.pirates.system.AssetsSystem;

import java.util.Locale;

public class TileFactory {

    private final AssetsSystem assetsSystem;

    public TileFactory(AssetsSystem assetsSystem) {
        this.assetsSystem = assetsSystem;
    }

    public Tile of(TileType type) {
        return new Tile(
                animationFor(type),
                assetsSystem.getTextureRegion("picked_border.png"),
                new TimeAccumulator(), type
        );
    }

    private Animation<TextureRegion> animationFor(TileType type) {
        switch (type) {
            case COIN:
                return assetsSystem.getAnimation("coin_sheet.png", new Size(9, 1));

            case BOMB:
                return assetsSystem.getAnimation("bomb_sheet.png", new Size(7, 1));

            case HELM:
                return assetsSystem.getAnimation("helm_sheet.png", new Size(9, 1));

            case SAMPLE:
                return assetsSystem.getAnimation("sample.png", new Size(1, 1));

            case REPAIR:
                return assetsSystem.getAnimation("repair_sheet.png", new Size(11, 1));
        }

        throw new IllegalArgumentException(String.format(Locale.ENGLISH, "Unsupported Tile type %s", type.toString()));
    }
}
