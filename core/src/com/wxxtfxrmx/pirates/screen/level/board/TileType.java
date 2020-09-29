package com.wxxtfxrmx.pirates.screen.level.board;

public enum TileType {
    COIN("coin_sheet"),
    BOMB("bomb_sheet"),
    HELM("helm_sheet"),
    @Deprecated
    SAMPLE("sample"),
    REPAIR("repair_sheet")
    ;

    private final String sheet;

    TileType(String sheet) {
        this.sheet = sheet;
    }

    public String getSheet() {
        return sheet;
    }
}
