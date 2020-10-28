package com.wxxtfxrmx.pirates.screen.levelv2.layer.board.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.CollectedTilesComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.board.component.MarkToSendTileComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.board.component.TileTypeComponent;

public class SendCollectedTilesSystem extends EntitySystem {

    private final ComponentMapper<TileTypeComponent> typeMapper = ComponentMapper.getFor(TileTypeComponent.class);
    private final Family collectedTilesFamily = Family.all(MarkToSendTileComponent.class).get();
    private final PooledEngine engine;

    public SendCollectedTilesSystem(PooledEngine engine) {
        this.engine = engine;
    }

    @Override
    public void update(float deltaTime) {
        ImmutableArray<Entity> collectedEntities = getEngine().getEntitiesFor(collectedTilesFamily);

        if (collectedEntities.size() == 0) return;

        Entity collectedEntitiesEntity = engine.createEntity();
        CollectedTilesComponent collectedTilesComponent = engine.createComponent(CollectedTilesComponent.class);
        collectedTilesComponent.size = collectedEntities.size();
        collectedTilesComponent.type = typeMapper.get(collectedEntities.first()).type;
        collectedEntitiesEntity.add(collectedTilesComponent);

        engine.addEntity(collectedEntitiesEntity);

        collectedEntities.forEach((entity) -> entity.remove(MarkToSendTileComponent.class));
    }
}
