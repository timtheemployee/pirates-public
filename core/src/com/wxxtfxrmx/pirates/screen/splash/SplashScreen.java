package com.wxxtfxrmx.pirates.screen.splash;

import com.wxxtfxrmx.pirates.navigation.Navigation;
import com.wxxtfxrmx.pirates.screen.BaseScreen;
import com.wxxtfxrmx.pirates.system.AnimationsSystem;

public class SplashScreen extends BaseScreen {

    private final AnimationsSystem animations;
    private final Navigation navigation;

    public SplashScreen(AnimationsSystem animations, Navigation navigation) {
        this.animations = animations;
        this.navigation = navigation;
    }

    @Override
    public void show() {
        super.show();
        animations.preload("coin_sheet.png", "bomb_sheet.png", "helm_sheet.png", "sample.png");
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if (animations.isLoaded()) {
            navigation.openLevelScreen();
        }
    }
}
