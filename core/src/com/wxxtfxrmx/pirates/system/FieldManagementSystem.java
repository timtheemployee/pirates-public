package com.wxxtfxrmx.pirates.system;

import com.badlogic.gdx.utils.Align;
import com.wxxtfxrmx.pirates.component.IndexesPair;
import com.wxxtfxrmx.pirates.component.Size;
import com.wxxtfxrmx.pirates.entity.Tile;
import com.wxxtfxrmx.pirates.entity.TileType;
import com.wxxtfxrmx.pirates.entity.factory.TileFactory;

import java.util.Random;

//TODO:Refactor it
public final class FieldManagementSystem {

    private final TileFactory factory;
    private final Random random;

    private Tile[][] tiles;

    private Size boardSize;

    private Tile pickedTile = null;
    private Tile targetTile = null;

    public FieldManagementSystem(final TileFactory factory,
                                 final long fieldSsid) {

        this.factory = factory;
        this.random = new Random(fieldSsid);
    }

    public void setSize(final int tilesInColumn, final int tilesInRow) {
        boardSize = new Size(tilesInColumn, tilesInRow);
        tiles = new Tile[tilesInColumn][tilesInRow];
        for (int i = 0; i < tilesInColumn; i++) {
            tiles[i] = createTile(tilesInRow);
        }
    }

    public Tile getTile(final float x, final float y) {
        int column = (int) (x / 64);
        int row = (int) (y / 64);

        return tiles[column][row];
    }

    private Tile[] createTile(final int tilesInRow) {
        Tile[] row = new Tile[tilesInRow];
        for (int i = 0; i < tilesInRow; i++) {
            Tile tile = createTile();
            row[i] = tile;
        }

        return row;
    }

    //FIXME: Перерисовывать когда были совершены изменения
    public void act(float delta) {
        for (int column = 0; column < boardSize.getWidth(); column++) {
            for (int row = 0; row < boardSize.getHeight(); row++) {
                Tile tile = tiles[column][row];
                if (tile.isChanged()) {
                    tile.act(delta);
                }
            }
        }

        match();
        replaceMatched();
    }

    private Tile createTile() {
        int index = random.nextInt(TileType.values().length);
        TileType type = TileType.values()[index];

        return factory.of(type);
    }

    private void replaceMatched() {
        for (int column = 0; column < boardSize.getWidth(); column++) {
            for (int rowIndex = 0; rowIndex < boardSize.getHeight(); rowIndex++) {
                Tile tile = tiles[column][rowIndex];

                if (tile.isMatched()) {
                    //push tile to end of field's child list
                    final int col = column;
                    final int row = rowIndex;

                    tile.onMatch(() -> {
                        Tile newTile = createTile();
                        newTile.setScale(0.1f, 0.1f);
                        tiles[col][row] = newTile;
                        newTile.onCreate();
                    });
                }
            }
        }
    }

    private void match() {
        for (int column = 0; column < tiles.length; column++) {
            Tile[] row = tiles[column];
            final IndexesPair boardSize = new IndexesPair(tiles.length - 1, row.length - 1);
            for (int rowIndex = 0; rowIndex < row.length; rowIndex++) {
                final IndexesPair indexes = new IndexesPair(column, rowIndex);
                final Tile tile = tiles[column][rowIndex];

                verticalMatch(tile, indexes, boardSize);
                horizontalMatch(tile, indexes, boardSize);
            }
        }
    }

    private void verticalMatch(final Tile tile, final IndexesPair indexes, final IndexesPair boardSize) {
        if (isOnBorder(indexes, boardSize)) {
            borderVerticalMatch(tile, indexes, boardSize);
        } else {
            Tile leftNeighbor = tiles[indexes.column][indexes.row - 1];
            Tile rightNeighbor = tiles[indexes.column][indexes.row + 1];
            compareAndMark(tile, leftNeighbor, rightNeighbor);
        }
    }

    private void borderVerticalMatch(final Tile tile, final IndexesPair indexes, final IndexesPair boardSize) {
        if (isOnVerticalBorder(indexes, boardSize)) {
            Tile neighbor = tiles[indexes.column][indexes.row - 1];
            Tile anotherNeighbor = tiles[indexes.column][indexes.row + 1];

            compareAndMark(tile, neighbor, anotherNeighbor);
        }
    }

    private void horizontalMatch(final Tile tile, final IndexesPair indexes, final IndexesPair boardSize) {
        if (isOnBorder(indexes, boardSize)) {
            borderHorizontalMatch(tile, indexes, boardSize);
        } else {
            Tile topNeighbor = tiles[indexes.column + 1][indexes.row];
            Tile bottomNeighbor = tiles[indexes.column - 1][indexes.row];

            compareAndMark(tile, topNeighbor, bottomNeighbor);
        }
    }

    private void borderHorizontalMatch(final Tile tile, final IndexesPair indexes, final IndexesPair boardSize) {
        if (isOnHorizontalBorder(indexes, boardSize)) {
            Tile neighbor = tiles[indexes.column + 1][indexes.row];
            Tile anotherNeighbor = tiles[indexes.column - 1][indexes.row];

            compareAndMark(tile, neighbor, anotherNeighbor);
        }
    }

    private void compareAndMark(Tile target, Tile neighbor, Tile anotherNeighbor) {
        if (neighbor.getType() == target.getType() && anotherNeighbor.getType() == target.getType()) {
            neighbor.setMatched(true);
            target.setMatched(true);
            anotherNeighbor.setMatched(true);
        }
    }

    private boolean isOnBorder(final IndexesPair indexes, final IndexesPair boardSize) {
        return indexes.column == 0 || indexes.column == boardSize.column
                || indexes.row == boardSize.row || indexes.row == 0;
    }

    private boolean isOnVerticalBorder(final IndexesPair indexes, final IndexesPair boardSize) {
        return (indexes.column == 0 || indexes.column == boardSize.column) && !(indexes.row == 0 || indexes.row == boardSize.row);
    }

    private boolean isOnHorizontalBorder(final IndexesPair indexes, final IndexesPair boardSize) {
        return (indexes.row == 0 || indexes.row == boardSize.row) && !(indexes.column == 0 || indexes.column == boardSize.column);
    }
}
