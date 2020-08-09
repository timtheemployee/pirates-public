package com.wxxtfxrmx.pirates.system;

import com.badlogic.gdx.utils.Align;
import com.wxxtfxrmx.pirates.component.IndexesPair;
import com.wxxtfxrmx.pirates.entity.Tile;
import com.wxxtfxrmx.pirates.entity.TileType;
import com.wxxtfxrmx.pirates.entity.factory.TileFactory;

import java.util.Random;

public final class FieldManagementSystem {

    private final TileFactory factory;
    private final Random random;

    private Tile[][] tiles;

    private Tile pickedTile = null;
    private Tile targetTile = null;

    public FieldManagementSystem(final TileFactory factory,
                                 final long fieldSsid) {

        this.factory = factory;
        this.random = new Random(fieldSsid);
    }

    public void setSize(final int tilesInColumn, final int tilesInRow) {
        tiles = new Tile[tilesInColumn][tilesInRow];
        for (int i = 0; i < tilesInColumn; i++) {
            tiles[i] = generate(tilesInRow);
        }
    }

    public Tile getTile(final float x, final float y) {
        int column = (int) x / 64;
        int row = (int) y / 64;

        return tiles[column][row];
    }

    private Tile[] generate(final int tilesInRow) {
        final Tile[] row = new Tile[tilesInRow];

        for (int i = 0; i < tilesInRow; i++) {
            row[i] = generate();
        }

        return row;
    }

    //FIXME: Перерисовывать когда были совершены изменения
    public void act(float delta) {
        for (Tile[] row : tiles) {
            for (Tile tile : row) {
                tile.act(delta);
            }
        }

        makeSwapIfPossible();
        match3();
        replaceMatched();
    }

    private Tile generate() {
        final int index = random.nextInt(TileType.values().length);
        final TileType type = TileType.values()[index];

        return factory.of(type);
    }

    private void makeSwapIfPossible() {
        if (pickedTile == null || targetTile == null) {
            return;
        }

        makeSwapTransaction(pickedTile, targetTile);

        pickedTile = null;
        targetTile = null;
    }

    private void makeSwapTransaction(Tile pickedTile, Tile targetTile) {
        final IndexesPair pickedTileIndexes = getTileIndexes(pickedTile);
        final IndexesPair targetTileIndexes = getTileIndexes(targetTile);

        final float pickedX = pickedTile.getX();
        final float pickedY = pickedTile.getY();

        pickedTile.setPosition(targetTile.getX(), targetTile.getY());
        targetTile.setPosition(pickedX, pickedY);

        tiles[pickedTileIndexes.column][pickedTileIndexes.row] = targetTile;
        tiles[targetTileIndexes.column][targetTileIndexes.row] = pickedTile;

        pickedTile.updateState();
    }

    private IndexesPair getTileIndexes(Tile tile) {
        for (int i = 0; i < tiles.length; i++) {
            final Tile[] column = tiles[i];
            for (int j = 0; j < column.length; j++) {
                if (tiles[i][j] == tile) {
                    return new IndexesPair(i, j);
                }
            }
        }

        throw new IllegalStateException("No tile in field");
    }

    public boolean onTouchDown(float x, float y) {
        for (Tile[] value : tiles) {
            for (final Tile tile : value) {
                final float startX = tile.getX();
                final float endX = tile.getX(Align.right);

                final float bottomY = tile.getY();
                final float topY = tile.getY(Align.top);

                if (x >= startX && x <= endX && y >= bottomY && y <= topY) {
                    if (pickedTile == null) {
                        pickedTile = tile;
                        pickedTile.updateState();
                    } else if (targetTile == null) {
                        if (isInPickedTileBounds(x, y)) {
                            targetTile = tile;
                        } else {
                            pickedTile.updateState();
                            pickedTile = null;
                        }
                    }

                    return true;
                }
            }
        }

        return false;
    }

    private boolean isInPickedTileBounds(float x, float y) {
        if (pickedTile == null)
            throw new IllegalStateException("Call this method after picking pickedActor");

        final float pickedStart = pickedTile.getX();
        final float pickedEnd = pickedTile.getX(Align.right);

        final float pickedBottom = pickedTile.getY();
        final float pickedTop = pickedTile.getY(Align.top);

        return isInHorizontalBounds(
                x, y,
                pickedStart, pickedEnd,
                pickedBottom, pickedTop,
                pickedTile.getWidth() // Accepted tile is a square
        ) || isInVerticalBounds(
                x, y,
                pickedStart, pickedEnd,
                pickedBottom, pickedTop,
                pickedTile.getWidth()
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

    private void replaceMatched() {
        for (int column = 0; column < tiles.length; column++) {
            Tile[] row = tiles[column];

            for (int rowIndex = 0; rowIndex < row.length; rowIndex++) {
                final Tile tile = row[rowIndex];

                if (tile.isMatched()) {
                    final Tile newTile = generate();
                    tiles[column][rowIndex] = newTile;
                }
            }
        }
    }

    private void match3() {
        for (int column = 0; column < tiles.length; column++) {
            Tile[] row = tiles[column];
            final IndexesPair boardSize = new IndexesPair(tiles.length - 1, row.length - 1);
            for (int rowIndex = 0; rowIndex < row.length; rowIndex++) {
                final IndexesPair indexes = new IndexesPair(column, rowIndex);
                final Tile tile = tiles[column][rowIndex];

                horizontalMatch(tile, indexes, boardSize);
                verticalMatch(tile, indexes, boardSize);
            }
        }
    }

    private void horizontalMatch(final Tile tile, final IndexesPair indexes, final IndexesPair boardSize) {
        if (!isOnBorder(indexes, boardSize) && !tile.isMatched()) {
            final Tile[] row = tiles[indexes.column];
            final Tile leftNeighbor = row[indexes.row - 1];
            final Tile rightNeighbor = row[indexes.row + 1];

            if (leftNeighbor.getType() == tile.getType() && rightNeighbor.getType() == tile.getType()) {
                leftNeighbor.setMatched(true);
                tile.setMatched(true);
                rightNeighbor.setMatched(true);
            }
        }
    }

    private void verticalMatch(final Tile tile, final IndexesPair indexes, final IndexesPair boardSize) {
        if (!isOnBorder(indexes, boardSize) && !tile.isMatched()) {
            final Tile[] topRow = tiles[indexes.column + 1];
            final Tile[] bottomRow = tiles[indexes.column - 1];

            final Tile topNeighbor = topRow[indexes.row];
            final Tile bottomNeighbor = bottomRow[indexes.row];

            if (topNeighbor.getType() == tile.getType() && bottomNeighbor.getType() == tile.getType()) {
                topNeighbor.setMatched(true);
                tile.setMatched(true);
                bottomNeighbor.setMatched(true);
            }
        }
    }

    private boolean isOnBorder(final IndexesPair indexes, final IndexesPair boardSize) {
        return indexes.column == 0 || indexes.column == boardSize.column
                || indexes.row == 0 || indexes.row == boardSize.row;
    }
}
