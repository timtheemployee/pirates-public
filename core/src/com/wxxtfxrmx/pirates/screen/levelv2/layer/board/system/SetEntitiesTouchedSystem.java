package com.wxxtfxrmx.pirates.screen.levelv2.layer.board.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.wxxtfxrmx.pirates.screen.levelv2.Constants;
import com.wxxtfxrmx.pirates.screen.levelv2.component.BoundsComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.component.TilePickedComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.component.TileTypeComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.component.TouchChainComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.board.world.TileType;

public class SetEntitiesTouchedSystem extends EntitySystem {

    private final static float MAX_DIAGONAL_DIFFERENCE = Constants.UNIT * (float) Math.sqrt(2);
    private final static Family tilesFamily = Family.all(BoundsComponent.class, TileTypeComponent.class).get();
    private final static Family touchChainFamily = Family.all(TouchChainComponent.class).get();

    private final ComponentMapper<TouchChainComponent> chainTouchMapper = ComponentMapper.getFor(TouchChainComponent.class);
    private final ComponentMapper<BoundsComponent> boundsMapper = ComponentMapper.getFor(BoundsComponent.class);
    private final ComponentMapper<TileTypeComponent> typeMapper = ComponentMapper.getFor(TileTypeComponent.class);
    private final PooledEngine pooledEngine;

    public SetEntitiesTouchedSystem(PooledEngine pooledEngine) {
        this.pooledEngine = pooledEngine;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        Entity chain = getChainEntity();
        if (chain == null) return;

        TouchChainComponent touchChainComponent = chainTouchMapper.get(chain);
        Array<Vector2> touches = touchChainComponent.chain;

        TileType type = getPickedType();

        ImmutableArray<Entity> tiles = getEngine().getEntitiesFor(tilesFamily);
        BoundsComponent lastPickedBounds = null;
        for (Vector2 touch : touches) {
            for (Entity tile : tiles) {
                BoundsComponent bounds = boundsMapper.get(tile);
                TileTypeComponent typeComponent = typeMapper.get(tile);
                boolean sameComponents = type == null || typeComponent.type == type;
                boolean containsTouch = bounds.bounds.contains(touch);
                boolean isAvailableToMatch = isAvailableToMatch(lastPickedBounds, bounds);
                if (containsTouch && sameComponents && isAvailableToMatch) {
                    TilePickedComponent pickedComponent = pooledEngine.createComponent(TilePickedComponent.class);
                    tile.add(pickedComponent);
                    lastPickedBounds = bounds;
                }
            }
        }
    }

    private boolean isAvailableToMatch(BoundsComponent lastPickedBounds, BoundsComponent currentBounds) {
        if (lastPickedBounds == null) return true;
        Vector2 lastPicked = new Vector2();
        lastPickedBounds.bounds.getPosition(lastPicked);

        Vector2 currentPicked = new Vector2();
        currentBounds.bounds.getPosition(currentPicked);

        return currentPicked.dst(lastPicked) <= MAX_DIAGONAL_DIFFERENCE;
    }

    private TileType getPickedType() {
        ImmutableArray<Entity> picked = getEngine().getEntitiesFor(Family.all(TilePickedComponent.class).get());

        Entity first = picked.size() == 0 ? null : picked.first();

        if (first == null) return null;
        else {
            TileTypeComponent typeComponent = typeMapper.get(first);
            return typeComponent.type;
        }
    }

    private Entity getChainEntity() {
        ImmutableArray<Entity> entities = getEngine().getEntitiesFor(touchChainFamily);

        return entities.size() == 0 ? null : entities.first();
    }
}
