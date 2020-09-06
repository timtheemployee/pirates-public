package com.wxxtfxrmx.pirates.screen.level.board;

public class UiContext {

    private final float tileSize;

    private float width;
    private float height;

    private float horizontalOffset;
    private float verticalOffset;

    public UiContext(float tileSize) {
        this.tileSize = tileSize;
    }

    private float getParameterOffset(float parameter, float tileSize) {
        float fullOffset = parameter - (int) (parameter / tileSize) * tileSize;

        return fullOffset / 2;
    }

    public void update(float width, float height) {
        this.width = width;
        this.height = height;

        this.horizontalOffset = getParameterOffset(width, tileSize);
        this.verticalOffset = getParameterOffset(height, tileSize);
    }

    public float tileSize() {
        return tileSize;
    }

    public float start() {
        return horizontalOffset;
    }

    public float end() {
        return width - horizontalOffset;
    }

    public float top() {
        return height - verticalOffset;
    }

    public float bottom() {
        return verticalOffset;
    }
}
