package com.wxxtfxrmx.pirates.entity;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.wxxtfxrmx.pirates.component.TileSize;
import com.wxxtfxrmx.pirates.system.FieldManagementSystem;

public final class Field extends Actor {
    private final int tileWidth;
    private final int tileHeight;

    private float horizontalOffset;
    private float verticalOffset;
    private final FieldManagementSystem fieldManagementSystem;

    //TODO: Заюзать ssid для генератора
    public Field(final TileSize tileSize, final FieldManagementSystem fieldManagementSystem) {
        this.fieldManagementSystem = fieldManagementSystem;

        this.tileWidth = tileSize.getWidth();
        this.tileHeight = tileSize.getHeight();

        this.horizontalOffset = getHorizontalOffset();
        this.verticalOffset = getVerticalOffset();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        for (float height = getFieldBottom(); height < getFieldTop(); height += tileHeight) {
            final float tileTopY = height + tileHeight;

            if (tileTopY > getFieldTop()) {
                continue;
            }

            for (float width = getFieldStart(); width < getFieldEnd(); width += tileWidth) {
                final float tileRightX = width + tileWidth;

                if (tileRightX > getFieldEnd()) {
                    continue;
                }

                final Actor tile = fieldManagementSystem.getTile(width, height);
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
        final float rawWidth = getX(Align.right);
        final float fieldWidth = rawWidth - (int) (rawWidth / tileWidth) * tileWidth;

        return fieldWidth / 2;
    }

    private float getVerticalOffset() {
        final float rawHeight = getY(Align.top);
        final float fieldHeight = rawHeight - (int) (rawHeight / tileWidth) * tileWidth;

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
        final int horizontalCount = (int) (getX(Align.right) / tileWidth) * tileWidth;
        final int verticalCount = (int) (getY(Align.top) / tileHeight) * tileHeight;

        fieldManagementSystem.setSize(verticalCount, horizontalCount);
    }
}
