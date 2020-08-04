package com.wxxtfxrmx.pirates.entity.sample;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.wxxtfxrmx.pirates.entity.ActorBuilder;

public final class SampleBuilder implements ActorBuilder {

    private final Animation<TextureRegion> animation;

    public SampleBuilder(Animation<TextureRegion> animation) {
        this.animation = animation;
    }

    @Override
    public Actor build() {
        return new SampleActor(
                animation
        );
    }
}
