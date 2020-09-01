package com.wxxtfxrmx.pirates.system;

import com.wxxtfxrmx.pirates.entity.GridContext;
import com.wxxtfxrmx.pirates.entity.Tile;
import com.wxxtfxrmx.pirates.entity.TileType;
import com.wxxtfxrmx.pirates.entity.factory.TileFactory;

import java.util.Random;

public class FillEmptyTilesSystem {

    private final Random random;
    private final TileFactory factory;

    public FillEmptyTilesSystem(Random random, TileFactory factory) {
        this.random = random;
        this.factory = factory;
    }

    public void fill(GridContext gridContext) {
        Tile[][] tiles = gridContext.getGrid();

        for (int column = 0; column < gridContext.getTilesInColumn(); column++) {
            for (int row = 0; row < gridContext.getTilesInRow(); row++) {
                Tile target = tiles[column][row];
                if (target == null) {
                    Tile tile = createTile();
                    tiles[column][row] = tile;
                    tile.onCreate();
                }
            }
        }
    }

    private Tile createTile() {
        TileType type = TileType.values()[random.nextInt(TileType.values().length)];

        return factory.of(type);
    }
}
