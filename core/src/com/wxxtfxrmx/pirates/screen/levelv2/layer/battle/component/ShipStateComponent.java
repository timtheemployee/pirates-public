package com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component;

import com.badlogic.ashley.core.Component;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.world.ShipState;

public class ShipStateComponent implements Component {
    public ShipState state = ShipState.IDLE_TOP;
}
