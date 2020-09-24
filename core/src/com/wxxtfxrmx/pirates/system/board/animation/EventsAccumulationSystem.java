package com.wxxtfxrmx.pirates.system.board.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.wxxtfxrmx.pirates.system.board.System;
import com.wxxtfxrmx.pirates.system.board.animation.performing.CompleteAnimation;
import com.wxxtfxrmx.pirates.system.board.animation.performing.StartAnimation;
import com.wxxtfxrmx.pirates.system.board.event.BoardEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

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
            Gdx.app.error("PERFORMING TILES", String.format(Locale.ENGLISH, "FIRE EVENTS WITH SIZE %d", accumulatedEventsPool.size()));
            List<Event> accumulatedEventsPoolCopy = new ArrayList<>(accumulatedEventsPool);
            for (Event event : accumulatedEventsPoolCopy) {
                parent.fire(event);
            }

            accumulatedEventsPool.removeAll(accumulatedEventsPoolCopy);
        }
    }

    //TODO MAKE NON-BLOCKING EVENTS
    // HANDLE EVENTS WHICH ALREADY BEEN IN POOL
    @Override
    public boolean handle(Event event) {
        Gdx.app.error("PERFORMING TILES", String.format(Locale.ENGLISH, "POOL SIZE IS, %d", performingTiles.size()));
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

        if (event instanceof BoardEvent) {
            accumulatedEventsPool.add(event);
            return true;
        }

        return false;
    }
}
