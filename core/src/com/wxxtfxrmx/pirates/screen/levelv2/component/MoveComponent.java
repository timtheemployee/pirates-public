package com.wxxtfxrmx.pirates.screen.levelv2.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector3;

public class MoveComponent implements Component {
    public boolean isMoving = false;
    public Vector3 from = new Vector3();
    public Vector3 to = new Vector3();
}
