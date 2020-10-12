package com.wxxtfxrmx.pirates.screen.levelv2.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.component.BoundsComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.component.DestinationComponent;

public class MoveEntityToDestinationSystem extends IteratingSystem {

    private final static float VELOCITY = 12f;
    private final ComponentMapper<BoundsComponent> boundsMapper = ComponentMapper.getFor(BoundsComponent.class);
    private final ComponentMapper<DestinationComponent> destinationMapper = ComponentMapper.getFor(DestinationComponent.class);

    public MoveEntityToDestinationSystem() {
        super(Family.all(BoundsComponent.class, DestinationComponent.class).get());
    }

    //TODO REMOVING TILE FROM SCREEN
    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        BoundsComponent boundsComponent = boundsMapper.get(entity);
        DestinationComponent destinationComponent = destinationMapper.get(entity);

        if (boundsComponent.bounds.x == destinationComponent.destination.x &&
                boundsComponent.bounds.y == destinationComponent.destination.y) {
            entity.remove(DestinationComponent.class);
            return;
        }

        float differenceX = destinationComponent.destination.x - boundsComponent.bounds.x;
        float signX = Math.signum(differenceX);
        boundsComponent.bounds.x += signX * VELOCITY;

        float differenceY = destinationComponent.destination.y - boundsComponent.bounds.y;
        float signY = Math.signum(differenceY);
        boundsComponent.bounds.y += signY * VELOCITY;
    }
}
