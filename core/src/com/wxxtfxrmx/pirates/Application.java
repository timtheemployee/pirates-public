package com.wxxtfxrmx.pirates;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.wxxtfxrmx.pirates.entity.factory.TileFactory;
import com.wxxtfxrmx.pirates.navigation.Navigation;
import com.wxxtfxrmx.pirates.screen.level.LevelScreenAssembly;
import com.wxxtfxrmx.pirates.screen.splash.SplashScreenAssembly;
import com.wxxtfxrmx.pirates.system.AnimationsSystem;

import static com.badlogic.gdx.Application.LOG_DEBUG;

public class Application extends Game {

    private final AnimationsSystem animationsSystem;
    private final TileFactory factory;
    private final SplashScreenAssembly splash;
    private final LevelScreenAssembly level;
    private final Navigation navigation;

    public Application() {
        final AssetManager assets = new AssetManager();
        animationsSystem = new AnimationsSystem(assets);
        factory = new TileFactory(animationsSystem);

        splash = new SplashScreenAssembly(animationsSystem);
        level = new LevelScreenAssembly(factory);
        navigation = new Navigation(this, level, splash);
    }

    @Override
    public void create() {
        Gdx.app.setLogLevel(LOG_DEBUG);
        navigation.openSplashScreen();
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        getScreen().render(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void dispose() {
        animationsSystem.release();
    }
}
