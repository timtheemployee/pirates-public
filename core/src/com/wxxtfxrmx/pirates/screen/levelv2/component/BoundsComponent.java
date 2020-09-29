package com.wxxtfxrmx.pirates.screen.levelv2.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Rectangle;


public class BoundsComponent implements Component {
    public Rectangle bounds = new Rectangle();
    public float z = 0f;
}
