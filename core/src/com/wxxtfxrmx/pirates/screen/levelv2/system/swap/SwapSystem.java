package com.wxxtfxrmx.pirates.screen.levelv2.system.swap;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector3;
import com.wxxtfxrmx.pirates.screen.level.board.TileState;
import com.wxxtfxrmx.pirates.screen.levelv2.component.BoundsComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.component.MoveComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.component.move.MoveForwardComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.component.TileStateComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.component.TileTypeComponent;

public abstract class SwapSystem extends IteratingSystem {

    private final ComponentMapper<TileStateComponent> state = ComponentMapper.getFor(TileStateComponent.class);
    private final ComponentMapper<BoundsComponent> bounds = ComponentMapper.getFor(BoundsComponent.class);
    private final ComponentMapper<TileTypeComponent> type = ComponentMapper.getFor(TileTypeComponent.class);

    public SwapSystem() {
        super(Family.all(
                TileStateComponent.class,
                MoveComponent.class,
                BoundsComponent.class,
                TileTypeComponent.class
        ).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TileStateComponent stateComponent = state.get(entity);
        MoveComponent move = entity.getComponent(MoveComponent.class);

        if (move.isMoving) return;
        if (stateComponent.state == TileState.IDLE) return;

        Entity acceptableEntity = getAcceptableEntity(entity);

        if (acceptableEntity == null) return;

        BoundsComponent currentBounds = this.bounds.get(entity);
        BoundsComponent acceptableBounds = this.bounds.get(acceptableEntity);

        if (!isInAcceptableBounds(currentBounds, acceptableBounds)) {
            entity.getComponent(TileStateComponent.class).state = TileState.IDLE;
            acceptableEntity.getComponent(TileStateComponent.class).state = TileState.IDLE;
        } else {

        }
    }

    private Entity getAcceptableEntity(Entity current) {
        for (Entity entity : getEntities()) {
            if (entity == current) continue;

            TileStateComponent stateComponent = state.get(entity);

            if (stateComponent.state == TileState.PICKED) return entity;
        }

        return null;
    }

    abstract boolean isInAcceptableBounds(BoundsComponent current, BoundsComponent acceptable);
}
