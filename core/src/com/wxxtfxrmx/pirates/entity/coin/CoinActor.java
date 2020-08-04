package com.wxxtfxrmx.pirates.entity.coin;


import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.wxxtfxrmx.pirates.component.TimeAccumulator;


public final class CoinActor extends Actor {

    private final Animation<TextureRegion> animation;
    private final TimeAccumulator accumulator;

    public CoinActor(final Animation<TextureRegion> animation, final TimeAccumulator accumulator) {
        this.animation = animation;
        this.accumulator = accumulator;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(animation.getKeyFrame(accumulator.getAccumulator()), getX(), getY());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        accumulator.add(delta);
    }
}
