package com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.wxxtfxrmx.pirates.screen.levelv2.Constants;
import com.wxxtfxrmx.pirates.screen.levelv2.component.BoundsComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.component.DestinationComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.component.ScaleComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.component.TextureComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.ShotDistributionComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.world.ShipTexture;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.world.ShipTextureLoader;

import java.util.Random;

public class ProcessShotDistributionSystem extends IteratingSystem {

    private final ComponentMapper<ShotDistributionComponent> shotDistributionMapper = ComponentMapper.getFor(ShotDistributionComponent.class);
    private final Random random = new Random();
    private final PooledEngine engine;
    private final ShipTextureLoader shipTextureLoader;

    public ProcessShotDistributionSystem(PooledEngine engine, ShipTextureLoader shipTextureLoader) {
        super(Family.all(ShotDistributionComponent.class).get());
        this.engine = engine;
        this.shipTextureLoader = shipTextureLoader;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        ShotDistributionComponent shotDistributionComponent = shotDistributionMapper.get(entity);
        Vector2 toTop = shotDistributionComponent.toTop;
        Vector2 toBottom = shotDistributionComponent.toBottom;
        Vector2 from = shotDistributionComponent.from;

        TextureRegion bomb = shipTextureLoader.load(ShipTexture.BOMB, false);

        for (int i = 0; i < shotDistributionComponent.hit; i++) {
            Entity hitEntity = createBombEntity(engine, from, bomb);

            float hitY = getRandomInRange(toBottom.y, toTop.y);
            float hitX = toBottom.x;

            DestinationComponent destinationComponent = engine.createComponent(DestinationComponent.class);
            destinationComponent.destination = new Vector2(hitX, hitY);
            hitEntity.add(destinationComponent);

            engine.addEntity(hitEntity);
        }

        float direction = Math.signum(toTop.x - from.x);
        float hitX;

        if (direction == 1) {
            hitX = Constants.WIDTH * Constants.UNIT;
        } else if (direction == -1) {
            hitX = 9;
        } else {
            throw new IllegalArgumentException("Are ships on the same positions?");
        }

        for (int i = 0; i < shotDistributionComponent.miss; i++) {
            Entity missEntity = createBombEntity(engine, from, bomb);

            float hitY = getRandomInRange(toBottom.y, toTop.y);

            DestinationComponent destinationComponent = engine.createComponent(DestinationComponent.class);
            destinationComponent.destination = new Vector2(hitX, hitY);
            missEntity.add(destinationComponent);

            engine.addEntity(missEntity);
        }

        shotDistributionComponent.miss = 0;
        shotDistributionComponent.hit = 0;
        entity.remove(ShotDistributionComponent.class);
        engine.removeEntity(entity);
    }

    private float getRandomInRange(float min, float max) {
        return min + random.nextFloat() * (max - min);
    }

    private Entity createBombEntity(PooledEngine engine, Vector2 from, TextureRegion bombTexture) {
        Entity entity = engine.createEntity();
        TextureComponent textureComponent = engine.createComponent(TextureComponent.class);
        textureComponent.region = bombTexture;
        ScaleComponent scaleComponent = engine.createComponent(ScaleComponent.class);
        scaleComponent.scale = new Vector2(1f, 1f);
        BoundsComponent boundsComponent = engine.createComponent(BoundsComponent.class);
        boundsComponent.bounds = new Rectangle(from.x, from.y, bombTexture.getRegionWidth(), bombTexture.getRegionHeight());
        boundsComponent.z = -1;

        entity.add(textureComponent);
        entity.add(scaleComponent);
        entity.add(boundsComponent);

        return entity;
    }
}
