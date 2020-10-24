package com.wxxtfxrmx.pirates.screen.levelv2.layer.ui.world;

public enum UiButtonType {
    COMMON("crumpled-idle", "crumpled-clicked"),
    SHADOWED("shadowed-idle", "shadowed-clicked")
    ;

    private final String idle;
    private final String picked;

    UiButtonType(String idle, String picked) {
        this.idle = idle;
        this.picked = picked;
    }

    public String getIdle() {
        return idle;
    }

    public String getPicked() {
        return picked;
    }
}
