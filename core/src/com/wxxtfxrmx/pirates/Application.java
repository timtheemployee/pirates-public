package com.wxxtfxrmx.pirates;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.wxxtfxrmx.pirates.screen.levelv2.LevelV2Screen;

import static com.badlogic.gdx.Application.LOG_DEBUG;

public class Application extends Game {

    private SpriteBatch batch;

    @Override
    public void create() {
        Gdx.app.setLogLevel(LOG_DEBUG);
        batch = new SpriteBatch();
        setScreen(new LevelV2Screen(batch));
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        getScreen().render(Gdx.graphics.getDeltaTime());
    }
}
