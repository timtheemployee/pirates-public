package com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.CollectedTilesComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.CurrentTurnComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.CoinComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.board.world.TileType;

public class ApplyGoldSystem extends IteratingSystem {

    private final ComponentMapper<CollectedTilesComponent> collectedTilesMapper = ComponentMapper.getFor(CollectedTilesComponent.class);
    private final ComponentMapper<CoinComponent> coinMapper = ComponentMapper.getFor(CoinComponent.class);
    private final Family attackerFamily = Family.all(CoinComponent.class, CurrentTurnComponent.class).get();

    public ApplyGoldSystem() {
        super(Family.all(CollectedTilesComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        CollectedTilesComponent collectedTiles = collectedTilesMapper.get(entity);

        if (collectedTiles.type != TileType.COIN) return;

        Entity attacker = getEngine().getEntitiesFor(attackerFamily).first();
        CoinComponent coinComponent = coinMapper.get(attacker);
        coinComponent.value += collectedTiles.size;
    }
}
