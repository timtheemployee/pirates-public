package com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.component.TextureComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.CannonBallComponent;

public class MoveCannonBallSystem extends IteratingSystem {

    private final ComponentMapper<CannonBallComponent> cannonBallMapper = ComponentMapper.getFor(CannonBallComponent.class);

    public MoveCannonBallSystem() {
        super(Family.all(CannonBallComponent.class, TextureComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        CannonBallComponent cannonBallComponent = cannonBallMapper.get(entity);

        if (isPositiveDirection(cannonBallComponent)) {
            cannonBallComponent.currentPoint.add(1f, 0f);
            if (isOppositeDirection(cannonBallComponent)) {
                dispose(entity, cannonBallComponent);
            }

        } else if (isOppositeDirection(cannonBallComponent)) {
            cannonBallComponent.currentPoint.sub(1f, 0f);
            if (isPositiveDirection(cannonBallComponent)) {
                dispose(entity, cannonBallComponent);
            }

        } else {
            dispose(entity, cannonBallComponent);
        }
    }

    private void dispose(Entity entity, CannonBallComponent cannonBallComponent) {
        cannonBallComponent.currentPoint = null;
        cannonBallComponent.startPoint = null;
        entity.remove(CannonBallComponent.class);
        entity.remove(TextureComponent.class);
    }

    private boolean isPositiveDirection(CannonBallComponent cannonBallComponent) {
        return Math.signum(cannonBallComponent.hitPoint.x - cannonBallComponent.currentPoint.x) == 1;
    }

    private boolean isOppositeDirection(CannonBallComponent cannonBallComponent) {
        return Math.signum(cannonBallComponent.hitPoint.x - cannonBallComponent.currentPoint.x) == -1;
    }
}
