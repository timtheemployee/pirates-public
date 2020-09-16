package com.wxxtfxrmx.pirates.system.battlefield;

import com.wxxtfxrmx.pirates.screen.level.battlefield.BattleContext;
import com.wxxtfxrmx.pirates.screen.level.battlefield.Chain;
import com.wxxtfxrmx.pirates.screen.level.battlefield.Ship;
import com.wxxtfxrmx.pirates.screen.level.board.GridContext;
import com.wxxtfxrmx.pirates.screen.level.board.TileType;

public final class ApplyRepairSystem {

    public void apply(GridContext grid, BattleContext context) {
        if (grid.isLockedUntilAnimation()) return;
        if (context.getLastChain().isEmpty()) return;

        Chain repairChain = null;
        for (Chain chain: context.getLastChain()) {
            if (chain.getType() == TileType.REPAIR) {
                repairChain = chain;
                break;
            }
        }

        if (repairChain == null) return;

        applyRepair(context.getAttacker(), repairChain);
    }

    private void applyRepair(Ship attacker, Chain repairChain) {
        int newHp = attacker.getHp() + (int) (attacker.getHp() * repairChain.getTilesCount() * 0.1f);
        attacker.setHp(newHp);
    }
}
