package com.wxxtfxrmx.pirates.screen.level.battlefield;

import com.badlogic.gdx.scenes.scene2d.Group;

import java.util.Random;

public final class BattleField extends Group {

    private final Random random;
    private final Turn turn;

    public BattleField(final Random random) {
        this.random = random;

        turn = Turn.values()[random.nextInt(Turn.values().length)];
    }

    public void onEvent() {

    }
}
