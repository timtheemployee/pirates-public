package com.wxxtfxrmx.pirates;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.wxxtfxrmx.pirates.navigation.Destination;
import com.wxxtfxrmx.pirates.navigation.Navigator;
import com.wxxtfxrmx.pirates.screen.levelv2.LevelV2Screen;
import com.wxxtfxrmx.pirates.screen.start.StartScreen;

import static com.badlogic.gdx.Application.LOG_DEBUG;

public class Application extends Game implements Navigator {

    private SpriteBatch batch;

    @Override
    public void create() {
        Gdx.app.setLogLevel(LOG_DEBUG);
        batch = new SpriteBatch();
        open(Destination.START);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        getScreen().render(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void open(Destination destination) {
        switch (destination) {
            case LEVEL:
                setScreen(new LevelV2Screen(batch, this));
                break;

            case START:
                setScreen(new StartScreen(this));
        }
    }
}
