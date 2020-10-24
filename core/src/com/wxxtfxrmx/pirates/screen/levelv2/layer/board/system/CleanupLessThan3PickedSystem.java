package com.wxxtfxrmx.pirates.screen.levelv2.layer.board.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.wxxtfxrmx.pirates.screen.levelv2.component.TilePickedComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.component.TouchChainComponent;

public class CleanupLessThan3PickedSystem extends EntitySystem {

    private final Family chainFamily = Family.all(TouchChainComponent.class).get();
    private final Family pickedFamily = Family.all(TilePickedComponent.class).get();

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if (!canCleanup()) return;

        ImmutableArray<Entity> picked = getEngine().getEntitiesFor(pickedFamily);

        if (picked.size() < 3) {
            picked.forEach(entity -> entity.remove(TilePickedComponent.class));
        }
    }

    private boolean canCleanup() {
        return getEngine().getEntitiesFor(chainFamily).size() == 0;
    }
}
