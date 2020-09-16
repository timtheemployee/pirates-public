package com.wxxtfxrmx.pirates.system.board;

import com.wxxtfxrmx.pirates.screen.level.board.GridContext;
import com.wxxtfxrmx.pirates.screen.level.board.Tile;

public final class RemoveMatchedTilesSystem {

    public void update(GridContext gridContext) {
        Tile[][] tiles = gridContext.getGrid();

        for (int column = 0; column < gridContext.getTilesInColumn(); column++) {
            for (int row = 0; row < gridContext.getTilesInRow(); row++) {
                final int col = column;
                final int rowIndex = row;
                Tile target = tiles[column][row];
                if (target != null && target.isMatched()) {
                    target.matchAction(() -> {
                        tiles[col][rowIndex] = null;
                    });
                }
            }
        }
    }
}
