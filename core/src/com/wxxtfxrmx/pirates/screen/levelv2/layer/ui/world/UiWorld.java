package com.wxxtfxrmx.pirates.screen.levelv2.layer.ui.world;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.ui.component.LevelContextComponent;

public class UiWorld {

    private final PooledEngine engine;

    public UiWorld(PooledEngine engine) {
        this.engine = engine;
    }

    public void create() {
        LevelContextComponent contextComponent = engine.createComponent(LevelContextComponent.class);
        Entity entity = engine.createEntity();
        entity.add(contextComponent);

        engine.addEntity(entity);
    }
}
