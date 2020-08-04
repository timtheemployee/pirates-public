package com.wxxtfxrmx.pirates.entity.helm;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.wxxtfxrmx.pirates.component.TimeAccumulator;
import com.wxxtfxrmx.pirates.entity.ActorBuilder;

public class HelmBuilder implements ActorBuilder {

    private final Animation<TextureRegion> animation;

    public HelmBuilder(Animation<TextureRegion> animation) {
        this.animation = animation;
    }

    @Override
    public Actor build() {
        return new HelmActor(
                animation,
                new TimeAccumulator()
        );
    }
}
