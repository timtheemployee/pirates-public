package com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.world;

public enum ShipTexture {
    BASE_SHIP("base_ship"),
    FRONT_SAIL("front_sail"),
    BACK_SAIL("back_sail"),
    BACK_SMALL_SAIL("back_sail_small");

    private final String textureName;

    ShipTexture(String textureName) {
        this.textureName = textureName;
    }

    public String getTextureName() {
        return textureName;
    }
}