package com.wxxtfxrmx.pirates.screen.levelv2.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationComponent implements Component {
    public Animation<TextureRegion> animation = new Animation<TextureRegion>(0.2f);
    public float frameDelta = 0f;
}
