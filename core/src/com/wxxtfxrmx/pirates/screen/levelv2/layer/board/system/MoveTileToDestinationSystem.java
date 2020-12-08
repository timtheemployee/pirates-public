package com.wxxtfxrmx.pirates.screen.levelv2.layer.board.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IteratingSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.component.BoundsComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.component.DestinationComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.board.component.ReadyToReuseComponent;

//TODO Make it more universal
@Deprecated
public class MoveTileToDestinationSystem extends IteratingSystem {

    private final static float VELOCITY = 8f;
    private final ComponentMapper<BoundsComponent> boundsMapper = ComponentMapper.getFor(BoundsComponent.class);
    private final ComponentMapper<DestinationComponent> destinationMapper = ComponentMapper.getFor(DestinationComponent.class);
    private final PooledEngine pooledEngine;

    public MoveTileToDestinationSystem(PooledEngine pooledEngine) {
        super(Family.all(BoundsComponent.class, DestinationComponent.class).get());
        this.pooledEngine = pooledEngine;
    }

    //TODO REMOVING TILE FROM SCREEN
    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        BoundsComponent boundsComponent = boundsMapper.get(entity);
        DestinationComponent destinationComponent = destinationMapper.get(entity);

        if (boundsComponent.bounds.x >= destinationComponent.destination.x &&
                boundsComponent.bounds.y >= destinationComponent.destination.y) {

            //TODO Remove it in another system
            entity.add(pooledEngine.createComponent(ReadyToReuseComponent.class));
            entity.remove(DestinationComponent.class);
            return;
        }

        //TODO Use linear algebra instead of manual coordinates calculation
        float differenceX = destinationComponent.destination.x - boundsComponent.bounds.x;
        float signX = Math.signum(differenceX);
        boundsComponent.bounds.x += signX * VELOCITY;

        float differenceY = destinationComponent.destination.y - boundsComponent.bounds.y;
        float signY = Math.signum(differenceY);
        boundsComponent.bounds.y += signY * VELOCITY;
    }
}
