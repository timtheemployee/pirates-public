package com.wxxtfxrmx.pirates.screen.levelv2.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.wxxtfxrmx.pirates.screen.levelv2.Constants;
import com.wxxtfxrmx.pirates.screen.levelv2.component.BoundsComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.component.ScaleComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.component.TextureComponent;

public class RenderingSystem extends IteratingSystem {

    private final Array<Entity> renderQueue = new Array<>();
    private final ComponentMapper<BoundsComponent> bounds = ComponentMapper.getFor(BoundsComponent.class);
    private final ComponentMapper<ScaleComponent> scale = ComponentMapper.getFor(ScaleComponent.class);
    private final ComponentMapper<TextureComponent> texture = ComponentMapper.getFor(TextureComponent.class);
    private final OrthographicCamera camera = new OrthographicCamera(
            Constants.WIDTH * Constants.UNIT,
            Constants.HEIGHT * Constants.UNIT
    );

    private final SpriteBatch batch;

    public RenderingSystem(SpriteBatch batch) {
        super(Family.all(BoundsComponent.class, ScaleComponent.class, TextureComponent.class).get());
        this.batch = batch;
        camera.position.set(
                Constants.WIDTH * Constants.UNIT / 2f,
                Constants.HEIGHT * Constants.UNIT / 2f,
                0f
        );
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        renderQueue.sort(this::byZ);

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        for (Entity entity: renderQueue) {
            render(batch, entity);
        }

        batch.end();

        renderQueue.clear();
    }

    private void render(SpriteBatch batch, Entity entity) {
        TextureRegion texture = this.texture.get(entity).region;
        if (texture == null) return;

        Rectangle bounds = this.bounds.get(entity).bounds;
        Vector2 scale = this.scale.get(entity).scale;

        float width = texture.getRegionWidth();
        float height = texture.getRegionHeight();

        float originX = width * 0.5f;
        float originY = height * 0.5f;

        batch.draw(
                texture,
                bounds.x, bounds.y,
                originX, originY,
                width, height,
                scale.x, scale.y,
                0f
        );
    }

    private int byZ(Entity first, Entity second) {
        return (int) Math.signum(bounds.get(second).z - bounds.get(first).z);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        renderQueue.add(entity);
    }
}
