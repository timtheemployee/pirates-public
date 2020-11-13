package com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.CoinComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.CollectedTilesComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.CurrentTurnComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.board.world.TileType;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.ui.component.SlotMachineMatchedComponent;

public class ApplyCoinsSystem extends IteratingSystem {

    private final ComponentMapper<CollectedTilesComponent> collectedTilesMapper = ComponentMapper.getFor(CollectedTilesComponent.class);
    private final ComponentMapper<SlotMachineMatchedComponent> slotMachineMapper = ComponentMapper.getFor(SlotMachineMatchedComponent.class);
    private final ComponentMapper<CoinComponent> coinMapper = ComponentMapper.getFor(CoinComponent.class);
    private final Family attackerFamily = Family.all(CoinComponent.class, CurrentTurnComponent.class).get();

    public ApplyCoinsSystem() {
        super(Family.one(CollectedTilesComponent.class, SlotMachineMatchedComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        if (collectedTilesMapper.has(entity)) {
            CollectedTilesComponent collectedTiles = collectedTilesMapper.get(entity);
            if (collectedTiles.type != TileType.COIN) return;
            applyCoins(collectedTiles.size);

        } else if (slotMachineMapper.has(entity)) {
            SlotMachineMatchedComponent slotMachineMatchedComponent = slotMachineMapper.get(entity);
            if (slotMachineMatchedComponent.tileType != TileType.COIN) return;
            applyCoins(slotMachineMatchedComponent.count);
            slotMachineMatchedComponent.tileType = null;
            slotMachineMatchedComponent.count = 0;
            getEngine().removeEntity(entity);
        }

    }

    private void applyCoins(int size) {
        Entity attacker = getEngine().getEntitiesFor(attackerFamily).first();
        CoinComponent coinComponent = coinMapper.get(attacker);
        coinComponent.value += size;
    }
}
