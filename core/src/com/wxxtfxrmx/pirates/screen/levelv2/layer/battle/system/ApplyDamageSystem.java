package com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IteratingSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.CannonBallDistributionComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.CollectedTilesComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.CurrentTurnComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.DamageComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.EvasionComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.HpComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.board.world.TileType;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.ui.component.SlotMachineMatchedComponent;

public class ApplyDamageSystem extends IteratingSystem {

    private final ComponentMapper<CollectedTilesComponent> collectedTilesMapper = ComponentMapper.getFor(CollectedTilesComponent.class);
    private final ComponentMapper<HpComponent> hpMapper = ComponentMapper.getFor(HpComponent.class);
    private final ComponentMapper<DamageComponent> damageMapper = ComponentMapper.getFor(DamageComponent.class);
    private final ComponentMapper<EvasionComponent> evasionMapper = ComponentMapper.getFor(EvasionComponent.class);
    private final ComponentMapper<SlotMachineMatchedComponent> slotMachineMapper = ComponentMapper.getFor(SlotMachineMatchedComponent.class);

    private final Family attackerFamily = Family.all(DamageComponent.class, CurrentTurnComponent.class).get();
    private final Family defenderFamily = Family.all(HpComponent.class, EvasionComponent.class).exclude(CurrentTurnComponent.class).get();

    private final PooledEngine engine;

    public ApplyDamageSystem(PooledEngine engine) {
        super(Family.one(CollectedTilesComponent.class, SlotMachineMatchedComponent.class).get());
        this.engine = engine;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        if (collectedTilesMapper.has(entity)) {
            CollectedTilesComponent collectedTiles = collectedTilesMapper.get(entity);
            if (collectedTiles.type != TileType.BOMB) return;

            applyDamage(collectedTiles.size);

        } else if (slotMachineMapper.has(entity)) {
            SlotMachineMatchedComponent slotMachineMatchedComponent = slotMachineMapper.get(entity);
            if (slotMachineMatchedComponent.tileType != TileType.BOMB) return;

            applyDamage(slotMachineMatchedComponent.count);
            slotMachineMatchedComponent.tileType = null;
            slotMachineMatchedComponent.count = 0;
            getEngine().removeEntity(entity);
        }
    }

    private void applyDamage(int cannonBallSize) {
        Entity attacker = getEngine().getEntitiesFor(attackerFamily).first();
        Entity defender = getEngine().getEntitiesFor(defenderFamily).first();

        HpComponent defenderHp = hpMapper.get(defender);
        EvasionComponent evasion = evasionMapper.get(defender);

        DamageComponent attackerDamage = damageMapper.get(attacker);

        int cannonBallsHit = cannonBallSize - (int) (cannonBallSize * evasion.percent);

        defenderHp.value -= attackerDamage.value * cannonBallsHit;

        CannonBallDistributionComponent cannonBallDistributionComponent = engine.createComponent(CannonBallDistributionComponent.class);
        cannonBallDistributionComponent.hit = cannonBallsHit;
        cannonBallDistributionComponent.miss = cannonBallSize - cannonBallsHit;
        attacker.add(cannonBallDistributionComponent);
    }
}
