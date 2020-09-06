package com.wxxtfxrmx.pirates.system;

import com.wxxtfxrmx.pirates.screen.level.board.GridContext;
import com.wxxtfxrmx.pirates.screen.level.board.Tile;
import com.wxxtfxrmx.pirates.screen.level.board.TileType;
import com.wxxtfxrmx.pirates.entity.factory.TileFactory;

import java.util.Random;

public class GenerateTilesBoardSystem {

    private final TileFactory factory;
    private final Random random;

    public GenerateTilesBoardSystem(TileFactory factory, Random random) {
        this.factory = factory;
        this.random = random;
    }

    public void update(GridContext gridContext) {
        boolean canGenerate = gridContext.getTilesInColumn() != 0 && gridContext.getTilesInRow() != 0;
        Tile[][] grid = gridContext.getGrid();

        if (canGenerate) {
            for(int row = 0; row < gridContext.getTilesInColumn(); row++) {
                grid[row] = createRow(gridContext.getTilesInRow());
            }
        }
    }

    private Tile[] createRow(int tilesInRow) {
        Tile[] tiles = new Tile[tilesInRow];
        for (int position = 0; position < tilesInRow; position++) {
            tiles[position] = createTile();
        }

        return tiles;
    }

    private Tile createTile() {
        TileType type = TileType.values()[random.nextInt(TileType.values().length)];
        return factory.of(type);
    }
}
