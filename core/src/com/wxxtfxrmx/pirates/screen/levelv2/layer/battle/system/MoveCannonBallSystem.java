package com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.CannonBallComponent;

public class MoveCannonBallSystem extends IteratingSystem {

    private final ComponentMapper<CannonBallComponent> cannonBallMapper = ComponentMapper.getFor(CannonBallComponent.class);

    public MoveCannonBallSystem() {
        super(Family.all(CannonBallComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        CannonBallComponent cannonBallComponent = cannonBallMapper.get(entity);

        //FIXME: CHECK IF cannon ball arrived to destination, then remove it, else add some velocity
    }
}
