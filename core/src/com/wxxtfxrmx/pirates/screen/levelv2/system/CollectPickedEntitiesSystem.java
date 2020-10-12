package com.wxxtfxrmx.pirates.screen.levelv2.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IteratingSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.Constants;
import com.wxxtfxrmx.pirates.screen.levelv2.component.DestinationComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.component.TilePickedComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.component.TouchChainComponent;

public class CollectPickedEntitiesSystem extends IteratingSystem {
    private final PooledEngine pooledEngine;

    public CollectPickedEntitiesSystem(PooledEngine pooledEngine) {
        super(Family.all(TilePickedComponent.class).get());
        this.pooledEngine = pooledEngine;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    //TODO MOVE PICKED TILES TO FIGHT SYSTEM
    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        if (!canCollect()) return;

        DestinationComponent destinationComponent = pooledEngine.createComponent(DestinationComponent.class);
        destinationComponent.destination.x = Constants.WIDTH * Constants.UNIT;
        destinationComponent.destination.y = Constants.MIDDLE_ROUNDED_HEIGHT * Constants.UNIT;

        entity.add(destinationComponent);
        entity.remove(TilePickedComponent.class);
    }

    private boolean canCollect() {
        return getEngine().getEntitiesFor(Family.all(TouchChainComponent.class).get()).size() == 0;
    }
}
