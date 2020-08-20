package com.wxxtfxrmx.pirates.screen.splash;

import com.wxxtfxrmx.pirates.system.AssetsSystem;

public final class SplashScreenAssembly {

    private final AssetsSystem animations;

    public SplashScreenAssembly(AssetsSystem animations) {
        this.animations = animations;
    }

    public AssetsSystem animations() {
        return animations;
    }
}
