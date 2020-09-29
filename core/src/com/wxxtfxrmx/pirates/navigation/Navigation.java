package com.wxxtfxrmx.pirates.navigation;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.wxxtfxrmx.pirates.screen.level.LevelScreen;
import com.wxxtfxrmx.pirates.screen.level.LevelScreenAssembly;
import com.wxxtfxrmx.pirates.screen.levelv2.LevelV2Screen;
import com.wxxtfxrmx.pirates.screen.sample.SampleScreen;
import com.wxxtfxrmx.pirates.screen.splash.SplashScreen;
import com.wxxtfxrmx.pirates.screen.splash.SplashScreenAssembly;

public final class Navigation {

    private final Game game;
    private final LevelScreenAssembly level;
    private final SplashScreenAssembly splash;
    private final SpriteBatch batch;

    public Navigation(Game game,
                      LevelScreenAssembly level,
                      SplashScreenAssembly splash,
                      SpriteBatch batch) {
        this.game = game;
        this.level = level;
        this.splash = splash;
        this.batch = batch;
    }

    public void openLevelScreen() {
        game.setScreen(new LevelV2Screen(batch));
    }

    public void openSplashScreen() {
        game.setScreen(new SplashScreen(
                splash.animations(),
                this
        ));
    }

    public void openSampleScreen() {
        game.setScreen(new SampleScreen());
    }
}
