package com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import com.wxxtfxrmx.pirates.screen.levelv2.component.BoundsComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.component.DestinationComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.AiComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.CurrentTurnComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.EvasionComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.HpComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.LastTouchedTileComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.board.component.TilePickedComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.board.component.TileTypeComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.board.component.TouchChainComponent;

public class TouchRandomTileSystem extends IteratingSystem {

    private final Family tileFamily = Family.all(TileTypeComponent.class, BoundsComponent.class).get();
    private final Family pickedTilesFamily = Family.all(TileTypeComponent.class, BoundsComponent.class, TilePickedComponent.class).get();
    private final Family movingTilesFamily = Family.all(DestinationComponent.class).get();
    private final ComponentMapper<BoundsComponent> boundsMapper = ComponentMapper.getFor(BoundsComponent.class);
    private final PooledEngine engine;

    public TouchRandomTileSystem(PooledEngine engine) {
        super(Family.all(
                CurrentTurnComponent.class,
                AiComponent.class,
                HpComponent.class,
                EvasionComponent.class
        ).get());

        this.engine = engine;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        if (getEngine().getEntitiesFor(movingTilesFamily).size() != 0) return;

        ImmutableArray<Entity> pickedTiles = getEngine().getEntitiesFor(pickedTilesFamily);

        if (pickedTiles.size() == 0) {
            ImmutableArray<Entity> allTiles = getEngine().getEntitiesFor(tileFamily);

            Entity randomTile = allTiles.random();
            TouchChainComponent chainComponent = engine.createComponent(TouchChainComponent.class);
            BoundsComponent randomBounds = boundsMapper.get(randomTile);

            Vector2 touch = new Vector2(
                    randomBounds.bounds.x + randomBounds.bounds.width / 2,
                    randomBounds.bounds.y + randomBounds.bounds.height / 2
            );

            chainComponent.chain.add(touch);

            Entity touchEntity = engine.createEntity();
            touchEntity.add(chainComponent);
            engine.addEntity(touchEntity);

            LastTouchedTileComponent lastTouchedTileComponent = engine.createComponent(LastTouchedTileComponent.class);
            randomTile.add(lastTouchedTileComponent);
        }
    }
}
