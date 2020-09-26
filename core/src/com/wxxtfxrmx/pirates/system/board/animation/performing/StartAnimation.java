package com.wxxtfxrmx.pirates.system.board.animation.performing;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;

public class StartAnimation extends Event {

    private final Actor target;

    public StartAnimation(Actor target) {
        this.target = target;
    }

    @Override
    public Actor getTarget() {
        return target;
    }
}
