package com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.wxxtfxrmx.pirates.screen.levelv2.component.BoundsComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.component.RotationComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.component.ScaleComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.component.TextureComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.component.TimerComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.CollectedTilesComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.CurrentTurnComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.HpComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.RepairHammerDirectionComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.TextureSkeletonComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.world.ShipTexture;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.world.ShipTextureLoader;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.board.world.TileType;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.ui.component.SlotMachineMatchedComponent;

import java.util.Random;

public class ApplyRepairSystem extends IteratingSystem {

    private final ComponentMapper<CollectedTilesComponent> collectedTilesMapper = ComponentMapper.getFor(CollectedTilesComponent.class);
    private final ComponentMapper<SlotMachineMatchedComponent> slotMachineMapper = ComponentMapper.getFor(SlotMachineMatchedComponent.class);
    private final ComponentMapper<HpComponent> hpMapper = ComponentMapper.getFor(HpComponent.class);
    private final ComponentMapper<TextureSkeletonComponent> skeletonMapper = ComponentMapper.getFor(TextureSkeletonComponent.class);

    private final Family attackerFamily = Family.all(HpComponent.class, TextureSkeletonComponent.class, CurrentTurnComponent.class).get();

    private final ShipTextureLoader loader;
    private final Random random = new Random();

    private final PooledEngine engine;

    public ApplyRepairSystem(ShipTextureLoader loader, PooledEngine engine) {
        super(Family.one(CollectedTilesComponent.class, SlotMachineMatchedComponent.class).get());
        this.loader = loader;
        this.engine = engine;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        if (collectedTilesMapper.has(entity)) {
            CollectedTilesComponent collectedTilesComponent = collectedTilesMapper.get(entity);
            if (collectedTilesComponent.type != TileType.REPAIR) return;
            applyRepair(collectedTilesComponent.size);
        } else if (slotMachineMapper.has(entity)) {
            SlotMachineMatchedComponent slotMachineMatchedComponent = slotMachineMapper.get(entity);
            if (slotMachineMatchedComponent.tileType != TileType.REPAIR) return;
            applyRepair(slotMachineMatchedComponent.count);
            slotMachineMatchedComponent.tileType = null;
            slotMachineMatchedComponent.count = 0;
            getEngine().removeEntity(entity);
        }
    }

    private void applyRepair(int count) {
        Entity attacker = getEngine().getEntitiesFor(attackerFamily).first();
        HpComponent hpComponent = hpMapper.get(attacker);

        if (hpComponent.value + count > hpComponent.maxValue) {
            hpComponent.value += hpComponent.maxValue - hpComponent.value;
        } else {
            hpComponent.value += count;
        }

        applyRepairAnimation(attacker);
    }

    private void applyRepairAnimation(Entity entity) {
        TextureSkeletonComponent skeletonComponent = skeletonMapper.get(entity);
        Rectangle bounds = skeletonComponent.skeleton.getBounds();

        for (int i = 0; i < 3; i++) {
            Entity animationEntity = engine.createEntity();
            TimerComponent timerComponent = engine.createComponent(TimerComponent.class);
            timerComponent.current = 0f;
            timerComponent.target = 0.3f;

            boolean isOnStart = random.nextBoolean();
            TextureRegion hammerTexture = loader.load(ShipTexture.REPAIR_HAMMER, !isOnStart);
            TextureComponent textureComponent = engine.createComponent(TextureComponent.class);
            textureComponent.region = hammerTexture;

            BoundsComponent boundsComponent = engine.createComponent(BoundsComponent.class);
            boundsComponent.bounds = new Rectangle();
            //NOTE: Make hammer not on corner
            float hammerX = getRandomInRange(bounds.x, bounds.x + bounds.width);
            boundsComponent.bounds.setX(hammerX);
            float randomY = getRandomInRange(bounds.y, bounds.y + bounds.height);
            boundsComponent.bounds.setY(randomY);
            boundsComponent.bounds.setSize(hammerTexture.getRegionWidth(), hammerTexture.getRegionHeight());

            ScaleComponent scaleComponent = engine.createComponent(ScaleComponent.class);
            scaleComponent.scale = new Vector2(1f, 1f);

            RotationComponent rotationComponent = engine.createComponent(RotationComponent.class);
            rotationComponent.angle = 0;

            RepairHammerDirectionComponent directionComponent = engine.createComponent(RepairHammerDirectionComponent.class);
            directionComponent.isStraight = isOnStart;

            animationEntity.add(textureComponent);
            animationEntity.add(boundsComponent);
            animationEntity.add(scaleComponent);
            animationEntity.add(timerComponent);
            animationEntity.add(rotationComponent);
            animationEntity.add(directionComponent);

            engine.addEntity(animationEntity);
        }
    }

    private float getRandomInRange(float min, float max) {
        return min + random.nextFloat() * (max - min);
    }
}
