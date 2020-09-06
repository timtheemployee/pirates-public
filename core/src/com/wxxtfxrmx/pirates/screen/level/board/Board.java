package com.wxxtfxrmx.pirates.screen.level.board;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.wxxtfxrmx.pirates.component.TileSize;
import com.wxxtfxrmx.pirates.entity.factory.TileFactory;
import com.wxxtfxrmx.pirates.system.FillEmptyTilesSystem;
import com.wxxtfxrmx.pirates.system.GenerateTilesBoardSystem;
import com.wxxtfxrmx.pirates.system.MatchTileSystem;
import com.wxxtfxrmx.pirates.system.PickTileSystem;
import com.wxxtfxrmx.pirates.system.RemoveMatchedTilesSystem;
import com.wxxtfxrmx.pirates.system.SwapTileSystem;

import java.util.Random;

public final class Board extends Group {

    private final UiContext uiContext;
    private final GridContext gridContext;

    private final GenerateTilesBoardSystem generateTilesBoardSystem;
    private final PickTileSystem pickTileSystem;
    private final SwapTileSystem swapTileSystem;
    private final MatchTileSystem matchTileSystem;
    private final RemoveMatchedTilesSystem removeMatchedTilesSystem;
    private final FillEmptyTilesSystem fillEmptyTilesSystem;

    public Board(final TileSize tileSize, TileFactory factory, Random random) {

        setColor(Color.BLUE);
        uiContext = new UiContext(tileSize.getWidth());
        gridContext = new GridContext(tileSize.getWidth());

        generateTilesBoardSystem = new GenerateTilesBoardSystem(factory, random);
        pickTileSystem = new PickTileSystem();
        swapTileSystem = new SwapTileSystem();
        matchTileSystem = new MatchTileSystem();
        removeMatchedTilesSystem = new RemoveMatchedTilesSystem();
        fillEmptyTilesSystem = new FillEmptyTilesSystem(random, factory);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        gridContext.draw(batch, parentAlpha, this, uiContext);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        gridContext.act(delta);
        swapTileSystem.swap(gridContext);
        matchTileSystem.match(gridContext);
        swapTileSystem.skipOrRestore(gridContext);
        removeMatchedTilesSystem.update(gridContext);
        fillEmptyTilesSystem.fill(gridContext);
    }

    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);
        uiContext.update(width, height);
        gridContext.update(uiContext.end(), uiContext.top());
        generateTilesBoardSystem.update(gridContext);
    }

    public boolean onTouchDown(float x, float y) {
        if (x <= uiContext.end() && y <= uiContext.top()) {
            return pickTileSystem.onTouchDown(x, y, gridContext);
        } else {
            return false;
        }
    }
}
