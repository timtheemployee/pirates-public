package com.wxxtfxrmx.pirates;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.wxxtfxrmx.pirates.entity.factory.TextureFactory;
import com.wxxtfxrmx.pirates.entity.factory.TileFactory;
import com.wxxtfxrmx.pirates.navigation.Navigation;
import com.wxxtfxrmx.pirates.screen.level.LevelScreenAssembly;
import com.wxxtfxrmx.pirates.screen.splash.SplashScreenAssembly;
import com.wxxtfxrmx.pirates.system.AssetsSystem;

import static com.badlogic.gdx.Application.LOG_DEBUG;

public class Application extends Game {

    private final AssetsSystem assetsSystem;
    private final TileFactory factory;
    private final TextureFactory images;
    private final SplashScreenAssembly splash;
    private final LevelScreenAssembly level;
    private SpriteBatch batch;
    private Navigation navigation;

    public Application() {
        final AssetManager assets = new AssetManager();
        assetsSystem = new AssetsSystem(assets);
        factory = new TileFactory(assetsSystem);
        images = new TextureFactory(assetsSystem);

        splash = new SplashScreenAssembly(assetsSystem);
        level = new LevelScreenAssembly(factory, images);
    }

    @Override
    public void create() {
        Gdx.app.setLogLevel(LOG_DEBUG);
        batch = new SpriteBatch();
        navigation = new Navigation(this, level, splash, batch);
        navigation.openSplashScreen();
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        getScreen().render(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void dispose() {
        assetsSystem.release();
    }
}
