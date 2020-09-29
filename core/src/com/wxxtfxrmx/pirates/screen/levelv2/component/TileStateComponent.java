package com.wxxtfxrmx.pirates.screen.levelv2.component;

import com.badlogic.ashley.core.Component;
import com.wxxtfxrmx.pirates.screen.level.board.TileState;

public class TileStateComponent implements Component {
    public TileState state = TileState.IDLE;
}
