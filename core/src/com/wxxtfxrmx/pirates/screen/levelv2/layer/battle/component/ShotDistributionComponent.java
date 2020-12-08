package com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class ShotDistributionComponent implements Component {
    public int hit = 0;
    public int miss = 0;
    public Vector2 from = null;
    public Vector2 toBottom = null;
    public Vector2 toTop = null;
}
