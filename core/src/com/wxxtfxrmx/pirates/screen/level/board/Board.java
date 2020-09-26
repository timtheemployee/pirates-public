package com.wxxtfxrmx.pirates.screen.level.board;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.wxxtfxrmx.pirates.component.TileSize;
import com.wxxtfxrmx.pirates.entity.factory.TileFactory;
import com.wxxtfxrmx.pirates.event.ExternalEvent;
import com.wxxtfxrmx.pirates.event.ExternalEventBridge;
import com.wxxtfxrmx.pirates.system.board.animation.EventsAccumulationSystem;
import com.wxxtfxrmx.pirates.system.board.animation.performing.PerformAnimationDelegate;
import com.wxxtfxrmx.pirates.system.board.distribute.DistributePickedTilesSystem;
import com.wxxtfxrmx.pirates.system.board.fill.FillEmptyTilesSystem;
import com.wxxtfxrmx.pirates.system.board.generate.GenerateTilesBoardSystem;
import com.wxxtfxrmx.pirates.system.board.index.TilesIndexSystem;
import com.wxxtfxrmx.pirates.system.board.pick.PickTileSystem;
import com.wxxtfxrmx.pirates.system.board.remove.RemoveMatchedTilesSystem;
import com.wxxtfxrmx.pirates.system.board.swap.SwapTileSystem;

import java.util.Random;

public final class Board extends Group {

    private final UiContext uiContext;
    private final GridContext gridContext;

    private final GenerateTilesBoardSystem generateTilesBoardSystem;
    private final PickTileSystem pickTileSystem;
    private final DistributePickedTilesSystem distributePickedTilesSystem;
    private final SwapTileSystem swapTileSystem;
    private final TilesIndexSystem tilesIndexSystem;
    private final RemoveMatchedTilesSystem removeMatchedTilesSystem;
    private final FillEmptyTilesSystem fillEmptyTilesSystem;
    private final EventsAccumulationSystem eventsAccumulationSystem;

    private ExternalEventBridge bridge;

    public Board(final TileSize tileSize, TileFactory factory, Random random) {

        setColor(Color.BLUE);
        uiContext = new UiContext(tileSize.getWidth());
        gridContext = new GridContext(tileSize.getWidth());
        PerformAnimationDelegate delegate = new PerformAnimationDelegate(this);

        generateTilesBoardSystem = new GenerateTilesBoardSystem(factory, random, this, gridContext);
        pickTileSystem = new PickTileSystem(this);
        distributePickedTilesSystem = new DistributePickedTilesSystem(this, gridContext);
        swapTileSystem = new SwapTileSystem(delegate, this, gridContext);
        tilesIndexSystem = new TilesIndexSystem(this, gridContext);
        removeMatchedTilesSystem = new RemoveMatchedTilesSystem(delegate, gridContext, this);
        fillEmptyTilesSystem = new FillEmptyTilesSystem(random, factory, delegate);
        eventsAccumulationSystem = new EventsAccumulationSystem(this);

        addListener(this::handleEvents);
    }

    public void setBridge(ExternalEventBridge bridge) {
        this.bridge = bridge;
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

        if (removeMatchedTilesSystem.handle(event)) {
            return true;
        }

        if (bridge != null && event instanceof ExternalEvent) {
            ExternalEvent external = (ExternalEvent) event;
            bridge.send(external);
            return true;
        }

        return false;
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
        fillEmptyTilesSystem.fill(gridContext);
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
