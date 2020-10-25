package com.wxxtfxrmx.pirates.screen.levelv2;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.board.BoardLayer;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.ui.UiLayer;

import java.util.Random;

public class LevelV2Screen extends ScreenAdapter {

    private final PooledEngine engine;
    private final OrthographicCamera camera = new OrthographicCamera(
            Constants.WIDTH * Constants.UNIT,
            Constants.HEIGHT * Constants.UNIT
    );

    private final Viewport levelViewPort = new ExtendViewport(
            Constants.WIDTH * Constants.UNIT,
            Constants.HEIGHT * Constants.UNIT,
            camera);

    private final Stage stage = new Stage(levelViewPort);

    private final Layer board;
    private final Layer ui;

    public LevelV2Screen(SpriteBatch batch) {
        engine = new PooledEngine();
        camera.position.set(
                Constants.WIDTH * Constants.UNIT / 2f,
                Constants.HEIGHT * Constants.UNIT / 2f,
                0f
        );
        Random random = new Random(888L);
        board = new BoardLayer(engine, random, camera, batch);
        ui = new UiLayer(stage);
    }

    @Override
    public void show() {
        board.create();
        ui.create();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        float newDelta = normalize(delta);
        engine.update(newDelta);
        stage.act(newDelta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        levelViewPort.update(width, height);
    }

    private float normalize(float delta) {
        return Math.min(delta, 0.1f);
    }

    @Override
    public void resume() {
        updateSystems(true);
    }

    @Override
    public void pause() {
        updateSystems(false);
    }

    private void updateSystems(Boolean processing) {
        board.setEnabled(processing);
    }
}
