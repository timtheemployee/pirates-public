package com.wxxtfxrmx.pirates.screen.levelv2.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.component.BoundsComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.component.EmptyPlaceComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.component.ReadyToReuseComponent;

public class SpawnUsedTileSystem extends IteratingSystem {

    private final ComponentMapper<EmptyPlaceComponent> emptyPlaceMapper = ComponentMapper.getFor(EmptyPlaceComponent.class);
    private final ComponentMapper<BoundsComponent> boundsMapper = ComponentMapper.getFor(BoundsComponent.class);

    public SpawnUsedTileSystem() {
        super(Family.all(
                ReadyToReuseComponent.class,
                EmptyPlaceComponent.class,
                BoundsComponent.class
        ).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        BoundsComponent boundsComponent = boundsMapper.get(entity);
        EmptyPlaceComponent emptyPlaceComponent = emptyPlaceMapper.get(entity);

        boundsComponent.bounds.x = emptyPlaceComponent.position.x;
        boundsComponent.bounds.y = emptyPlaceComponent.position.y;
        boundsComponent.z = 0f;

        entity.remove(EmptyPlaceComponent.class);
        entity.remove(ReadyToReuseComponent.class);
    }
}
