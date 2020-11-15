package com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.wxxtfxrmx.pirates.screen.levelv2.Constants;
import com.wxxtfxrmx.pirates.screen.levelv2.component.BoundsComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.LastTouchedTileComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.board.component.TilePickedComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.board.component.TileTypeComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.board.component.TouchChainComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.board.world.TileType;

public class ContinueRandomChainSystem extends IteratingSystem {

    private final Family untouchedTiles = Family
            .all(
                    TileTypeComponent.class,
                    BoundsComponent.class
            )
            .exclude(TilePickedComponent.class, LastTouchedTileComponent.class)
            .get();

    private final Family touchChainFamily = Family.all(TouchChainComponent.class).get();

    private final ComponentMapper<TileTypeComponent> typeMapper = ComponentMapper.getFor(TileTypeComponent.class);
    private final ComponentMapper<BoundsComponent> boundsMapper = ComponentMapper.getFor(BoundsComponent.class);
    private final ComponentMapper<TouchChainComponent> chainMapper = ComponentMapper.getFor(TouchChainComponent.class);
    private final PooledEngine engine;

    public ContinueRandomChainSystem(PooledEngine engine) {
        super(Family.all(
                LastTouchedTileComponent.class,
                BoundsComponent.class,
                TileTypeComponent.class
        ).get());
        this.engine = engine;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        BoundsComponent boundsComponent = boundsMapper.get(entity);
        Rectangle searchAreaBounds = new Rectangle();
        //Because Rectangle#contains method not works on borderlines rectangles
        searchAreaBounds.setPosition(boundsComponent.bounds.x - Constants.UNIT + 1, boundsComponent.bounds.y - Constants.UNIT - 1);
        searchAreaBounds.setSize(Constants.UNIT * 3 + 1);

        ImmutableArray<Entity> untouchedEntities = getEngine().getEntitiesFor(untouchedTiles);
        Array<Entity> filtered = filterByArea(untouchedEntities, searchAreaBounds);

        TileTypeComponent typeComponent = typeMapper.get(entity);

        Entity candidate = firstSameTypeEntity(filtered, typeComponent.type);

        Entity touchChainEntity = getEngine().getEntitiesFor(touchChainFamily).first();
        TouchChainComponent touchChainComponent = chainMapper.get(touchChainEntity);

        entity.remove(LastTouchedTileComponent.class);

        if (candidate == null) {
            touchChainComponent.chain.clear();
            getEngine().removeEntity(touchChainEntity);
        } else {
            LastTouchedTileComponent lastTouchedTileComponent = engine.createComponent(LastTouchedTileComponent.class);
            candidate.add(lastTouchedTileComponent);

            BoundsComponent candidateBounds = boundsMapper.get(candidate);
            Vector2 position = new Vector2(
                    candidateBounds.bounds.x + candidateBounds.bounds.width / 2,
                    candidateBounds.bounds.y + candidateBounds.bounds.height / 2
            );

            touchChainComponent.chain.add(position);
        }
    }

    private Array<Entity> filterByArea(ImmutableArray<Entity> untouchedEntities,
                                       Rectangle searchArea) {

        Array<Entity> filtered = new Array<Entity>();

        for (Entity entity: untouchedEntities) {
            BoundsComponent boundsComponent = boundsMapper.get(entity);
            if (searchArea.contains(boundsComponent.bounds)) {
                filtered.add(entity);
            }
        }

        return filtered;
    }

    private Entity firstSameTypeEntity(Array<Entity> filtered, TileType type) {
        for (Entity entity : filtered) {
            TileTypeComponent typeComponent = typeMapper.get(entity);
            if (typeComponent.type == type) {
                return entity;
            }
        }

        return null;
    }
}
