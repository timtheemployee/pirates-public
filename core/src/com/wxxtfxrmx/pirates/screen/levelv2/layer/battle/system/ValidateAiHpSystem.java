package com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.AiComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.HpComponent;

public class ValidateAiHpSystem extends IteratingSystem {

    private final ComponentMapper<HpComponent> hpMapper = ComponentMapper.getFor(HpComponent.class);

    public ValidateAiHpSystem() {
        super(Family.all(
                HpComponent.class,
                AiComponent.class
        ).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        HpComponent hpComponent = hpMapper.get(entity);

        if (hpComponent.value <= 0) {
            hpComponent.value = hpComponent.maxValue;
        }
    }
}
