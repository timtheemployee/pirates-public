package com.wxxtfxrmx.pirates.entity;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

import com.wxxtfxrmx.pirates.component.TimeAccumulator;

public final class Tile extends Actor {

    private final TimeAccumulator accumulator;
    private final Animation<TextureRegion> animation;
    private final TileType type;
    private TileState state = TileState.IDLE;

    public Tile(final Animation<TextureRegion> animation,
                final TimeAccumulator accumulator,
                final TileType type) {

        this.animation = animation;
        this.accumulator = accumulator;
        this.type = type;
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (state == TileState.IDLE) {
            accumulator.drop();
        } else {
            accumulator.add(delta);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(animation.getKeyFrame(accumulator.getAccumulator()), getX(), getY());
    }

    public TileType getType() {
        return type;
    }

    public void updateState() {
        if (state == TileState.IDLE) state = TileState.PICKED;
        else state = TileState.IDLE;
    }
}
