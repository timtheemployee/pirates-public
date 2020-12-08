package com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.world;

public enum ShipTexture {
    BOMB("bomb"),
    MAIN_SHIP("main_ship"),
    FRONT_SAIL("front_sail"),
    BACK_BOTTOM_SAIL("back_bottom_sail"),
    BACK_TOP_SAIL("back_top_sail");

    private final String textureName;

    ShipTexture(String textureName) {
        this.textureName = textureName;
    }

    public String getTextureName() {
        return textureName;
    }
}