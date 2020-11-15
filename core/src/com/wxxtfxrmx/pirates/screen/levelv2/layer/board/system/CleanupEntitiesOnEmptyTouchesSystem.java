package com.wxxtfxrmx.pirates.screen.levelv2.layer.board.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.board.component.TilePickedComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.board.component.TouchChainComponent;

public class CleanupEntitiesOnEmptyTouchesSystem extends IteratingSystem {

    private final Family pickedTilesFamily = Family.all(TilePickedComponent.class).get();
    private final ComponentMapper<TouchChainComponent> chainMapper = ComponentMapper.getFor(TouchChainComponent.class);

    public CleanupEntitiesOnEmptyTouchesSystem() {
        super(Family.all(
                TouchChainComponent.class
        ).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TouchChainComponent touchChainComponent = chainMapper.get(entity);

        if (touchChainComponent.chain.size == 0) {
            for (Entity e : getEngine().getEntitiesFor(pickedTilesFamily)) {
                e.remove(TilePickedComponent.class);
            }
        }
    }
}
