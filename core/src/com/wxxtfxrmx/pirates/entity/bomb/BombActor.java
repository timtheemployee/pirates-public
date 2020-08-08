package com.wxxtfxrmx.pirates.entity.bomb;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.wxxtfxrmx.pirates.component.TimeAccumulator;
import com.wxxtfxrmx.pirates.entity.BaseActor;

public final class BombActor extends BaseActor {
    private final Animation<TextureRegion> animation;

    public BombActor(Animation<TextureRegion> animation, TimeAccumulator accumulator) {
        super(accumulator);
        this.animation = animation;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(animation.getKeyFrame(accumulator.getAccumulator()), getX(), getY());
    }
}
