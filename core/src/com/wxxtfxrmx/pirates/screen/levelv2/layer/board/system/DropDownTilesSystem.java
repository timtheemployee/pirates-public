package com.wxxtfxrmx.pirates.screen.levelv2.layer.board.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.wxxtfxrmx.pirates.screen.levelv2.Constants;
import com.wxxtfxrmx.pirates.screen.levelv2.component.BoundsComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.board.component.EmptyPlaceComponent;

public class DropDownTilesSystem extends IteratingSystem {

    private final ComponentMapper<EmptyPlaceComponent> emptyPlaceMapper = ComponentMapper.getFor(EmptyPlaceComponent.class);
    private final ComponentMapper<BoundsComponent> boundsMapper = ComponentMapper.getFor(BoundsComponent.class);

    private final Family emptyPlaceFamily = Family.all(EmptyPlaceComponent.class).get();

    public DropDownTilesSystem() {
        super(Family
                .all(BoundsComponent.class)
                .exclude(EmptyPlaceComponent.class)
                .get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        if (emptyPlaceMapper.has(entity)) return;

        ImmutableArray<Entity> emptyPlaceEntities = getEngine().getEntitiesFor(emptyPlaceFamily);
        if (emptyPlaceEntities.size() == 0) return;

        BoundsComponent boundsComponent = boundsMapper.get(entity);
        for (Entity emptyPlaceEntity : emptyPlaceEntities) {
            EmptyPlaceComponent placeComponent = emptyPlaceMapper.get(emptyPlaceEntity);

            if (boundsComponent.bounds.y - placeComponent.position.y == Constants.UNIT
                    && boundsComponent.bounds.x == placeComponent.position.x) {
                dropDown(boundsComponent, placeComponent);
            }
        }
    }

    private void dropDown(BoundsComponent boundsComponent, EmptyPlaceComponent emptyPlaceComponent) {
        float emptyX = emptyPlaceComponent.position.x;
        float emptyY = emptyPlaceComponent.position.y;

        emptyPlaceComponent.position.x = boundsComponent.bounds.x;
        emptyPlaceComponent.position.y = boundsComponent.bounds.y;

        boundsComponent.bounds.x = emptyX;
        boundsComponent.bounds.y = emptyY;
    }
}
