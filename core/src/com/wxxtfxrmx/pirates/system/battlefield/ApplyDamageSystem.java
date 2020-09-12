package com.wxxtfxrmx.pirates.system.battlefield;

import com.wxxtfxrmx.pirates.screen.level.battlefield.BattleContext;
import com.wxxtfxrmx.pirates.screen.level.battlefield.Chain;
import com.wxxtfxrmx.pirates.screen.level.battlefield.Ship;
import com.wxxtfxrmx.pirates.screen.level.board.TileType;

import java.util.List;

public final class ApplyDamageSystem {

    public void apply(BattleContext context) {
        if (context.getLastChain().isEmpty()) return;

        applyDamage(context.getAttacked(), context.getAttacker(), context.getLastChain());
    }

    private void applyDamage(Ship attacked, Ship attacker, List<Chain> chains) {
        Chain bombChain = null;

        for (Chain chain : chains) {
            if (chain.getType() == TileType.BOMB) {
                bombChain = chain;
                break;
            }
        }

        if (bombChain == null) return;

        float evasion = attacked.getEvasion();
        int damage = attacker.getCannonBallDamage() * (bombChain.getTilesCount() - (int) (bombChain.getTilesCount() * evasion));
        attacked.setHp(attacked.getHp() - damage);
    }
}
