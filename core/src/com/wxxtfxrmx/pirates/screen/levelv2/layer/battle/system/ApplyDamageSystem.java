package com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.CollectedTilesComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.CurrentTurnComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.DamageComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.HpComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.board.world.TileType;

public class ApplyDamageSystem extends IteratingSystem {

    private final ComponentMapper<CollectedTilesComponent> collectedTilesMapper = ComponentMapper.getFor(CollectedTilesComponent.class);
    private final ComponentMapper<HpComponent> hpMapper = ComponentMapper.getFor(HpComponent.class);
    private final ComponentMapper<DamageComponent> damageMapper = ComponentMapper.getFor(DamageComponent.class);

    private final Family attackerFamily = Family.all(DamageComponent.class, CurrentTurnComponent.class).get();
    private final Family defenderFamily = Family.all(HpComponent.class).exclude(CurrentTurnComponent.class).get();

    public ApplyDamageSystem() {
        super(Family.all(CollectedTilesComponent.class).get());
    }

    //TODO Apply evasion to damage
    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        CollectedTilesComponent collectedTilesComponent = collectedTilesMapper.get(entity);

        if (collectedTilesComponent.type != TileType.BOMB) return;

        Entity attacker = getEngine().getEntitiesFor(attackerFamily).first();
        Entity defender = getEngine().getEntitiesFor(defenderFamily).first();

        HpComponent defenderHp = hpMapper.get(defender);

        DamageComponent attackerDamage = damageMapper.get(attacker);

        defenderHp.value -= attackerDamage.value * collectedTilesComponent.size;
    }
}
