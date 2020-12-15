package com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.component.BoundsComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.component.RotationComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.component.ScaleComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.component.TextureComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.component.TimerComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.RepairHammerDirectionComponent;

public class HandleRepairHammerAnimationTimeSystem extends IteratingSystem {

    private final static float ANIMATION_STEP_THRESHOLD = 0.1f;
    private float currentThreshold = 0f;
    private final ComponentMapper<TimerComponent> timerMapper = ComponentMapper.getFor(TimerComponent.class);
    private final ComponentMapper<RotationComponent> rotationMapper = ComponentMapper.getFor(RotationComponent.class);
    private final ComponentMapper<RepairHammerDirectionComponent> directionMapper = ComponentMapper.getFor(RepairHammerDirectionComponent.class);

    public HandleRepairHammerAnimationTimeSystem() {
        super(Family.all(TimerComponent.class,
                BoundsComponent.class,
                TextureComponent.class,
                ScaleComponent.class,
                RotationComponent.class,
                RepairHammerDirectionComponent.class)
                .get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        if (currentThreshold < ANIMATION_STEP_THRESHOLD) {
            currentThreshold += deltaTime;
            return;
        }

        currentThreshold = 0;

        TimerComponent timerComponent = timerMapper.get(entity);
        if (timerComponent.current >= timerComponent.target) {
            entity.remove(TimerComponent.class);
            entity.remove(BoundsComponent.class);
            entity.remove(TextureComponent.class);
            entity.remove(ScaleComponent.class);
            entity.remove(RotationComponent.class);
            entity.remove(RepairHammerDirectionComponent.class);
            getEngine().removeEntity(entity);
            return;
        }

        RepairHammerDirectionComponent directionComponent = directionMapper.get(entity);
        RotationComponent rotationComponent = rotationMapper.get(entity);

        if (rotationComponent.angle != 0) {
            rotationComponent.angle = 0;
            return;
        }

        if (directionComponent.isStraight) {
            rotationComponent.angle = 90f;
        } else {
            rotationComponent.angle = -90f;
        }

        timerComponent.current += deltaTime;
    }
}
