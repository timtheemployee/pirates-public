package com.wxxtfxrmx.pirates.screen.levelv2;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.wxxtfxrmx.pirates.navigation.Navigator;
import com.wxxtfxrmx.pirates.screen.BaseScreen;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.BattleLayer;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.board.BoardLayer;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.ui.UiLayer;

import java.util.Random;

public class LevelV2Screen extends BaseScreen {

    private final PooledEngine engine;
    private final Layer board;
    private final Layer ui;
    private final Layer battle;

    public LevelV2Screen(SpriteBatch batch, Navigator navigator) {
        super(navigator);
        engine = new PooledEngine();
        camera.position.set(
                Constants.WIDTH * Constants.UNIT / 2f,
                Constants.HEIGHT * Constants.UNIT / 2f,
                0f
        );
        Random random = new Random(888L);
        board = new BoardLayer(engine, random, camera, batch);
        ui = new UiLayer(stage, engine);
        battle = new BattleLayer(engine);
    }

    @Override
    public void show() {
        super.show();
        board.create();
        ui.create();
        battle.create();
    }

    @Override
    public void render(float delta) {
        float newDelta = normalize(delta);
        engine.update(newDelta);
        super.render(delta);
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
        ui.setEnabled(processing);
        battle.setEnabled(processing);
    }
}
