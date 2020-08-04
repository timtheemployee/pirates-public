package com.wxxtfxrmx.pirates.entity.coin;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.wxxtfxrmx.pirates.component.TimeAccumulator;
import com.wxxtfxrmx.pirates.entity.ActorBuilder;

public final class CoinBuilder implements ActorBuilder {

    private final Animation<TextureRegion> animation;

    public CoinBuilder(final Animation<TextureRegion> animation) {
        this.animation = animation;
    }

    @Override
    public Actor build() {
        return new CoinActor(
                animation,
                new TimeAccumulator()
        );
    }
}
