package com.wxxtfxrmx.pirates.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.wxxtfxrmx.pirates.component.TimeAccumulator;

import java.util.Locale;

public final class Tile extends Actor {

    private final TimeAccumulator accumulator;
    private final Animation<TextureRegion> animation;
    private final TileType type;
    private TileState state = TileState.IDLE;
    private boolean matched = false;

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
        setPosition(getX(), getY(), Align.bottomLeft);
        setScale(getScaleX(), getScaleY());
        setSize(getWidth(), getHeight());
        batch.draw(animation.getKeyFrame(accumulator.getAccumulator()),
                getX(),
                getY(Align.bottomLeft),
                getOriginX() + getWidth() / 2,
                getOriginY() + getHeight() / 2,
                getWidth(),
                getHeight(),
                getScaleX(),
                getScaleY(),
                getRotation());
    }

    @Override
    protected void positionChanged() {
        super.positionChanged();
        Gdx.app.debug("TAG", String.format(Locale.ENGLISH, "POSITION CHANGED FOR %s x = %f y= %f", type, getX(), getY()));
    }

    public TileType getType() {
        return type;
    }

    public boolean isMatched() {
        return matched;
    }

    public void setMatched(boolean matched) {
        this.matched = matched;
    }

    public void updateState() {
        if (state == TileState.IDLE) state = TileState.PICKED;
        else state = TileState.IDLE;
    }
}
