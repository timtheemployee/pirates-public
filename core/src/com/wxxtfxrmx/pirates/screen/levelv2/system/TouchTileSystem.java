package com.wxxtfxrmx.pirates.screen.levelv2.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.wxxtfxrmx.pirates.screen.level.board.TileState;
import com.wxxtfxrmx.pirates.screen.levelv2.component.BoundsComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.component.TextureComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.component.TileStateComponent;

public class TouchTileSystem extends EntitySystem {

    private ImmutableArray<Entity> entities = null;

    private final Vector3 touch = new Vector3();
    private final ComponentMapper<BoundsComponent> bounds = ComponentMapper.getFor(BoundsComponent.class);
    private final ComponentMapper<TileStateComponent> state = ComponentMapper.getFor(TileStateComponent.class);
    private final OrthographicCamera camera;


    public TouchTileSystem(OrthographicCamera camera) {
        this.camera = camera;
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        entities = engine.getEntitiesFor(Family.all(
                BoundsComponent.class,
                TextureComponent.class
        ).get());
    }

    @Override
    public void update(float deltaTime) {
        if (!Gdx.input.justTouched() || entities == null) return;

        Array<Entity> picked = getPickedEntities();
        if (picked.size == 2) return;

        touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(touch);

        for (int i = 0; i < entities.size(); i++) {
            BoundsComponent boundsComponent = bounds.get(entities.get(i));

            if (boundsComponent.bounds.contains(touch.x, touch.y)) {
                changeState(entities.get(i));

                return;
            }
        }
    }

    private Array<Entity> getPickedEntities() {
        Array<Entity> picked = new Array<>();
        for (Entity entity : entities) {
            TileStateComponent stateComponent = state.get(entity);

            if (stateComponent.state == TileState.PICKED) {
                picked.add(entity);
            }
        }

        return picked;
    }

    private void changeState(Entity entity) {
        TileStateComponent stateComponent = state.get(entity);
        stateComponent.state = stateComponent.state == TileState.IDLE ? TileState.PICKED : TileState.IDLE;
    }
}
