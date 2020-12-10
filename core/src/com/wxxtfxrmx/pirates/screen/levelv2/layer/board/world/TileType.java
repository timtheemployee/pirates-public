package com.wxxtfxrmx.pirates.screen.levelv2.layer.board.world;

public enum TileType {
    COIN("coin_sheet"),
    BOMB("bomb_sheet"),
    HELM("helm_sheet"),
    SPECIAL("special_purple_sheet"),
    REPAIR("repair_sheet");

    private final String atlasPath;

    TileType(String atlasPath) {
        this.atlasPath = atlasPath;
    }

    public String getAtlasPath() {
        return atlasPath;
    }
}
