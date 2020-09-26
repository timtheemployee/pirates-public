package com.wxxtfxrmx.pirates.system.board.index;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.wxxtfxrmx.pirates.screen.level.board.GridContext;
import com.wxxtfxrmx.pirates.screen.level.board.Tile;
import com.wxxtfxrmx.pirates.system.System;
import com.wxxtfxrmx.pirates.system.board.generate.BoardGenerated;
import com.wxxtfxrmx.pirates.system.board.swap.SwapAttempt;
import com.wxxtfxrmx.pirates.system.board.swap.SwapConfirmed;
import com.wxxtfxrmx.pirates.system.board.swap.SwapRejected;

public final class TilesIndexSystem implements System {

    private final GridContext context;
    private final Group parent;

    public TilesIndexSystem(Group parent, GridContext context) {
        this.parent = parent;
        this.context = context;
    }

    //TODO ON SWAP ATTEMPT ALWAYS MAKE MATCH
    // ALWAYS MATCH WHEN BOARD GENERATED
    // THINK ABOUT HOW HANDLE IDLE

    public void index() {
        match(context);
        if (anyMatched(context)) {
            parent.fire(new TilesIndexed());
        }
    }

    private boolean anyMatched(GridContext context) {
        for (Tile[] tiles : context.getGrid()) {
            for (Tile tile : tiles) {
                if (tile != null && tile.isMatched()) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean handle(Event event) {
        if (event instanceof SwapAttempt) {
            match(context);
            handleSwapAttempt();
            return true;
        } else if (event instanceof BoardGenerated) {
            match(context);
            parent.fire(new TilesIndexed());
            return true;
        }

        return false;
    }

    private void handleSwapAttempt() {
        if (context.getPicked().isMatched() || context.getTarget().isMatched()) {
            parent.fire(new SwapConfirmed());
        } else {
            parent.fire(new SwapRejected());
        }
    }

    private void match(GridContext gridContext) {
        if (gridContext.isLockedUntilAnimation()) return;

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

    private void verticalMatch(Tile[][] grid, Tile tile, Position position, int tilesInColumn) {
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
        for (Tile tile : tiles) {
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
