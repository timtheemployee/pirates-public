package com.wxxtfxrmx.pirates.screen.levelv2.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector3;
import com.wxxtfxrmx.pirates.screen.levelv2.Constants;
import com.wxxtfxrmx.pirates.screen.levelv2.component.BoundsComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.component.ScaleComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.component.SpawnComponent;

public class DropDownSystem extends IteratingSystem {

    private final ComponentMapper<BoundsComponent> bounds = ComponentMapper.getFor(BoundsComponent.class);
    private final ComponentMapper<SpawnComponent> spawn = ComponentMapper.getFor(SpawnComponent.class);
    private final ComponentMapper<ScaleComponent> scale = ComponentMapper.getFor(ScaleComponent.class);

    public DropDownSystem() {
        super(Family.all(
                BoundsComponent.class,
                SpawnComponent.class,
                ScaleComponent.class).get());
    }

    // TODO Maybe swap hidden entity with visible
    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        if (isOnBottomBorder(entity)) return;

        BoundsComponent boundsComponent = this.bounds.get(entity);
        SpawnComponent targetSpawnComponent = this.spawn.get(entity);
        Entity bottomSpawned = findBottomSpawnPoint(boundsComponent.bounds.x, boundsComponent.bounds.y);

        if (bottomSpawned != null && isRemoved(bottomSpawned)) {
            SpawnComponent spawnComponent = this.spawn.get(bottomSpawned);
            Vector3 spawnPoint = spawnComponent.spawn;
            boundsComponent.bounds.x = spawnPoint.x;
            boundsComponent.bounds.y = spawnPoint.y;

            spawnComponent.spawn = targetSpawnComponent.spawn;
            targetSpawnComponent.spawn = spawnPoint;
        }
    }

    private boolean isRemoved(Entity entity) {
        ScaleComponent scaleComponent = this.scale.get(entity);

        return scaleComponent.scale.x == 0f && scaleComponent.scale.y == 0f;
    }

    private Entity findBottomSpawnPoint(float x, float y) {
        float bottomY = y - Constants.UNIT;
        ImmutableArray<Entity> entities = getEntities();

        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            SpawnComponent spawnComponent = this.spawn.get(entity);

            if (spawnComponent.spawn.y == bottomY && spawnComponent.spawn.x == x) {
                return entity;
            }
        }

        return null;
    }

    private boolean isOnBottomBorder(Entity entity) {
        BoundsComponent boundsComponent = bounds.get(entity);

        return boundsComponent.bounds.y == 0;
    }
}
