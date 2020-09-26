package com.wxxtfxrmx.pirates.system.board.remove;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.wxxtfxrmx.pirates.screen.level.board.GridContext;
import com.wxxtfxrmx.pirates.screen.level.board.Tile;
import com.wxxtfxrmx.pirates.screen.level.board.TileType;
import com.wxxtfxrmx.pirates.system.System;
import com.wxxtfxrmx.pirates.system.battlefield.match.MatchedTiles;
import com.wxxtfxrmx.pirates.system.board.animation.performing.PerformAnimationDelegate;
import com.wxxtfxrmx.pirates.system.board.index.TilesIndexed;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class RemoveMatchedTilesSystem implements System {

    private final PerformAnimationDelegate delegate;
    private final GridContext context;
    private final Group parent;
    private final List<Tile> matched = new ArrayList<>();

    public RemoveMatchedTilesSystem(PerformAnimationDelegate delegate, GridContext context, Group parent) {
        this.delegate = delegate;
        this.context = context;
        this.parent = parent;
    }

    private void update(GridContext gridContext) {
        Tile[][] tiles = gridContext.getGrid();
        matched.clear();
        for (int column = 0; column < gridContext.getTilesInColumn(); column++) {
            for (int row = 0; row < gridContext.getTilesInRow(); row++) {
                final int col = column;
                final int rowIndex = row;
                Tile target = tiles[column][row];
                if (target != null && target.isMatched()) {
                    matched.add(target);
                    delegate.scaleDown(target, () -> {
                        delegate.remove(target);
                        tiles[col][rowIndex] = null;
                    });
                }
            }
        }

        parent.fire(new MatchedTiles(reduce(matched)));
    }

    private Map<TileType, Integer> reduce(List<Tile> matched) {
        Map<TileType, Integer> reduced = new HashMap<>();

        for (Tile tile : matched) {
            TileType type = tile.getType();

            if (reduced.containsKey(type)) {
                int count = reduced.get(type);
                reduced.put(type, ++count);
            } else {
                reduced.put(type, 1);
            }
        }

        return reduced;
    }

    @Override
    public boolean handle(Event event) {
        if (event instanceof TilesIndexed) {
            update(context);
            return true;
        }

        return false;
    }
}
