package com.wxxtfxrmx.pirates.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public abstract class BaseScreen implements Screen {

    protected Stage scene;

    @Override
    public void show() {
        scene = new Stage(new StretchViewport(480, 800));
        Gdx.input.setInputProcessor(scene);
    }

    @Override
    public void render(float delta) {
        scene.act(delta);
        scene.draw();
    }

    @Override
    public void resize(int width, int height) {
        scene.getViewport().update(width, height);
    }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

    @Override
    public void dispose() {
        scene.dispose();
        Gdx.input.setInputProcessor(null);
    }
}
