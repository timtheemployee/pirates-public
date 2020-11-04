package com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IteratingSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.HpComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.PlayerComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.PlayerLoseComponent;

public class ValidatePlayerHpSystem extends IteratingSystem {

    private final ComponentMapper<HpComponent> hpMapper = ComponentMapper.getFor(HpComponent.class);
    private final PooledEngine engine;

    public ValidatePlayerHpSystem(PooledEngine engine) {
        super(Family.all(
                HpComponent.class,
                PlayerComponent.class
        ).get());
        this.engine = engine;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        HpComponent hpComponent = hpMapper.get(entity);

        if (hpComponent.value <= 0) {
            Entity playerLoseEntity = engine.createEntity();
            PlayerLoseComponent playerLoseComponent = engine.createComponent(PlayerLoseComponent.class);
            playerLoseEntity.add(playerLoseComponent);

            engine.addEntity(playerLoseEntity);
        }
    }
}
