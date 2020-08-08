package com.wxxtfxrmx.pirates.component;

public final class TimeAccumulator {

    private float accumulator = 0f;

    public void add(final float elapsedTime) {
        accumulator += elapsedTime;
    }

    public void drop() {
        accumulator = 0f;
    }

    public float getAccumulator() {
        return accumulator;
    }
}
