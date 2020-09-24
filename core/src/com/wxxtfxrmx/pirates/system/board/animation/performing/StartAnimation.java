package com.wxxtfxrmx.pirates.system.board.animation.performing;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.wxxtfxrmx.pirates.system.board.event.BoardEvent;

public class StartAnimation extends BoardEvent {

    private final Actor target;

    public StartAnimation(Actor target) {
        this.target = target;
    }

    @Override
    public Actor getTarget() {
        return target;
    }
}
