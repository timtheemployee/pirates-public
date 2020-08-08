package com.wxxtfxrmx.pirates.entity.coin;


import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.wxxtfxrmx.pirates.component.TimeAccumulator;
import com.wxxtfxrmx.pirates.entity.BaseActor;


public final class CoinActor extends BaseActor {

    private final Animation<TextureRegion> animation;

    public CoinActor(final Animation<TextureRegion> animation, final TimeAccumulator accumulator) {
        super(accumulator);
        this.animation = animation;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(animation.getKeyFrame(accumulator.getAccumulator()), getX(), getY());
    }
}
