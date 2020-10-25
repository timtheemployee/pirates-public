package com.wxxtfxrmx.pirates.uikit;

public enum  Icon {
    GEAR("gear"),
    PAUSE("pause_button"),
    PLAY("play_button"),
    ARROW_DOWN("smooth_arrow_down"),
    ARROW_LEFT("smooth_arrow_left"),
    ARROW_RIGHT("smooth_arrow_right"),
    ARROW_TOP("smooth_arrow_top")
    ;

    private final String value;

    Icon(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public int size() {
        return 32;
    }
}
