package com.wxxtfxrmx.pirates.screen.level.board;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

public class GridContext {

    private Tile picked = null;
    private Tile target = null;

    private final float tileSize;
    private int tilesInRow;
    private int tilesInColumn;
    private boolean lockedUntilAnimation = false;

    private Tile[][] grid;

    public GridContext(float tileSize) {
        this.tileSize = tileSize;
    }

    public void update(float width, float height) {
        tilesInRow = (int) (width / tileSize);
        tilesInColumn = (int) (height / tileSize);

        grid = new Tile[tilesInColumn][tilesInRow];
    }

    public void draw(Batch batch, float parentAlpha, Group parent, UiContext uiContext) {
        for (float height = uiContext.bottom(); height < uiContext.top(); height += uiContext.tileSize()) {
            float tileTopY = height + uiContext.tileSize();


            if (tileTopY > uiContext.top()) {
                continue;
            }

            for (float width = uiContext.start(); width < uiContext.end(); width += uiContext.tileSize()) {
                float tileRightX = width + uiContext.tileSize();

                if (tileRightX > uiContext.end()) {
                    continue;
                }

                int gridX = (int) (width / tileSize);
                int gridY = (int) (height / tileSize);

                Actor tile = grid[gridY][gridX];

                if (tile == null) continue;

                if (!tile.hasParent()) {
                    parent.addActor(tile);
                    tile.setBounds(width, height, uiContext.tileSize(), uiContext.tileSize());
                    tile.setPosition(width, height);
                }

                tile.draw(batch, parentAlpha);
            }
        }
    }

    public Tile[][] getGrid() {
        return grid;
    }

    public int getTilesInRow() {
        return tilesInRow;
    }

    public int getTilesInColumn() {
        return tilesInColumn;
    }

    public Tile getPicked() {
        return picked;
    }

    public void setPicked(Tile picked) {
        this.picked = picked;
    }

    public Tile getTarget() {
        return target;
    }

    public void setTarget(Tile target) {
        this.target = target;
    }

    public void setLockedUntilAnimation(boolean lockedUntilAnimation) {
        this.lockedUntilAnimation = lockedUntilAnimation;
    }

    public boolean isLockedUntilAnimation() {
        return lockedUntilAnimation;
    }
}
