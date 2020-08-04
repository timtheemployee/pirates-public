package com.wxxtfxrmx.pirates.entity.bomb;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.wxxtfxrmx.pirates.component.TimeAccumulator;
import com.wxxtfxrmx.pirates.entity.ActorBuilder;

public final class BombBuilder implements ActorBuilder {

    private final Animation<TextureRegion> animation;

    public BombBuilder(Animation<TextureRegion> animation) {
        this.animation = animation;
    }

    @Override
    public Actor build() {
        return new BombActor(
                animation,
                new TimeAccumulator()
        );
    }
}
