package com.wxxtfxrmx.pirates.screen.levelv2.system.match;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.utils.Array;
import com.wxxtfxrmx.pirates.screen.levelv2.component.BoundsComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.component.TileMatchComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.component.TileTypeComponent;

public abstract class MatchSystem extends EntitySystem {
    protected ImmutableArray<Entity> entities;

    protected final ComponentMapper<BoundsComponent> bounds = ComponentMapper.getFor(BoundsComponent.class);
    protected final ComponentMapper<TileMatchComponent> matched = ComponentMapper.getFor(TileMatchComponent.class);
    protected final ComponentMapper<TileTypeComponent> type = ComponentMapper.getFor(TileTypeComponent.class);

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(
                Family.all(BoundsComponent.class, TileMatchComponent.class, TileTypeComponent.class).get()
        );
    }

    @Override
    public void update(float deltaTime) {
        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            BoundsComponent boundsComponent = bounds.get(entity);
            float y = boundsComponent.bounds.y;
            float x = boundsComponent.bounds.x;

            if (isOnBorder(boundsComponent)) continue;

            Array<Entity> neighbors = findNeighbors(x, y, entities);

            if (isMatchable(entity, neighbors)) {
                for (Entity neighbor : neighbors) {
                    TileMatchComponent matchComponent = matched.get(neighbor);
                    matchComponent.matched = true;
                }
            }
        }
    }

    private boolean isMatchable(Entity entity, Array<Entity> neighbors) {
        TileTypeComponent typeComponent = type.get(entity);

        for (Entity neighbor : neighbors) {
            TileTypeComponent neighborTypeComponent = type.get(neighbor);

            if (typeComponent.type != neighborTypeComponent.type) return false;
        }

        return true;
    }

    abstract boolean isOnBorder(BoundsComponent component);

    abstract Array<Entity> findNeighbors(float x, float y, Iterable<Entity> entities);
}
