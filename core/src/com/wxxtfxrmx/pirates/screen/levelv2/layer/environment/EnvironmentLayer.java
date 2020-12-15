package com.wxxtfxrmx.pirates.screen.levelv2.layer.environment;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.wxxtfxrmx.pirates.screen.levelv2.Layer;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.board.system.rendering.EnvironmentRenderingSystem;

public class EnvironmentLayer implements Layer {

    private final EnvironmentRenderingSystem environmentRenderingSystem;

    public EnvironmentLayer(PooledEngine engine, ShapeRenderer shapeRenderer) {
        environmentRenderingSystem = new EnvironmentRenderingSystem(shapeRenderer);
        engine.addSystem(environmentRenderingSystem);
    }

    @Override
    public void create() {
        //TODO nothing
    }

    @Override
    public void setEnabled(boolean enabled) {
        environmentRenderingSystem.setProcessing(enabled);
    }
}
