package com.wxxtfxrmx.pirates.system.board;

import com.wxxtfxrmx.pirates.screen.level.board.GridContext;
import com.wxxtfxrmx.pirates.screen.level.board.Tile;
import com.wxxtfxrmx.pirates.screen.level.board.TileActionsDelegate;

public final class RemoveMatchedTilesSystem {

    private final TileActionsDelegate delegate;

    public RemoveMatchedTilesSystem(TileActionsDelegate delegate) {
        this.delegate = delegate;
    }

    public void update(GridContext gridContext) {
        Tile[][] tiles = gridContext.getGrid();

        for (int column = 0; column < gridContext.getTilesInColumn(); column++) {
            for (int row = 0; row < gridContext.getTilesInRow(); row++) {
                final int col = column;
                final int rowIndex = row;
                Tile target = tiles[column][row];
                if (target != null && target.isMatched() && !target.hasActions()) {
                    delegate.match(target, () -> {
                        target.remove();
                        tiles[col][rowIndex] = null;
                    });
                }
            }
        }
    }
}
