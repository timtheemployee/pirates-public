package com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.CollectedTilesComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.CurrentTurnComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.HpComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.board.world.TileType;

public class ApplyRepairSystem extends IteratingSystem {

    private final ComponentMapper<CollectedTilesComponent> collectedTilesMapper = ComponentMapper.getFor(CollectedTilesComponent.class);
    private final ComponentMapper<HpComponent> hpMapper = ComponentMapper.getFor(HpComponent.class);

    private final Family attackerFamily = Family.all(HpComponent.class, CurrentTurnComponent.class).get();

    public ApplyRepairSystem() {
        super(Family.all(CollectedTilesComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        CollectedTilesComponent collectedTilesComponent = collectedTilesMapper.get(entity);

        if (collectedTilesComponent.type != TileType.REPAIR) return;

        Entity attacker = getEngine().getEntitiesFor(attackerFamily).first();
        HpComponent hpComponent = hpMapper.get(attacker);

        if (hpComponent.value + collectedTilesComponent.size > hpComponent.maxValue) {
            hpComponent.value += hpComponent.maxValue - hpComponent.value;
        } else {
            hpComponent.value += collectedTilesComponent.size;
        }
    }
}
