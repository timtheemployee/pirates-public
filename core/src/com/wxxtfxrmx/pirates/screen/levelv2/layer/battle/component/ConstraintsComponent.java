package com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component;

import com.badlogic.ashley.core.Component;
import com.wxxtfxrmx.pirates.uikit.utils.Constraint;
import com.wxxtfxrmx.pirates.uikit.utils.Reference;

import java.util.List;

public class ConstraintsComponent implements Component {
    public Reference reference = null;
    public List<Constraint> constraints = null;
}
