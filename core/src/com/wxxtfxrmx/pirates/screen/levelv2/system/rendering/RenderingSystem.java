package com.wxxtfxrmx.pirates.screen.levelv2.system.rendering;

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
import com.wxxtfxrmx.pirates.screen.levelv2.component.BoundsComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.component.ScaleComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.component.TextureComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.component.TilePickedComponent;

public class RenderingSystem extends IteratingSystem {

    private final Array<Entity> renderQueue = new Array<>();
    private final ComponentMapper<BoundsComponent> bounds = ComponentMapper.getFor(BoundsComponent.class);
    private final ComponentMapper<ScaleComponent> scale = ComponentMapper.getFor(ScaleComponent.class);
    private final ComponentMapper<TextureComponent> texture = ComponentMapper.getFor(TextureComponent.class);
    private final ComponentMapper<TilePickedComponent> state = ComponentMapper.getFor(TilePickedComponent.class);
    private final OrthographicCamera camera;

    private final SpriteBatch batch;

    public RenderingSystem(OrthographicCamera camera, SpriteBatch batch) {
        super(Family.all(BoundsComponent.class, ScaleComponent.class, TextureComponent.class).get());
        this.camera = camera;
        this.batch = batch;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        renderQueue.sort(this::byZ);

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        for (Entity entity : renderQueue) {
            render(batch, entity);
        }

        batch.end();

        renderQueue.clear();
    }

    private void render(SpriteBatch batch, Entity entity) {
        TextureRegion texture = this.texture.get(entity).region;
        TextureRegion border = this.texture.get(entity).border;
        if (texture == null) return;

        Rectangle bounds = this.bounds.get(entity).bounds;
        Vector2 scale = this.scale.get(entity).scale;

        float width = texture.getRegionWidth();
        float height = texture.getRegionHeight();

        float originX = width * 0.5f;
        float originY = height * 0.5f;

        TilePickedComponent stateComponent = this.state.get(entity);

        if (stateComponent != null) {
            batch.draw(
                    border,
                    bounds.x, bounds.y,
                    originX, originY,
                    width, height,
                    scale.x, scale.y,
                    0f
            );
        }

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
        return (int) Math.signum(bounds.get(first).z - bounds.get(second).z);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        renderQueue.add(entity);
    }
}
