package com.wxxtfxrmx.pirates.system.board;

import com.wxxtfxrmx.pirates.screen.level.board.GridContext;
import com.wxxtfxrmx.pirates.screen.level.board.Tile;
import com.wxxtfxrmx.pirates.screen.level.board.TileActionsDelegate;

public final class SwapTileSystem {

    private final TileActionsDelegate delegate;

    public SwapTileSystem(TileActionsDelegate delegate) {
        this.delegate = delegate;
    }

    public void swap(GridContext gridContext) {
        if (gridContext.isLockedUntilAnimation()) return;

        if (gridContext.getPicked() == null || gridContext.getTarget() == null) return;

        Tile picked = gridContext.getPicked();
        Tile target = gridContext.getTarget();

        Position pickedPosition = getPosition(gridContext.getPicked());
        Position targetPosition = getPosition(gridContext.getTarget());

        Tile[][] grid = gridContext.getGrid();

        grid[pickedPosition.column][pickedPosition.row] = target;
        grid[targetPosition.column][targetPosition.row] = picked;

        float pickedX = picked.getX();
        float pickedY = picked.getY();
        
        delegate.move(picked, target.getX(), target.getY());
        delegate.move(target, pickedX, pickedY);

        picked.updateState();
    }

    public void skipOrRestore(GridContext gridContext) {

        if (gridContext.isLockedUntilAnimation()) return;

        if (gridContext.getPicked() == null || gridContext.getTarget() == null) return;

        if (gridContext.getPicked().isMatched() || gridContext.getTarget().isMatched()) {
            gridContext.getPicked().updateState();
            gridContext.setPicked(null);
            gridContext.setTarget(null);
            return;
        }

        swap(gridContext);
        gridContext.getPicked().updateState();

        gridContext.setPicked(null);
        gridContext.setTarget(null);
    }

    private Position getPosition(Tile tile) {
        int row = (int) (tile.getX() / tile.getWidth());
        int column = (int) (tile.getY() / tile.getHeight());

        return new Position(column, row);
    }

    private static class Position {
        private final int column;
        private final int row;

        public Position(int column, int row) {
            this.column = column;
            this.row = row;
        }
    }
}
