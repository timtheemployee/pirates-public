package com.wxxtfxrmx.pirates.screen.level.board;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.wxxtfxrmx.pirates.component.TileSize;
import com.wxxtfxrmx.pirates.entity.factory.TileFactory;
import com.wxxtfxrmx.pirates.screen.level.battlefield.BattleContext;
import com.wxxtfxrmx.pirates.system.battlefield.ApplyDamageSystem;
import com.wxxtfxrmx.pirates.system.battlefield.ApplyEvasionSystem;
import com.wxxtfxrmx.pirates.system.battlefield.ApplyRepairSystem;
import com.wxxtfxrmx.pirates.system.battlefield.CleanupTurnSystem;
import com.wxxtfxrmx.pirates.system.battlefield.FetchChainsSystem;
import com.wxxtfxrmx.pirates.system.battlefield.PrintStatusSystem;
import com.wxxtfxrmx.pirates.system.battlefield.SwitchAttackerSystem;
import com.wxxtfxrmx.pirates.system.board.FillEmptyTilesSystem;
import com.wxxtfxrmx.pirates.system.board.GenerateTilesBoardSystem;
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
    private final FetchChainsSystem fetchChainsSystem;
    private final ApplyDamageSystem applyDamageSystem;
    private final ApplyEvasionSystem applyEvasionSystem;
    private final ApplyRepairSystem applyRepairSystem;
    private final PrintStatusSystem printStatusSystem;
    private final SwitchAttackerSystem switchAttackerSystem;
    private final CleanupTurnSystem cleanupTurnSystem;

    public Board(final TileSize tileSize, TileFactory factory, Random random, BattleContext battleContext) {

        setColor(Color.BLUE);
        uiContext = new UiContext(tileSize.getWidth());
        gridContext = new GridContext(tileSize.getWidth());
        this.battleContext = battleContext;

        generateTilesBoardSystem = new GenerateTilesBoardSystem(factory, random);
        pickTileSystem = new PickTileSystem();
        swapTileSystem = new SwapTileSystem();
        matchTileSystem = new MatchTileSystem();
        removeMatchedTilesSystem = new RemoveMatchedTilesSystem();
        fillEmptyTilesSystem = new FillEmptyTilesSystem(random, factory);

        fetchChainsSystem = new FetchChainsSystem();
        //TODO REMOVE IT TO BATTLEFIELD WIDGET
        applyDamageSystem = new ApplyDamageSystem();
        applyEvasionSystem = new ApplyEvasionSystem();
        applyRepairSystem = new ApplyRepairSystem();
        switchAttackerSystem = new SwitchAttackerSystem();
        cleanupTurnSystem = new CleanupTurnSystem();
        printStatusSystem = new PrintStatusSystem();
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
        fetchChainsSystem.fetch(battleContext, gridContext);
        applyDamageSystem.apply(battleContext);
        applyEvasionSystem.apply(battleContext);
        applyRepairSystem.apply(battleContext);
        printStatusSystem.print(battleContext);
        switchAttackerSystem.switchShips(battleContext);
        cleanupTurnSystem.clean(battleContext);
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
