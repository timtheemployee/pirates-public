package com.wxxtfxrmx.pirates.system.board.generate;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.wxxtfxrmx.pirates.entity.factory.TileFactory;
import com.wxxtfxrmx.pirates.screen.level.board.GridContext;
import com.wxxtfxrmx.pirates.screen.level.board.Tile;
import com.wxxtfxrmx.pirates.screen.level.board.TileType;

import java.util.Random;

public final class GenerateTilesBoardSystem {

    private final TileFactory factory;
    private final Random random;
    private final Group parent;
    private final GridContext context;

    public GenerateTilesBoardSystem(TileFactory factory, Random random, Group parent, GridContext context) {
        this.factory = factory;
        this.random = random;
        this.parent = parent;
        this.context = context;
    }

    public void update() {
        boolean canGenerate = context.getTilesInColumn() != 0 && context.getTilesInRow() != 0;
        Tile[][] grid = context.getGrid();

        if (canGenerate) {
            for (int row = 0; row < context.getTilesInColumn(); row++) {
                grid[row] = createRow(context.getTilesInRow());
            }

            parent.fire(new BoardGenerated());
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
