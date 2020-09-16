package com.wxxtfxrmx.pirates.system.battlefield;

import com.wxxtfxrmx.pirates.screen.level.battlefield.BattleContext;
import com.wxxtfxrmx.pirates.screen.level.battlefield.Chain;
import com.wxxtfxrmx.pirates.screen.level.battlefield.Ship;
import com.wxxtfxrmx.pirates.screen.level.board.GridContext;
import com.wxxtfxrmx.pirates.screen.level.board.TileType;

public final class ApplyEvasionSystem {

    public void apply(GridContext grid, BattleContext context) {
        if (grid.isLockedUntilAnimation()) return;
        if (context.getLastChain().isEmpty()) return;

        Chain evasionChain = null;
        for (Chain chain : context.getLastChain()) {
            if (chain.getType() == TileType.HELM) {
                evasionChain = chain;
                break;
            }
        }

        if (evasionChain == null) return;

        applyEvasion(context.getAttacker(), evasionChain);
    }

    private void applyEvasion(Ship ship, Chain chain) {
        float evasion = chain.getTilesCount() * 0.05f;
        ship.setEvasion(evasion);
    }
}
