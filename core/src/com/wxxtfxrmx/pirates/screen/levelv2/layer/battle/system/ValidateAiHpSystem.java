package com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.AiComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.HpComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.ui.component.LevelContextComponent;

public class ValidateAiHpSystem extends IteratingSystem {

    private final ComponentMapper<HpComponent> hpMapper = ComponentMapper.getFor(HpComponent.class);
    private final Family levelContextFamily = Family.all(LevelContextComponent.class).get();
    private final ComponentMapper<LevelContextComponent> levelContextMapper = ComponentMapper.getFor(LevelContextComponent.class);

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
            Entity levelContextEntity = getLevelContextEntity();
            LevelContextComponent levelContext = levelContextMapper.get(levelContextEntity);
            levelContext.shipDestroyed += 1;
            hpComponent.value = hpComponent.maxValue;
        }
    }

    private Entity getLevelContextEntity() {

        return getEngine().getEntitiesFor(levelContextFamily).first();
    }
}
