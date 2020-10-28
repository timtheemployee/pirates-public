package com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.CollectedTilesComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.RemainedTimeComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.board.component.TilePickedComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.board.component.TouchChainComponent;

import java.util.Locale;

public class SwitchTurnSystem extends IteratingSystem {

    private final ComponentMapper<RemainedTimeComponent> remainedTimeMapper = ComponentMapper.getFor(RemainedTimeComponent.class);
    private final ComponentMapper<CollectedTilesComponent> collectedTilesMapper = ComponentMapper.getFor(CollectedTilesComponent.class);

    private final Family touchChainFamily = Family.all(TouchChainComponent.class).get();
    private final Family touchedTilesFamily = Family.all(TilePickedComponent.class).get();
    private final Family remainingTimeFamily = Family.all(RemainedTimeComponent.class).get();

    public SwitchTurnSystem() {
        super(Family.one(RemainedTimeComponent.class, CollectedTilesComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        if (remainedTimeMapper.has(entity)) {
            RemainedTimeComponent remainedTimeComponent = remainedTimeMapper.get(entity);
            if (remainedTimeComponent.remainedTime == 0) {
                cleanupTouchChain();
                cleanupTouchedTiles();
            }

        } else if (collectedTilesMapper.has(entity)) {
            CollectedTilesComponent collectedTilesComponent = collectedTilesMapper.get(entity);

            Gdx.app.error("TAG", "Switching turn after collectedComponent");
            Gdx.app.error("TAG", String.format(Locale.ENGLISH,"Switching after combination %d, %s", collectedTilesComponent.size, collectedTilesComponent.type.toString()));

            collectedTilesComponent.size = 0;
            collectedTilesComponent.type = null;

            entity.remove(CollectedTilesComponent.class);
            getEngine().removeEntity(entity);

            dropCountDownTimer();
        }
    }

    private void dropCountDownTimer() {
        ImmutableArray<Entity> countDownEntities = getEngine().getEntitiesFor(remainingTimeFamily);

        countDownEntities.forEach(entity -> {
            RemainedTimeComponent remainedTimeComponent = remainedTimeMapper.get(entity);
            remainedTimeComponent.remainedTime = 0;
        });
    }

    private void cleanupTouchChain() {
        ImmutableArray<Entity> touchChainEntities = getEngine().getEntitiesFor(touchChainFamily);

        if (touchChainEntities.size() == 0) return;

        touchChainEntities.forEach(entity -> {
            entity.getComponent(TouchChainComponent.class).chain.clear();
            getEngine().removeEntity(entity);
        });
    }

    private void cleanupTouchedTiles() {
        ImmutableArray<Entity> touchedTilesEntities = getEngine().getEntitiesFor(touchedTilesFamily);

        if (touchedTilesEntities.size() == 0) return;

        touchedTilesEntities.forEach(entity -> entity.remove(TilePickedComponent.class));
    }
}
