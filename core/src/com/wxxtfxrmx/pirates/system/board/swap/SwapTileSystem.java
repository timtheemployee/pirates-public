package com.wxxtfxrmx.pirates.system.board.swap;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.wxxtfxrmx.pirates.screen.level.board.GridContext;
import com.wxxtfxrmx.pirates.screen.level.board.Tile;
import com.wxxtfxrmx.pirates.screen.level.board.TileActionsDelegate;
import com.wxxtfxrmx.pirates.system.board.System;
import com.wxxtfxrmx.pirates.system.board.distribute.DistributedTiles;
import com.wxxtfxrmx.pirates.system.board.index.TilesIndexed;

public final class SwapTileSystem implements System {

    private final TileActionsDelegate delegate;
    private final Group parent;
    private final GridContext context;

    public SwapTileSystem(TileActionsDelegate delegate, Group parent, GridContext context) {
        this.delegate = delegate;
        this.parent = parent;
        this.context = context;
    }

    @Override
    public boolean handle(Event event) {
        if (event instanceof DistributedTiles) {
            swap(context.getPicked(), context.getTarget(), context);
            parent.fire(new SwapAttempt());
            return true;
        } else if (event instanceof SwapConfirmed) {
            context.setPicked(null);
            context.setTarget(null);
            parent.fire(new TilesIndexed());
            return true;
        } else if (event instanceof SwapRejected) {
            swap(context.getPicked(), context.getTarget(), context);
            parent.fire(new TilesIndexed());
            return true;
        }
        return false;
    }

    private void swap(Tile picked, Tile target, GridContext context) {
        Position pickedPosition = getPosition(picked);
        Position targetPosition = getPosition(target);

        Tile[][] grid = context.getGrid();

        grid[pickedPosition.column][pickedPosition.row] = target;
        grid[targetPosition.column][targetPosition.row] = picked;

        float pickedX = picked.getX();
        float pickedY = picked.getY();
        
        delegate.move(picked, target.getX(), target.getY());
        delegate.move(target, pickedX, pickedY);

        picked.updateState();
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