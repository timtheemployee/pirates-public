package com.wxxtfxrmx.pirates.entity;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.Align;
import com.wxxtfxrmx.pirates.component.TimeAccumulator;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.action;

public final class Tile extends Actor implements Comparable<Actor> {

    private final TimeAccumulator accumulator;
    private final Animation<TextureRegion> animation;
    private final TextureRegion pickedBorder;
    private final TileType type;
    private TileState state = TileState.IDLE;
    private boolean matched = false;
    private boolean isChanged = false;

    public Tile(final Animation<TextureRegion> animation,
                final TextureRegion pickedBorder,
                final TimeAccumulator accumulator,
                final TileType type) {

        this.animation = animation;
        this.pickedBorder = pickedBorder;
        this.accumulator = accumulator;
        this.type = type;
        toBack();
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

    public boolean isChanged() {
        boolean tmp = isChanged;
        isChanged = false;
        return tmp;
    }

    public void setMatched(boolean matched) {
        this.matched = matched;
    }

    public void updateState() {
        if (state == TileState.IDLE) state = TileState.PICKED;
        else state = TileState.IDLE;
    }

    //afterMatch might be null
    public void onMatch(Runnable afterMatch) {
        SequenceAction actionsSequence = action(SequenceAction.class);

        Group parent = getParent();

        if (parent != null) {
            MoveToAction topRightCorner = action(MoveToAction.class);
            topRightCorner.setPosition(getParent().getX(Align.right), getParent().getY(Align.top));
            topRightCorner.setDuration(1.5f);
            actionsSequence.addAction(topRightCorner);
        }

        RunnableAction tileRemoveAction = action(RunnableAction.class);
        tileRemoveAction.setRunnable(this::remove);
        actionsSequence.addAction(tileRemoveAction);

        if (afterMatch != null) {
            RunnableAction afterMatchAction = action(RunnableAction.class);
            afterMatchAction.setRunnable(afterMatch);
            actionsSequence.addAction(afterMatchAction);
        }

        addAction(actionsSequence);
    }

    public void onCreate() {
        SequenceAction actionSequence = action(SequenceAction.class);

        ScaleToAction tileScale = action(ScaleToAction.class);
        tileScale.setScale(1, 1);
        tileScale.setDuration(0.3f);
        actionSequence.addAction(tileScale);

        RunnableAction afterCreatingAction = action(RunnableAction.class);
        afterCreatingAction.setRunnable(this::clearActions);
        actionSequence.addAction(afterCreatingAction);

        addAction(actionSequence);
    }

    @Override
    protected void positionChanged() {
        super.positionChanged();
        isChanged = true;
        toFront();
    }

    @Override
    protected void sizeChanged() {
        super.sizeChanged();
        isChanged = true;
        toFront();
    }

    @Override
    protected void rotationChanged() {
        super.rotationChanged();
        isChanged = true;
        toFront();
    }

    @Override
    public int compareTo(Actor actor) {
        return Integer.compare(getZIndex(), actor.getZIndex());
    }
}
