package com.wxxtfxrmx.pirates.system.battlefield;

import com.wxxtfxrmx.pirates.screen.level.battlefield.BattleContext;
import com.wxxtfxrmx.pirates.screen.level.board.GridContext;

public final class CleanupTurnSystem {

    public void clean(GridContext grid, BattleContext context) {
        if (grid.isLockedUntilAnimation()) return;
        context.getLastChain().clear();
    }
}
