package com.wxxtfxrmx.pirates.uikit;

public enum VerticalMargin {
    TINY(4),
    SMALL(8),
    MEDIUM(16),
    LARGE(24)
    ;

    private final float value;

    VerticalMargin(float value) {
        this.value = value;
    }

    public float getValue() {
        return value;
    }
}
