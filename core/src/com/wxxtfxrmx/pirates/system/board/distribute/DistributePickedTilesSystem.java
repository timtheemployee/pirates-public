package com.wxxtfxrmx.pirates.system.board.distribute;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;
import com.wxxtfxrmx.pirates.screen.level.board.GridContext;
import com.wxxtfxrmx.pirates.screen.level.board.Tile;
import com.wxxtfxrmx.pirates.system.board.System;
import com.wxxtfxrmx.pirates.system.board.pick.PickTile;

public class DistributePickedTilesSystem implements System {

    private final Group parent;
    private final GridContext context;

    public DistributePickedTilesSystem(Group parent, GridContext context) {
        this.parent = parent;
        this.context = context;
    }

    @Override
    public boolean handle(Event event) {
        if (event instanceof PickTile) {
            PickTile picked = (PickTile) event;

            if (context.getPicked() == null) {
                setPicked(context, picked.getTile());
            } else if (context.getTarget() == null) {

                Tile target = picked.getTile();
                boolean inBounds = isInPickedTileBounds(picked.getTile(), context.getPicked());
                if (inBounds) {
                    setTarget(context, target);
                    parent.fire(new DistributedTiles());
                } else {
                    context.getPicked().updateState();
                    setPicked(context, target);
                }
            }

            return true;
        }

        return false;
    }

    private void setTarget(GridContext context, Tile tile) {
        context.setTarget(tile);
    }

    private void setPicked(GridContext context, Tile tile) {
        context.setPicked(tile);
        context.getPicked().updateState();
    }

    private boolean isInPickedTileBounds(Tile target, Tile picked) {
        if (picked == null)
            throw new IllegalStateException("Call this method after picking pickedActor");

        float pickedStart = picked.getX();
        float pickedEnd = picked.getX(Align.right);

        float pickedBottom = picked.getY();
        float pickedTop = picked.getY(Align.top);
        float width = picked.getWidth();

        float x = target.getX(Align.center);
        float y = target.getY(Align.center);

        return isInHorizontalBounds(
                x, y,
                pickedStart, pickedEnd,
                pickedBottom, pickedTop,
                width // Accepted tile is a square
        ) || isInVerticalBounds(
                x, y,
                pickedStart, pickedEnd,
                pickedBottom, pickedTop,
                width
        );
    }

    private boolean isInHorizontalBounds(float x, float y, float tileStart, float tileEnd, float tileBottom, float tileTop, float tileSize) {
        if (y <= tileBottom || y >= tileTop) return false;

        boolean isBeforeStart = x < tileStart && x >= tileStart - tileSize;
        boolean isAfterEnd = x > tileEnd && x <= tileEnd + tileSize;

        return isBeforeStart || isAfterEnd;
    }

    private boolean isInVerticalBounds(float x, float y, float tileStart, float tileEnd, float tileBottom, float tileTop, float tileSize) {
        if (x <= tileStart || x >= tileEnd) return false;

        boolean isBeforeBottom = y < tileBottom && y >= tileBottom - tileSize;
        boolean isAfterTop = y > tileTop && y <= tileTop + tileSize;

        return isBeforeBottom || isAfterTop;
    }
}
