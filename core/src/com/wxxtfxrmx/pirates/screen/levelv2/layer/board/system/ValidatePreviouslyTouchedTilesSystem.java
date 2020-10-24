package com.wxxtfxrmx.pirates.screen.levelv2.layer.board.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.wxxtfxrmx.pirates.screen.levelv2.component.BoundsComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.component.TilePickedComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.component.TouchChainComponent;

public class ValidatePreviouslyTouchedTilesSystem extends IteratingSystem {

    private final Family pickedTilesFamily = Family.all(TilePickedComponent.class).get();
    private final ComponentMapper<TouchChainComponent> chainMapper = ComponentMapper.getFor(TouchChainComponent.class);
    private final ComponentMapper<BoundsComponent> tileBoundsMapper = ComponentMapper.getFor(BoundsComponent.class);

    public ValidatePreviouslyTouchedTilesSystem() {
        super(Family.all(TouchChainComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TouchChainComponent chainComponent = chainMapper.get(entity);

        ImmutableArray<Entity> pickedEntities = getEngine().getEntitiesFor(pickedTilesFamily);

        pickedEntities.forEach((picked) -> {
            if (!isValid(picked, chainComponent.chain)) {
                picked.remove(TilePickedComponent.class);
            }
        });
    }

    private boolean isValid(Entity entity, Array<Vector2> touches) {
        BoundsComponent boundsComponent = tileBoundsMapper.get(entity);
        for (Vector2 touch : touches) {
            if (boundsComponent.bounds.contains(touch)) return true;
        }

        return false;
    }
}
