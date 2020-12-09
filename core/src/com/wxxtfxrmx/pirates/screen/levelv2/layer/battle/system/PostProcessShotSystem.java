package com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.board.component.ReadyToReuseComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.board.component.TileTypeComponent;

public class PostProcessShotSystem extends IteratingSystem {

    public PostProcessShotSystem() {
        super(Family.all(ReadyToReuseComponent.class).exclude(TileTypeComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        entity.remove(ReadyToReuseComponent.class);
        getEngine().removeEntity(entity);
    }
}
