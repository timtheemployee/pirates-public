package com.wxxtfxrmx.pirates.system.board;

import com.wxxtfxrmx.pirates.screen.level.board.GridContext;
import com.wxxtfxrmx.pirates.screen.level.board.Tile;

public final class MatchTileSystem {

    public void match(GridContext gridContext) {
        Tile[][] grid = gridContext.getGrid();
        for (int column = 0; column < gridContext.getTilesInColumn(); column++) {
            for (int row = 0; row < gridContext.getTilesInRow(); row++) {
                Tile tile = grid[column][row];

                if (tile == null) continue;

                Position position = new Position(row, column);

                horizontalMatch(grid, tile, position, gridContext.getTilesInColumn());
                verticalMatch(grid, tile, position, gridContext.getTilesInRow());
            }
        }
    }

    private void  verticalMatch(Tile[][] grid, Tile tile, Position position, int tilesInColumn) {
        if (position.row == 0 || position.row == tilesInColumn - 1) return;

        Tile top = grid[position.column][position.row + 1];
        Tile bottom = grid[position.column][position.row - 1];

        if (top == null || bottom == null) return;

        if (haveEqualTypes(tile, top, bottom)) {
            match(top, bottom, tile);
        }
    }

    private void horizontalMatch(Tile[][] grid, Tile tile, Position position, int tilesInRow) {
        if (position.column == 0 || position.column == tilesInRow - 1) return;

        Tile left = grid[position.column - 1][position.row];
        Tile right = grid[position.column + 1][position.row];

        if (left == null || right == null) return;

        if (haveEqualTypes(tile, left, right)) {
            match(left, right, tile);
        }
    }

    private void match(Tile... tiles) {
        for (Tile tile: tiles) {
            tile.toFront();
            tile.setMatched(true);
        }
    }

    private boolean haveEqualTypes(Tile tile, Tile other, Tile another) {
        return other.getType() == tile.getType() && another.getType() == tile.getType();
    }

    private static class Position {
        private final int row;
        private final int column;

        public Position(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }
}
