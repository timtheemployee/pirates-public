package com.wxxtfxrmx.pirates.system;

import com.wxxtfxrmx.pirates.entity.GridContext;
import com.wxxtfxrmx.pirates.entity.Tile;

public class RemoveMatchedTilesSystem {

    public void update(GridContext gridContext) {
        Tile[][] tiles = gridContext.getGrid();

        for (int column = 0; column < gridContext.getTilesInColumn(); column++) {
            for (int row = 0; row < gridContext.getTilesInRow(); row++) {
                final int col = column;
                final int rowIndex = row;
                Tile target = tiles[column][row];
                if (target != null && target.isMatched()) {
                    target.onMatch(() -> {
                        tiles[col][rowIndex] = null;
                    });
                }
            }
        }
    }
}
