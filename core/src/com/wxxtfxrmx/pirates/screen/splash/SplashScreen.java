package com.wxxtfxrmx.pirates.screen.splash;

import com.wxxtfxrmx.pirates.navigation.Navigation;
import com.wxxtfxrmx.pirates.screen.BaseScreen;
import com.wxxtfxrmx.pirates.system.AssetsSystem;

public class SplashScreen extends BaseScreen {

    private final AssetsSystem animations;
    private final Navigation navigation;

    public SplashScreen(AssetsSystem animations, Navigation navigation) {
        this.animations = animations;
        this.navigation = navigation;
    }

    @Override
    public void show() {
        super.show();
        animations.preload("coin_sheet.png", "bomb_sheet.png", "helm_sheet.png", "sample.png", "back.png");
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if (animations.isLoaded()) {
            navigation.openLevelScreen();
        }
    }
}
