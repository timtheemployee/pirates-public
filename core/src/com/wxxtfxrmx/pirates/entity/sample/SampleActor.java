package com.wxxtfxrmx.pirates.entity.sample;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.wxxtfxrmx.pirates.component.TimeAccumulator;
import com.wxxtfxrmx.pirates.entity.BaseActor;

// SAMPLE ACTOR
public final class SampleActor extends BaseActor {
    private final Animation<TextureRegion> animation;

    public SampleActor(Animation<TextureRegion> animation, TimeAccumulator accumulator) {
        super(accumulator);
        this.animation = animation;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(animation.getKeyFrame(accumulator.getAccumulator()), getX(), getY());
    }
}
