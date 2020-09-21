package com.wxxtfxrmx.pirates.screen.level.board;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.Align;
import com.wxxtfxrmx.pirates.component.TimeAccumulator;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.action;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public final class Tile extends Actor implements Comparable<Actor> {

    private final TimeAccumulator accumulator;
    private final Animation<TextureRegion> animation;
    private final TextureRegion pickedBorder;
    private final TileType type;
    private TileState state = TileState.IDLE;
    private boolean matched = false;

    public Tile(final Animation<TextureRegion> animation,
                final TextureRegion pickedBorder,
                final TimeAccumulator accumulator,
                final TileType type) {

        this.animation = animation;
        this.pickedBorder = pickedBorder;
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

        if (state == TileState.PICKED) {
            batch.draw(pickedBorder,
                    getX(),
                    getY(Align.bottomLeft),
                    getOriginX() + getWidth() / 2,
                    getOriginY() + getHeight() / 2,
                    getWidth(),
                    getHeight(),
                    getScaleX(),
                    getScaleY(),
                    getRotation()
            );
        }

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

    @Override
    public int compareTo(Actor actor) {
        return Integer.compare(getZIndex(), actor.getZIndex());
    }
}
