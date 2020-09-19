package com.wxxtfxrmx.pirates.system.battlefield;

import com.wxxtfxrmx.pirates.screen.level.battlefield.BattleContext;
import com.wxxtfxrmx.pirates.screen.level.battlefield.Turn;
import com.wxxtfxrmx.pirates.screen.level.board.GridContext;

public class SwitchShipsSystem {

    private static final float IDLE_DELAY = 0.4f;

    private Turn lastTurn = null;
    private float idleDelta = 0f;

    public void swap(GridContext grid, BattleContext battle, float delta) {
        if (lastTurn == null) {
            lastTurn = battle.getTurn();
        }

        if (grid.isLockedUntilAnimation()) return;

        if (idleDelta < IDLE_DELAY) {
            idleDelta += delta;
        } else if (!battle.getTileChains().isEmpty()){
            battle.setOppositeTurn(lastTurn);
            lastTurn = battle.getTurn();
            idleDelta = 0;
            battle.getTileChains().clear();
        }
    }
}
