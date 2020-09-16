package com.wxxtfxrmx.pirates.system.board;

import com.badlogic.gdx.utils.Align;
import com.wxxtfxrmx.pirates.screen.level.board.GridContext;
import com.wxxtfxrmx.pirates.screen.level.board.Tile;

public final class PickTileSystem {

    public boolean onTouchDown(float x, float y, GridContext gridContext) {
        if (gridContext.isLockedUntilAnimation()) return false;

        Tile[][] grid = gridContext.getGrid();

        for (Tile[] row : grid) {
            for (Tile tile : row) {
                if (tile == null) continue;

                float startX = tile.getX();
                float endX = tile.getX(Align.right);

                float bottomY = tile.getY();
                float topY = tile.getY(Align.top);

                if (x >= startX && x <= endX && y >= bottomY && y <= topY) {
                    if (gridContext.getPicked() == null) {
                        gridContext.setPicked(tile);
                        gridContext.getPicked().updateState();
                    } else if (gridContext.getTarget() == null) {
                        if (isInPickedTileBounds(x, y, gridContext)) {
                            gridContext.setTarget(tile);
                        } else {
                            gridContext.getPicked().updateState();
                            gridContext.setPicked(tile);
                            gridContext.getPicked().updateState();
                        }
                    }

                    return true;
                }
            }
        }

        return false;
    }

    private boolean isInPickedTileBounds(float x, float y, GridContext gridContext) {
        if (gridContext.getPicked() == null)
            throw new IllegalStateException("Call this method after picking pickedActor");

        float pickedStart = gridContext.getPicked().getX();
        float pickedEnd = gridContext.getPicked().getX(Align.right);

        float pickedBottom = gridContext.getPicked().getY();
        float pickedTop = gridContext.getPicked().getY(Align.top);
        float width = gridContext.getPicked().getWidth();

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
