package com.wxxtfxrmx.pirates.system.battlefield;

import com.wxxtfxrmx.pirates.screen.level.battlefield.BattleContext;
import com.wxxtfxrmx.pirates.screen.level.battlefield.Chain;
import com.wxxtfxrmx.pirates.screen.level.board.GridContext;
import com.wxxtfxrmx.pirates.screen.level.board.Tile;
import com.wxxtfxrmx.pirates.screen.level.board.TileType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class CollectMatchedTilesSystem {

    private Map<TileType, Integer> collectedCache = new HashMap<>();

    public void collect(GridContext context, BattleContext battle) {
        for (Tile[] tiles : context.getGrid()) {
            for (Tile tile : tiles) {
                if (tile != null && tile.isMatched()) {
                    collect(tile);
                }
            }
        }

        battle.getTileChains().addAll(getCacheAsChainList(collectedCache));
        collectedCache.clear();
    }

    private void collect(Tile tile) {
        TileType type = tile.getType();

        if (collectedCache.containsKey(type)) {
            int value = collectedCache.get(type);
            collectedCache.put(type, ++value);
        } else {
            collectedCache.put(type, 1);
        }
    }

    private List<Chain> getCacheAsChainList(Map<TileType, Integer> collectedCache) {
        List<Chain> chains = new ArrayList<>();

        for (Map.Entry<TileType, Integer> entry: collectedCache.entrySet()) {
            chains.add(new Chain(entry.getKey(), entry.getValue()));
        }

        return chains;
    }
}
