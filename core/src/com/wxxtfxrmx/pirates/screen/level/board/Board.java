package com.wxxtfxrmx.pirates.screen.level.board;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.wxxtfxrmx.pirates.component.TileSize;
import com.wxxtfxrmx.pirates.entity.factory.TileFactory;
import com.wxxtfxrmx.pirates.screen.level.battlefield.BattleContext;
import com.wxxtfxrmx.pirates.system.battlefield.CollectMatchedTilesSystem;
import com.wxxtfxrmx.pirates.system.battlefield.SwitchShipsSystem;
import com.wxxtfxrmx.pirates.system.board.FillEmptyTilesSystem;
import com.wxxtfxrmx.pirates.system.board.RemoveMatchedTilesSystem;
import com.wxxtfxrmx.pirates.system.board.animation.EventsAccumulationSystem;
import com.wxxtfxrmx.pirates.system.board.animation.performing.PerformAnimationDelegate;
import com.wxxtfxrmx.pirates.system.board.distribute.DistributePickedTilesSystem;
import com.wxxtfxrmx.pirates.system.board.generate.GenerateTilesBoardSystem;
import com.wxxtfxrmx.pirates.system.board.index.TilesIndexSystem;
import com.wxxtfxrmx.pirates.system.board.pick.PickTileSystem;
import com.wxxtfxrmx.pirates.system.board.swap.SwapTileSystem;

import java.util.Random;

public final class Board extends Group {

    private final UiContext uiContext;
    private final GridContext gridContext;
    private final BattleContext battleContext;

    private final GenerateTilesBoardSystem generateTilesBoardSystem;
    private final PickTileSystem pickTileSystem;
    private final DistributePickedTilesSystem distributePickedTilesSystem;
    private final SwapTileSystem swapTileSystem;
    private final TilesIndexSystem tilesIndexSystem;
    private final RemoveMatchedTilesSystem removeMatchedTilesSystem;
    private final FillEmptyTilesSystem fillEmptyTilesSystem;
    private final SwitchShipsSystem switchShipsSystem;
    private final CollectMatchedTilesSystem collectMatchedTilesSystem;
    private final EventsAccumulationSystem eventsAccumulationSystem;

    public Board(final TileSize tileSize, TileFactory factory, Random random, BattleContext battleContext) {

        setColor(Color.BLUE);
        uiContext = new UiContext(tileSize.getWidth());
        gridContext = new GridContext(tileSize.getWidth());
        PerformAnimationDelegate delegate = new PerformAnimationDelegate(this);
        this.battleContext = battleContext;

        generateTilesBoardSystem = new GenerateTilesBoardSystem(factory, random, this, gridContext);
        pickTileSystem = new PickTileSystem(this);
        distributePickedTilesSystem = new DistributePickedTilesSystem(this, gridContext);
        swapTileSystem = new SwapTileSystem(delegate, this, gridContext);
        tilesIndexSystem = new TilesIndexSystem(this, gridContext);
        removeMatchedTilesSystem = new RemoveMatchedTilesSystem(delegate, gridContext, this);
        fillEmptyTilesSystem = new FillEmptyTilesSystem(random, factory, delegate);
        switchShipsSystem = new SwitchShipsSystem();
        collectMatchedTilesSystem = new CollectMatchedTilesSystem();
        eventsAccumulationSystem = new EventsAccumulationSystem(this);

        addListener(this::handleEvents);
    }

    private boolean handleEvents(Event event) {
        if (eventsAccumulationSystem.handle(event)) {
            return true;
        }

        if (distributePickedTilesSystem.handle(event)) {
            return true;
        }

        if (swapTileSystem.handle(event)) {
            return true;
        }

        if (tilesIndexSystem.handle(event)) {
            return true;
        }

        return removeMatchedTilesSystem.handle(event);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        gridContext.draw(batch, parentAlpha, this, uiContext);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        eventsAccumulationSystem.update();
        tilesIndexSystem.index();
        collectMatchedTilesSystem.collect(gridContext, battleContext);
        removeMatchedTilesSystem.update(gridContext);
        fillEmptyTilesSystem.fill(gridContext);
        switchShipsSystem.swap(gridContext, battleContext, delta);
    }

    @Override
    public void setBounds(float x, float y, float width, float height) {
        super.setBounds(x, y, width, height);
        uiContext.update(width, height);
        gridContext.update(uiContext.end(), uiContext.top());
        generateTilesBoardSystem.update();
    }

    public boolean onTouchDown(float x, float y) {
        if (x <= uiContext.end() && y <= uiContext.top()) {
            return pickTileSystem.onTouchDown(x, y);
        } else {
            return false;
        }
    }
}
