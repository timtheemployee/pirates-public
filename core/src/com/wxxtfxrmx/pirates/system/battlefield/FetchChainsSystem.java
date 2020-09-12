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

public final class FetchChainsSystem {

    private Map<TileType, Integer> tileChains = new HashMap<>();

    public void fetch(BattleContext battle, GridContext grid) {

        for (Tile[] row : grid.getGrid()) {
            for (Tile tile : row) {
                if (tile != null && tile.isMatched()) {
                    putTile(tile);
                }
            }
        }

        updateBattleContext(battle, tileChains);
        tileChains.clear();
    }

    private void putTile(Tile tile) {
        if (tileChains.containsKey(tile.getType())) {
            int value = tileChains.get(tile.getType());
            tileChains.put(tile.getType(), ++value);
        } else {
            tileChains.put(tile.getType(), 1);
        }
    }

    private void updateBattleContext(BattleContext context, Map<TileType, Integer> chainsMap) {
        List<Chain> chains = new ArrayList<>();

        for (Map.Entry<TileType, Integer> entry: chainsMap.entrySet()) {
            chains.add(new Chain(entry.getKey(), entry.getValue()));
        }

        context.updateChains(chains);
    }
}
