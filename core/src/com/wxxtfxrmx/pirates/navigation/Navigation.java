package com.wxxtfxrmx.pirates.navigation;

import com.badlogic.gdx.Game;
import com.wxxtfxrmx.pirates.screen.level.LevelScreen;
import com.wxxtfxrmx.pirates.screen.level.LevelScreenAssembly;
import com.wxxtfxrmx.pirates.screen.splash.SplashScreen;
import com.wxxtfxrmx.pirates.screen.splash.SplashScreenAssembly;

public final class Navigation {

    private final Game game;
    private final LevelScreenAssembly level;
    private final SplashScreenAssembly splash;

    public Navigation(Game game, LevelScreenAssembly level, SplashScreenAssembly splash) {
        this.game = game;
        this.level = level;
        this.splash = splash;
    }

    public void openLevelScreen() {
        game.setScreen(new LevelScreen(
                level.tiles(),
                this
        ));
    }

    public void openSplashScreen() {
        game.setScreen(new SplashScreen(
                splash.animations(),
                this
        ));
    }
}
