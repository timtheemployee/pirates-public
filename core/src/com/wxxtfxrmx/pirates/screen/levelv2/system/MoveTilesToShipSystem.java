package com.wxxtfxrmx.pirates.screen.levelv2.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Rectangle;
import com.wxxtfxrmx.pirates.screen.levelv2.Constants;
import com.wxxtfxrmx.pirates.screen.levelv2.component.BoundsComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.component.ScaleComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.component.TileMatchComponent;

public class MoveTilesToShipSystem extends IteratingSystem {

    private final float xVelocity = 10f;
    private final float yVelocity = 10f;

    private final static float END_X = (Constants.WIDTH - 1) * Constants.UNIT;
    private final static float END_Y = (Constants.MIDDLE_ROUNDED_HEIGHT + 1) * Constants.UNIT;

    private final ComponentMapper<BoundsComponent> bounds = ComponentMapper.getFor(BoundsComponent.class);
    private final ComponentMapper<TileMatchComponent> match = ComponentMapper.getFor(TileMatchComponent.class);
    private final ComponentMapper<ScaleComponent> scale = ComponentMapper.getFor(ScaleComponent.class);

    public MoveTilesToShipSystem() {
        super(Family.all(BoundsComponent.class, TileMatchComponent.class, ScaleComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TileMatchComponent match = this.match.get(entity);

        if (!match.matched) return;

        BoundsComponent boundsComponent = this.bounds.get(entity);
        boundsComponent.z = -1f;
        Rectangle bounds = boundsComponent.bounds;

        if (bounds.y <= END_Y) {
            bounds.y += yVelocity;
        }

        if (bounds.x <= END_X) {
            bounds.x += xVelocity;
        }

        if (bounds.y >= END_Y && bounds.x >= END_X) {
            match.matched = false;
            ScaleComponent scaleComponent = this.scale.get(entity);
            scaleComponent.scale.x = 0f;
            scaleComponent.scale.y = 0f;
        }
    }
}
