package com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.CollectedTilesComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.CurrentTurnComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.HpComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.board.world.TileType;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.ui.component.SlotMachineMatchedComponent;

public class ApplyRepairSystem extends IteratingSystem {

    private final ComponentMapper<CollectedTilesComponent> collectedTilesMapper = ComponentMapper.getFor(CollectedTilesComponent.class);
    private final ComponentMapper<SlotMachineMatchedComponent> slotMachineMapper = ComponentMapper.getFor(SlotMachineMatchedComponent.class);
    private final ComponentMapper<HpComponent> hpMapper = ComponentMapper.getFor(HpComponent.class);

    private final Family attackerFamily = Family.all(HpComponent.class, CurrentTurnComponent.class).get();

    public ApplyRepairSystem() {
        super(Family.one(CollectedTilesComponent.class, SlotMachineMatchedComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        if (collectedTilesMapper.has(entity)) {
            CollectedTilesComponent collectedTilesComponent = collectedTilesMapper.get(entity);
            if (collectedTilesComponent.type != TileType.REPAIR) return;
            applyRepair(collectedTilesComponent.size);
        } else if (slotMachineMapper.has(entity)) {
            SlotMachineMatchedComponent slotMachineMatchedComponent = slotMachineMapper.get(entity);
            if (slotMachineMatchedComponent.tileType != TileType.REPAIR) return;
            applyRepair(slotMachineMatchedComponent.count);
            slotMachineMatchedComponent.tileType = null;
            slotMachineMatchedComponent.count = 0;
            getEngine().removeEntity(entity);
        }
    }

    private void applyRepair(int count) {
        Entity attacker = getEngine().getEntitiesFor(attackerFamily).first();
        HpComponent hpComponent = hpMapper.get(attacker);

        if (hpComponent.value + count > hpComponent.maxValue) {
            hpComponent.value += hpComponent.maxValue - hpComponent.value;
        } else {
            hpComponent.value += count;
        }
    }
}
