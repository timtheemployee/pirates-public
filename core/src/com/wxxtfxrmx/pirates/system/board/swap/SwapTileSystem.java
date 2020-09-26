package com.wxxtfxrmx.pirates.system.board.swap;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.wxxtfxrmx.pirates.screen.level.board.GridContext;
import com.wxxtfxrmx.pirates.screen.level.board.Tile;
import com.wxxtfxrmx.pirates.system.board.System;
import com.wxxtfxrmx.pirates.system.board.animation.performing.PerformAnimationDelegate;
import com.wxxtfxrmx.pirates.system.board.distribute.DistributedTiles;

public final class SwapTileSystem implements System {

    private final PerformAnimationDelegate delegate;
    private final Group parent;
    private final GridContext context;

    public SwapTileSystem(PerformAnimationDelegate delegate, Group parent, GridContext context) {
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
            clearPickedTiles(context);
            return true;
        } else if (event instanceof SwapRejected) {
            swap(context.getPicked(), context.getTarget(), context);
            clearPickedTiles(context);
            return true;
        }
        return false;
    }

    private void clearPickedTiles(GridContext context) {
        context.getPicked().updateState();
        context.setPicked(null);
        context.setTarget(null);
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
