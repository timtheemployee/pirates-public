package com.wxxtfxrmx.pirates.screen.level.board;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.wxxtfxrmx.pirates.component.TileSize;
import com.wxxtfxrmx.pirates.entity.factory.TileFactory;
import com.wxxtfxrmx.pirates.screen.level.battlefield.BattleContext;
import com.wxxtfxrmx.pirates.system.battlefield.CollectMatchedTilesSystem;
import com.wxxtfxrmx.pirates.system.battlefield.SwitchShipsSystem;
import com.wxxtfxrmx.pirates.system.board.FillEmptyTilesSystem;
import com.wxxtfxrmx.pirates.system.board.GenerateTilesBoardSystem;
import com.wxxtfxrmx.pirates.system.board.LockBoardUntilAnimationSystem;
import com.wxxtfxrmx.pirates.system.board.MatchTileSystem;
import com.wxxtfxrmx.pirates.system.board.PickTileSystem;
import com.wxxtfxrmx.pirates.system.board.RemoveMatchedTilesSystem;
import com.wxxtfxrmx.pirates.system.board.SwapTileSystem;

import java.util.Random;

public final class Board extends Group {

    private final UiContext uiContext;
    private final GridContext gridContext;
    private final BattleContext battleContext;

    private final GenerateTilesBoardSystem generateTilesBoardSystem;
    private final PickTileSystem pickTileSystem;
    private final SwapTileSystem swapTileSystem;
    private final MatchTileSystem matchTileSystem;
    private final RemoveMatchedTilesSystem removeMatchedTilesSystem;
    private final FillEmptyTilesSystem fillEmptyTilesSystem;
    private final LockBoardUntilAnimationSystem lockBoardUntilAnimationSystem;
    private final SwitchShipsSystem switchShipsSystem;
    private final CollectMatchedTilesSystem collectMatchedTilesSystem;

    public Board(final TileSize tileSize, TileFactory factory, Random random, BattleContext battleContext) {

        setColor(Color.BLUE);
        uiContext = new UiContext(tileSize.getWidth());
        gridContext = new GridContext(tileSize.getWidth());
        TileActionsDelegate delegate = new TileActionsDelegate(gridContext);
        this.battleContext = battleContext;

        generateTilesBoardSystem = new GenerateTilesBoardSystem(factory, random);
        pickTileSystem = new PickTileSystem();
        swapTileSystem = new SwapTileSystem(delegate);
        matchTileSystem = new MatchTileSystem();
        removeMatchedTilesSystem = new RemoveMatchedTilesSystem(delegate);
        fillEmptyTilesSystem = new FillEmptyTilesSystem(random, factory, delegate);
        lockBoardUntilAnimationSystem = new LockBoardUntilAnimationSystem();
        switchShipsSystem = new SwitchShipsSystem();
        collectMatchedTilesSystem = new CollectMatchedTilesSystem();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        gridContext.draw(batch, parentAlpha, this, uiContext);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        swapTileSystem.swap(gridContext);
        matchTileSystem.match(gridContext);
        collectMatchedTilesSystem.collect(gridContext, battleContext);
        lockBoardUntilAnimationSystem.lock(this, gridContext);
        swapTileSystem.skipOrRestore(gridContext);
        removeMatchedTilesSystem.update(gridContext);
        fillEmptyTilesSystem.fill(gridContext);
        switchShipsSystem.swap(gridContext, battleContext, delta);
    }

    @Override
    public void setBounds(float x, float y, float width, float height) {
        super.setBounds(x, y, width, height);
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
