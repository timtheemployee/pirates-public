package com.wxxtfxrmx.pirates.system.board.fill;

import com.wxxtfxrmx.pirates.entity.factory.TileFactory;
import com.wxxtfxrmx.pirates.screen.level.board.GridContext;
import com.wxxtfxrmx.pirates.screen.level.board.Tile;
import com.wxxtfxrmx.pirates.screen.level.board.TileType;
import com.wxxtfxrmx.pirates.system.board.animation.performing.PerformAnimationDelegate;

import java.util.Random;

public final class FillEmptyTilesSystem {

    private final Random random;
    private final TileFactory factory;
    private final PerformAnimationDelegate delegate;

    public FillEmptyTilesSystem(Random random, TileFactory factory, PerformAnimationDelegate delegate) {
        this.random = random;
        this.factory = factory;
        this.delegate = delegate;
    }

    public void fill(GridContext gridContext) {
        Tile[][] tiles = gridContext.getGrid();

        for (int column = 0; column < gridContext.getTilesInColumn(); column++) {
            for (int row = 0; row < gridContext.getTilesInRow(); row++) {
                Tile target = tiles[column][row];
                if (target == null) {
                    Tile tile = createTile();
                    tiles[column][row] = tile;
                    delegate.scaleUp(tile);
                }
            }
        }
    }

    private Tile createTile() {
        TileType type = TileType.values()[random.nextInt(TileType.values().length)];

        return factory.of(type);
    }
}
