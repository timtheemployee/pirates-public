package com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.ShipComponent;
import com.wxxtfxrmx.pirates.uikit.utils.Constraint;
import com.wxxtfxrmx.pirates.uikit.utils.Reference;

import java.util.List;

public class ShipRenderingSystem extends IteratingSystem {

    private final ComponentMapper<ShipComponent> shipPartMapper = ComponentMapper.getFor(ShipComponent.class);

    private final SpriteBatch batch;
    private final OrthographicCamera camera;

    public ShipRenderingSystem(SpriteBatch batch, OrthographicCamera camera) {
        super(Family.all(ShipComponent.class).get());
        this.batch = batch;
        this.camera = camera;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        ShipComponent shipComponent = shipPartMapper.get(entity);
        Reference reference = shipComponent.reference;
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        render(batch, reference.getTexture(), reference.getBounds());
        for (Constraint constraint: reference.getConstraints()) {
            render(batch, constraint.getTexture(), constraint.getBounds());
        }
        batch.end();
    }

    private void render(SpriteBatch batch, TextureRegion textureRegion, Rectangle rectangle) {
        batch.draw(
                textureRegion,
                rectangle.x,
                rectangle.y
        );
    }
}
