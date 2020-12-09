package com.wxxtfxrmx.pirates.screen.levelv2.layer.board.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.wxxtfxrmx.pirates.screen.levelv2.component.BoundsComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.component.DestinationComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.board.component.ReadyToReuseComponent;

public class MoveTileToDestinationSystem extends IteratingSystem {

    private final static float VELOCITY = 8f;
    private final ComponentMapper<BoundsComponent> boundsMapper = ComponentMapper.getFor(BoundsComponent.class);
    private final ComponentMapper<DestinationComponent> destinationMapper = ComponentMapper.getFor(DestinationComponent.class);
    private final PooledEngine pooledEngine;

    public MoveTileToDestinationSystem(PooledEngine pooledEngine) {
        super(Family.all(BoundsComponent.class, DestinationComponent.class).get());
        this.pooledEngine = pooledEngine;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        BoundsComponent boundsComponent = boundsMapper.get(entity);
        DestinationComponent destinationComponent = destinationMapper.get(entity);

        Vector2 entityPosition = new Vector2();
        boundsComponent.bounds.getPosition(entityPosition);

        Vector2 destinationPosition = destinationComponent.destination;

        float actualDistance = entityPosition.dst(destinationPosition);

        if (actualDistance <= VELOCITY) {
            entity.add(pooledEngine.createComponent(ReadyToReuseComponent.class));
            entity.remove(DestinationComponent.class);
            return;
        }

        Vector2 direction = destinationPosition.cpy().sub(entityPosition).nor();
        direction.scl(VELOCITY);

        entityPosition.add(direction);
        boundsComponent.bounds.setPosition(entityPosition);
    }
}
