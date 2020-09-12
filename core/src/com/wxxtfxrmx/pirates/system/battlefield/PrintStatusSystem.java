package com.wxxtfxrmx.pirates.system.battlefield;

import com.badlogic.gdx.Gdx;
import com.wxxtfxrmx.pirates.screen.level.battlefield.BattleContext;
import com.wxxtfxrmx.pirates.screen.level.battlefield.Chain;
import com.wxxtfxrmx.pirates.screen.level.battlefield.Ship;

import java.util.List;
import java.util.Locale;

public final class PrintStatusSystem {

    public void print(BattleContext context) {
        if (context.getLastChain().isEmpty()) return;

        String whoIsAttacker = String.format("Attacker is %s", context.getTurn());
        Ship attacker = context.getAttacker();
        String attackerStatus = String.format(Locale.ENGLISH,"Attacker status: hp %d, evasion %f", attacker.getHp(), attacker.getEvasion());
        Ship attacked = context.getAttacked();
        String attackedStatus = String.format(Locale.ENGLISH,"Attacker status: hp %d, evasion %f", attacked.getHp(), attacked.getEvasion());
        StringBuilder lastChainWas = new StringBuilder();
        lastChainWas.append("Last chain was: [");
        List<Chain> chains = context.getLastChain();

        for(Chain chain: chains) {
            lastChainWas.append(String.format(Locale.ENGLISH,"%s: %d", chain.getType(), chain.getTilesCount()));
        }

        lastChainWas.append("]");

        String result = lastChainWas.toString();

        String report = String.format("\n********\n%s\n%s\n%s\n%s\n********", whoIsAttacker, attackerStatus, attackedStatus, result);

        Gdx.app.error("PrintStatusSystem", report);
    }
}
