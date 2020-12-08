package com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class CannonBallComponent implements Component {
    public Vector2 startPoint = null;
    public Vector2 currentPoint = null;
    public Vector2 hitPoint = null;
    public Vector2 velocity = null;
}
