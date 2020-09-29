package com.wxxtfxrmx.pirates.system.board.animation;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.wxxtfxrmx.pirates.system.System;
import com.wxxtfxrmx.pirates.system.board.animation.performing.CompleteAnimation;
import com.wxxtfxrmx.pirates.system.board.animation.performing.StartAnimation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EventsAccumulationSystem implements System {

    private List<Actor> performingTiles = new ArrayList<>();
    private List<Event> accumulatedEventsPool = new ArrayList<>();
    private final Group parent;

    public EventsAccumulationSystem(Group parent) {
        this.parent = parent;
    }

    public void update() {
        performingTiles.removeAll(Collections.singleton(null));
        if (performingTiles.isEmpty()) {
            List<Event> accumulatedEventsPoolCopy = new ArrayList<>(accumulatedEventsPool);
            for (Event event : accumulatedEventsPoolCopy) {
                event.handle();
                parent.fire(event);
                accumulatedEventsPool.remove(event);
            }
        }
    }

    @Override
    public boolean handle(Event event) {
        if (event.isHandled()) return false;

        if (event instanceof StartAnimation) {
            StartAnimation startAnimation = (StartAnimation) event;
            performingTiles.add(startAnimation.getTarget());
            return true;
        }

        if (event instanceof CompleteAnimation) {
            CompleteAnimation animationCompleted = (CompleteAnimation) event;
            performingTiles.remove(animationCompleted.getTarget());
            return true;
        }

        accumulatedEventsPool.add(event);
        return true;
    }
}
