package com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.ShipComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.ShipStateComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.world.ShipState;

import java.util.Random;

public class ShipIdleAnimationSystem extends IteratingSystem {

    private final static float IDLE_DELAY = 0.5f;
    private float currentDelay = 0f;

    private final ComponentMapper<ShipComponent> shipMapper = ComponentMapper.getFor(ShipComponent.class);
    private final ComponentMapper<ShipStateComponent> stateMapper = ComponentMapper.getFor(ShipStateComponent.class);

    private final Random random = new Random();

    public ShipIdleAnimationSystem() {
        super(Family.all(ShipComponent.class, ShipStateComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        if (currentDelay <= IDLE_DELAY) {
            currentDelay += deltaTime;
            return;
        }

        currentDelay = 0;

        ShipComponent shipComponent = shipMapper.get(entity);
        ShipStateComponent stateComponent = stateMapper.get(entity);

        if (random.nextBoolean()) {
            if (stateComponent.state == ShipState.IDLE_TOP) {
                stateComponent.state = ShipState.IDLE_BOTTOM;
                shipComponent.reference.translateY(-8);
            } else {
                stateComponent.state = ShipState.IDLE_TOP;
                shipComponent.reference.translateY(8);
            }
        }
    }
}
