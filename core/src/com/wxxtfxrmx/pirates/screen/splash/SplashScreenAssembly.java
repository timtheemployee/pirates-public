package com.wxxtfxrmx.pirates.screen.splash;

import com.wxxtfxrmx.pirates.system.AnimationsSystem;

public final class SplashScreenAssembly {

    private final AnimationsSystem animations;

    public SplashScreenAssembly(AnimationsSystem animations) {
        this.animations = animations;
    }

    public AnimationsSystem animations() {
        return animations;
    }
}
