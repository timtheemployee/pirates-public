package com.wxxtfxrmx.pirates.system.board.remove;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.wxxtfxrmx.pirates.screen.level.board.GridContext;
import com.wxxtfxrmx.pirates.screen.level.board.Tile;
import com.wxxtfxrmx.pirates.system.System;
import com.wxxtfxrmx.pirates.system.board.animation.performing.PerformAnimationDelegate;
import com.wxxtfxrmx.pirates.system.board.index.TilesIndexed;

public final class RemoveMatchedTilesSystem implements System {


    private final PerformAnimationDelegate delegate;
    private final GridContext context;
    private final Group parent;

    public RemoveMatchedTilesSystem(PerformAnimationDelegate delegate, GridContext context, Group parent) {
        this.delegate = delegate;
        this.context = context;
        this.parent = parent;
    }

    public void update(GridContext gridContext) {
        Tile[][] tiles = gridContext.getGrid();

        for (int column = 0; column < gridContext.getTilesInColumn(); column++) {
            for (int row = 0; row < gridContext.getTilesInRow(); row++) {
                final int col = column;
                final int rowIndex = row;
                Tile target = tiles[column][row];
                if (target != null && target.isMatched()) {
                    delegate.scaleDown(target, () -> {
                        delegate.remove(target);
                        tiles[col][rowIndex] = null;
                    });
                }
            }
        }
    }

    @Override
    public boolean handle(Event event) {
        if (event instanceof TilesIndexed) {
            update(context);
            return true;
        }

        return false;
    }
}
