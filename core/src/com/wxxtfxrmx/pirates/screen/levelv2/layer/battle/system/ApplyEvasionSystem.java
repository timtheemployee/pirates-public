package com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.CollectedTilesComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.CurrentTurnComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.EvasionComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.board.world.TileType;

public class ApplyEvasionSystem extends IteratingSystem {

    private final ComponentMapper<CollectedTilesComponent> collectedTilesMapper = ComponentMapper.getFor(CollectedTilesComponent.class);
    private final ComponentMapper<EvasionComponent> evasionMapper = ComponentMapper.getFor(EvasionComponent.class);


    private final Family attackerFamily = Family.all(
            EvasionComponent.class,
            CurrentTurnComponent.class
    ).get();

    public ApplyEvasionSystem() {
        super(Family.all(CollectedTilesComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        CollectedTilesComponent collectedTiles = collectedTilesMapper.get(entity);

        if (collectedTiles.type != TileType.HELM) return;

        Entity attacker = getEngine().getEntitiesFor(attackerFamily).first();

        EvasionComponent evasion = evasionMapper.get(attacker);
        evasion.percent = 0.1f * collectedTiles.size;
    }
}
