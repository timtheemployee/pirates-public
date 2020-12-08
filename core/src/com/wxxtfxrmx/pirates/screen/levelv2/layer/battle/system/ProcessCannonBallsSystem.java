package com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.wxxtfxrmx.pirates.screen.levelv2.Constants;
import com.wxxtfxrmx.pirates.screen.levelv2.component.TextureComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.AiComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.CannonBallComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.CannonBallDistributionComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.PlayerComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.ShipComponent;

import java.util.Random;

public class ProcessCannonBallsSystem extends IteratingSystem {

    private final static float MAX_CANNON_SHOOT_DELTA = 0.3f;

    private final static Vector2 MIN_VELOCITY = new Vector2(0.1f, 0.0f);
    private final static Vector2 MAX_VELOCITY = new Vector2(0.3f, 0.0f);
    private final Random random = new Random();

    private final ComponentMapper<CannonBallDistributionComponent> cannonballsMapper = ComponentMapper.getFor(CannonBallDistributionComponent.class);
    private final ComponentMapper<PlayerComponent> playerMapper = ComponentMapper.getFor(PlayerComponent.class);
    private final ComponentMapper<ShipComponent> shipMapper = ComponentMapper.getFor(ShipComponent.class);
    private final TextureRegion bombTexture;
    private final PooledEngine engine;

    private final Family aiFamily = Family.all(ShipComponent.class, AiComponent.class).get();
    private final Family playerFamily = Family.all(ShipComponent.class, PlayerComponent.class).get();

    public ProcessCannonBallsSystem(TextureRegion bombTexture, PooledEngine engine) {
        super(Family
                .all(CannonBallDistributionComponent.class, ShipComponent.class)
                .one(PlayerComponent.class, AiComponent.class)
                .get()
        );
        this.bombTexture = bombTexture;
        this.engine = engine;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        CannonBallDistributionComponent cannonBallDistributionComponent = cannonballsMapper.get(entity);
        if (cannonBallDistributionComponent.lastCannonballDelta <= MAX_CANNON_SHOOT_DELTA) {
            cannonBallDistributionComponent.lastCannonballDelta += deltaTime;
            return;
        }

        cannonBallDistributionComponent.lastCannonballDelta = 0f;

        ShipComponent shipComponent = shipMapper.get(entity);

        Entity opposite;
        if (playerMapper.has(entity)) {
            opposite = getEngine().getEntitiesFor(aiFamily).first();
        } else {
            opposite = getEngine().getEntitiesFor(playerFamily).first();
        }

        ShipComponent oppositeShipComponent = shipMapper.get(opposite);
        Vector2 startPosition = new Vector2();
        startPosition = shipComponent.reference.getBounds().getPosition(startPosition);

        Vector2 endPosition = new Vector2();
        endPosition = oppositeShipComponent.reference.getBounds().getPosition(endPosition);

        if (cannonBallDistributionComponent.hit != 0) {
            Entity hitEntity = engine.createEntity();
            CannonBallComponent hitComponent = engine.createComponent(CannonBallComponent.class);
            hitComponent.startPoint = startPosition;
            hitComponent.currentPoint = startPosition;
            hitComponent.hitPoint = endPosition;
            setVelocity(hitComponent);

            TextureComponent texture = engine.createComponent(TextureComponent.class);
            texture.region = bombTexture;

            hitEntity.add(hitComponent);
            hitEntity.add(texture);
            engine.addEntity(hitEntity);
            cannonBallDistributionComponent.hit -= 1;
        }

        if (cannonBallDistributionComponent.miss != 0) {
            Entity hitEntity = engine.createEntity();
            CannonBallComponent hitComponent = engine.createComponent(CannonBallComponent.class);
            hitComponent.startPoint = startPosition;
            hitComponent.currentPoint = startPosition;
            setVelocity(hitComponent);
            //TODO REFACTOR IT
            hitComponent.hitPoint = new Vector2(Constants.WIDTH * 0.5f * Constants.UNIT,
                    Constants.MIDDLE_ROUNDED_HEIGHT * Constants.UNIT);

            TextureComponent texture = engine.createComponent(TextureComponent.class);
            texture.region = bombTexture;

            hitEntity.add(hitComponent);
            hitEntity.add(texture);
            engine.addEntity(hitEntity);
            cannonBallDistributionComponent.miss -= 1;
        }

        if (cannonBallDistributionComponent.hit == 0 && cannonBallDistributionComponent.miss == 0) {
            entity.remove(CannonBallDistributionComponent.class);
        }
    }

    private boolean isMaxVelocity() {
        return random.nextBoolean();
    }

    private void setVelocity(CannonBallComponent cannonBallComponent) {
        cannonBallComponent.velocity = isMaxVelocity() ? MAX_VELOCITY : MIN_VELOCITY;
    }
}
