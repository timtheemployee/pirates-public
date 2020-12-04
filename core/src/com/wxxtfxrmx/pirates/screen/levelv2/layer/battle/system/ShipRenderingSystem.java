package com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.wxxtfxrmx.pirates.screen.levelv2.component.BoundsComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.ShipPartComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.world.ShipPart;

public class ShipRenderingSystem extends IteratingSystem {

    private final ComponentMapper<ShipPartComponent> shipPartMapper = ComponentMapper.getFor(ShipPartComponent.class);
    private final ComponentMapper<BoundsComponent> boundsMapper = ComponentMapper.getFor(BoundsComponent.class);

    private final SpriteBatch batch;

    public ShipRenderingSystem(SpriteBatch batch) {
        super(Family.all(BoundsComponent.class, ShipPartComponent.class).get());
        this.batch = batch;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        BoundsComponent boundsComponent = boundsMapper.get(entity);
        ShipPartComponent shipPartComponent = shipPartMapper.get(entity);

        batch.begin();
        for (ShipPart part: shipPartComponent.parts) {
            batch.draw(
                    part.texture,
                    boundsComponent.bounds.x + part.offset.getX(),
                    boundsComponent.bounds.y + part.offset.getY()
            );
        }
        batch.end();
    }
}
