package com.wxxtfxrmx.pirates.system.battlefield.match;

import com.wxxtfxrmx.pirates.event.ExternalEvent;
import com.wxxtfxrmx.pirates.screen.level.board.TileType;

import java.util.Map;

public class MatchedTiles extends ExternalEvent {
    private final Map<TileType, Integer> matchedTiles;

    public MatchedTiles(Map<TileType, Integer> matchedTiles) {
        this.matchedTiles = matchedTiles;
    }

    public Map<TileType, Integer> getMatchedTiles() {
        return matchedTiles;
    }
}
