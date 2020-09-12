package com.wxxtfxrmx.pirates.system.battlefield;

import com.wxxtfxrmx.pirates.screen.level.battlefield.BattleContext;

public final class CleanupTurnSystem {

    public void clean(BattleContext context) {
        context.getLastChain().clear();
    }
}
