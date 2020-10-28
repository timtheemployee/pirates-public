package com.wxxtfxrmx.pirates.screen.levelv2.layer.board.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IteratingSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.Constants;
import com.wxxtfxrmx.pirates.screen.levelv2.component.BoundsComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.component.DestinationComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.board.component.MarkToSendTileComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.board.component.EmptyPlaceComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.board.component.TilePickedComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.board.component.TouchChainComponent;

public class MarkMatchedEntitiesSystem extends IteratingSystem {

    private final ComponentMapper<BoundsComponent> boundsMapper = ComponentMapper.getFor(BoundsComponent.class);
    private final PooledEngine pooledEngine;

    public MarkMatchedEntitiesSystem(PooledEngine pooledEngine) {
        super(Family.all(TilePickedComponent.class, BoundsComponent.class).get());
        this.pooledEngine = pooledEngine;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        if (!canCollect()) return;

        DestinationComponent destinationComponent = pooledEngine.createComponent(DestinationComponent.class);
        destinationComponent.destination.x = Constants.WIDTH * Constants.UNIT;
        destinationComponent.destination.y = Constants.MIDDLE_ROUNDED_HEIGHT * Constants.UNIT;

        BoundsComponent boundsComponent = boundsMapper.get(entity);
        boundsComponent.z = 1f;

        EmptyPlaceComponent emptyPlaceComponent = pooledEngine.createComponent(EmptyPlaceComponent.class);
        emptyPlaceComponent.position.x = boundsComponent.bounds.x;
        emptyPlaceComponent.position.y = boundsComponent.bounds.y;

        MarkToSendTileComponent collectedTileComponent = pooledEngine.createComponent(MarkToSendTileComponent.class);

        entity.add(emptyPlaceComponent);
        entity.add(destinationComponent);
        entity.remove(TilePickedComponent.class);
        entity.add(collectedTileComponent);
    }

    private boolean canCollect() {
        return getEngine().getEntitiesFor(Family.all(TouchChainComponent.class).get()).size() == 0;
    }
}
