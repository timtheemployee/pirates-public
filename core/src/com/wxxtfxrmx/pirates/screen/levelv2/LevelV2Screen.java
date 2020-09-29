package com.wxxtfxrmx.pirates.screen.levelv2;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.wxxtfxrmx.pirates.screen.levelv2.system.RenderingSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.world.BoardWorld;

public class LevelV2Screen implements Screen {

    private final PooledEngine engine;
    private final AssetManager assets;
    private final BoardWorld boardWorld;

    public LevelV2Screen(SpriteBatch batch) {
        engine = new PooledEngine();
        assets = new AssetManager();
        boardWorld = new BoardWorld(engine, 888L);
        engine.addSystem(new RenderingSystem(batch));
    }

    @Override
    public void show() {
        boardWorld.create();
    }

    @Override
    public void render(float delta) {
        float newDelta = normalize(delta);
        engine.update(newDelta);
    }

    private float normalize(float delta) {
        return Math.min(delta, 0.1f);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void resume() {
        updateSystems(true);
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void pause() {
        updateSystems(false);
    }

    private void updateSystems(Boolean processing) {
        for (EntitySystem system : engine.getSystems()) {
            system.setProcessing(processing);
        }
    }
}
