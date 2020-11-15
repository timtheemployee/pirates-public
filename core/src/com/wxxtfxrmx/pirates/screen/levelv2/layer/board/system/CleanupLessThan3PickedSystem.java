package com.wxxtfxrmx.pirates.screen.levelv2.layer.board.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.board.component.TilePickedComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.board.component.TouchChainComponent;

public class CleanupLessThan3PickedSystem extends EntitySystem {

    private final Family chainFamily = Family.all(TouchChainComponent.class).get();
    private final Family pickedFamily = Family.all(TilePickedComponent.class).get();

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if (!canCleanup()) return;

        ImmutableArray<Entity> picked = getEngine().getEntitiesFor(pickedFamily);

        if (picked.size() < 3) {
            for (Entity entity: picked) {
                entity.remove(TilePickedComponent.class);
            }
        }
    }

    private boolean canCleanup() {
        return getEngine().getEntitiesFor(chainFamily).size() == 0;
    }
}
