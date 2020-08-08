package com.wxxtfxrmx.pirates.entity;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.wxxtfxrmx.pirates.component.TimeAccumulator;

public abstract class BaseActor extends Actor {

    protected final TimeAccumulator accumulator;
    private State currentState = State.IDLE;
    private boolean matched = false;

    public BaseActor(final TimeAccumulator accumulator) {
        this.accumulator = accumulator;
        setTouchable(Touchable.enabled);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (currentState == State.IDLE) {
            accumulator.drop();
        } else {
            accumulator.add(delta);
        }
    }

    public void changeState() {
        if (currentState == State.IDLE) currentState = State.PICKED;
        else currentState = State.IDLE;
    }

    public boolean isMatched() {
        return matched;
    }

    public void setMatched(boolean matched) {
        this.matched = matched;
    }

    protected enum State {
        IDLE,
        PICKED
    }
}
