package com.wxxtfxrmx.pirates.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public abstract class BaseScreen implements Screen {

    private InputMultiplexer multiplexer = new InputMultiplexer();
    protected Stage scene;

    @Override
    public void show() {
        scene = new Stage(new StretchViewport(480, 800));
        multiplexer.addProcessor(scene);

        Gdx.input.setInputProcessor(multiplexer);
    }

    protected void addProcessor(InputProcessor processor) {
        multiplexer.addProcessor(processor);
    }

    private void removeProcessor(InputProcessor processor) {
        multiplexer.removeProcessor(processor);
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
