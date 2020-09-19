package com.wxxtfxrmx.pirates.system.board;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.wxxtfxrmx.pirates.screen.level.board.Board;
import com.wxxtfxrmx.pirates.screen.level.board.GridContext;

public final class LockBoardUntilAnimationSystem {

    public void lock(Board board, GridContext gridContext) {

        for (Actor actor: board.getChildren()) {
            if (actor.hasActions()) {
                gridContext.setLockedUntilAnimation(true);
                break;
            }

            gridContext.setLockedUntilAnimation(false);
        }
    }
}
