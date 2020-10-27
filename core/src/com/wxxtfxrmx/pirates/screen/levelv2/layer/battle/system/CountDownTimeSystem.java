package com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.RemainedTimeComponent;

public class CountDownTimeSystem extends IteratingSystem {

    private final ComponentMapper<RemainedTimeComponent> remainedTimeMapper = ComponentMapper.getFor(RemainedTimeComponent.class);
    private float accumulator = 0f;

    public CountDownTimeSystem() {
        super(Family.all(RemainedTimeComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        RemainedTimeComponent remainedTimeComponent = remainedTimeMapper.get(entity);

        if (remainedTimeComponent.remainedTime == 0) {
            remainedTimeComponent.remainedTime = 10;
        }

        if (accumulator >= 1f) {
            remainedTimeComponent.remainedTime -= 1;
            accumulator = 0f;
        }

        accumulator += deltaTime;
    }
}
