package com.wxxtfxrmx.pirates.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.wxxtfxrmx.pirates.navigation.Navigator;
import com.wxxtfxrmx.pirates.screen.levelv2.Constants;

public abstract class BaseScreen implements Screen {

    protected final OrthographicCamera camera = new OrthographicCamera(
            Constants.WIDTH * Constants.UNIT,
            Constants.HEIGHT * Constants.UNIT
    );

    protected final Viewport viewport = new ScalingViewport(
            Scaling.stretch,
            Constants.WIDTH * Constants.UNIT,
            Constants.HEIGHT * Constants.UNIT,
            camera);

    protected final Stage stage = new Stage(viewport);

    protected final Navigator navigator;

    protected BaseScreen(Navigator navigator) {
        this.navigator = navigator;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        stage.dispose();
        Gdx.input.setInputProcessor(null);
    }
}
