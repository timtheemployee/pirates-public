package com.wxxtfxrmx.pirates.system.battlefield;

import com.badlogic.gdx.Gdx;
import com.wxxtfxrmx.pirates.screen.level.battlefield.BattleContext;
import com.wxxtfxrmx.pirates.screen.level.board.GridContext;

import java.util.Locale;

public final class SwitchAttackerSystem {

    private final static float SWITCH_SHIP_DELAY = 0.300f;

    public void switchShips(GridContext grid, BattleContext context) {
        //Gdx.app.error("SwitchAttackerSystem", String.format(Locale.ENGLISH, "Idle delay %f", grid.getIdleDelay()));
        if (!grid.isLockedUntilAnimation() && grid.getIdleDelay() >= SWITCH_SHIP_DELAY) {
            grid.dropIdleDelay();
            context.switchShips();
        }
    }
}
