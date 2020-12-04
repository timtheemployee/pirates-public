package com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component;

import com.badlogic.ashley.core.Component;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.world.ShipPart;

import java.util.ArrayList;
import java.util.List;

public class ShipPartComponent implements Component {
    public List<ShipPart> parts = new ArrayList<ShipPart>();
}
