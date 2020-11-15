package com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.CollectedTilesComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.CurrentTurnComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.HpComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.RemainedTimeComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.board.component.TilePickedComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.board.component.TouchChainComponent;

public class SwitchTurnSystem extends IteratingSystem {

    private final ComponentMapper<RemainedTimeComponent> remainedTimeMapper = ComponentMapper.getFor(RemainedTimeComponent.class);
    private final ComponentMapper<CollectedTilesComponent> collectedTilesMapper = ComponentMapper.getFor(CollectedTilesComponent.class);

    private final Family touchChainFamily = Family.all(TouchChainComponent.class).get();
    private final Family touchedTilesFamily = Family.all(TilePickedComponent.class).get();
    private final Family remainingTimeFamily = Family.all(RemainedTimeComponent.class).get();

    private final PooledEngine engine;

    public SwitchTurnSystem(PooledEngine engine) {
        super(Family.one(RemainedTimeComponent.class, CollectedTilesComponent.class).get());
        this.engine = engine;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        if (remainedTimeMapper.has(entity)) {
            RemainedTimeComponent remainedTimeComponent = remainedTimeMapper.get(entity);
            if (remainedTimeComponent.remainedTime == 0) {
                cleanupTouchChain();
                cleanupTouchedTiles();
                switchTurn();
            }

        } else if (collectedTilesMapper.has(entity)) {
            CollectedTilesComponent collectedTilesComponent = collectedTilesMapper.get(entity);

            switchTurn();

            collectedTilesComponent.size = 0;
            collectedTilesComponent.type = null;

            entity.remove(CollectedTilesComponent.class);
            getEngine().removeEntity(entity);

            dropCountDownTimer();
        }
    }

    private void switchTurn() {
        Entity attacker = getEngine()
                .getEntitiesFor(Family.all(HpComponent.class, CurrentTurnComponent.class).get())
                .first();

        Entity defender = getEngine()
                .getEntitiesFor(Family.all(HpComponent.class).exclude(CurrentTurnComponent.class).get())
                .first();

        attacker.remove(CurrentTurnComponent.class);
        CurrentTurnComponent currentTurnComponent = engine.createComponent(CurrentTurnComponent.class);
        defender.add(currentTurnComponent);
    }

    private void dropCountDownTimer() {
        ImmutableArray<Entity> countDownEntities = getEngine().getEntitiesFor(remainingTimeFamily);

        for (Entity entity : countDownEntities) {
            RemainedTimeComponent remainedTimeComponent = remainedTimeMapper.get(entity);
            remainedTimeComponent.remainedTime = 0;
        }
    }

    private void cleanupTouchChain() {
        ImmutableArray<Entity> touchChainEntities = getEngine().getEntitiesFor(touchChainFamily);

        if (touchChainEntities.size() == 0) return;

        for (Entity entity : touchChainEntities) {
            entity.getComponent(TouchChainComponent.class).chain.clear();
            getEngine().removeEntity(entity);
        }
    }

    private void cleanupTouchedTiles() {
        ImmutableArray<Entity> touchedTilesEntities = getEngine().getEntitiesFor(touchedTilesFamily);

        if (touchedTilesEntities.size() == 0) return;

        for (Entity entity : touchedTilesEntities) {
            entity.remove(TilePickedComponent.class);
        }
    }
}
