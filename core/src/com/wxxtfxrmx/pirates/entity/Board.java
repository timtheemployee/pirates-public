package com.wxxtfxrmx.pirates.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Collections;
import com.wxxtfxrmx.pirates.component.TileSize;
import com.wxxtfxrmx.pirates.system.FieldManagementSystem;

public final class Board extends Group {
    private final int tileWidth;
    private final int tileHeight;

    private float horizontalOffset;
    private float verticalOffset;
    private final FieldManagementSystem fieldManagementSystem;

    public Board(final TileSize tileSize, final FieldManagementSystem fieldManagementSystem) {
        this.fieldManagementSystem = fieldManagementSystem;

        this.tileWidth = tileSize.getWidth();
        this.tileHeight = tileSize.getHeight();

        this.horizontalOffset = getHorizontalOffset();
        this.verticalOffset = getVerticalOffset();

        setColor(Color.BLUE);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        for (float height = getFieldBottom(); height < getFieldTop(); height += tileHeight) {
            float tileTopY = height + tileHeight;

            if (tileTopY > getFieldTop()) {
                continue;
            }

            for (float width = getFieldStart(); width < getFieldEnd(); width += tileWidth) {
                float tileRightX = width + tileWidth;

                if (tileRightX > getFieldEnd()) {
                    continue;
                }

                Actor tile = fieldManagementSystem.getTile(width, height);
                addActor(tile);
                tile.setBounds(width, height, tileWidth, tileHeight);
                tile.setPosition(width, height);
                tile.draw(batch, parentAlpha);
            }
        }
    }

    private float getFieldTop() {
        return getY(Align.top) - verticalOffset;
    }

    private float getFieldBottom() {
        return getY(Align.bottom) + verticalOffset;
    }

    private float getFieldStart() {
        return getX(Align.left) + horizontalOffset;
    }

    private float getFieldEnd() {
        return getX(Align.right) - horizontalOffset;
    }

    private float getHorizontalOffset() {
        float rawWidth = getX(Align.right);
        float fieldWidth = rawWidth - (int) (rawWidth / tileWidth) * tileWidth;

        return fieldWidth / 2;
    }

    private float getVerticalOffset() {
        float rawHeight = getY(Align.top);
        float fieldHeight = rawHeight - (int) (rawHeight / tileWidth) * tileWidth;

        return fieldHeight / 2;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        fieldManagementSystem.act(delta);
    }

    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);
        horizontalOffset = getHorizontalOffset();
        verticalOffset = getVerticalOffset();
        final int horizontalCount = (int) (getX(Align.right) / tileWidth);
        final int verticalCount = (int) (getY(Align.top) / tileHeight);

        fieldManagementSystem.setSize(horizontalCount, verticalCount);
    }

    public boolean onTouchDown(float x, float y) {
        if (x <= getX(Align.right) && y <= getY(Align.top)) {
            return fieldManagementSystem.onTouchDown(x, y);
        } else {
            return false;
        }
    }
}
