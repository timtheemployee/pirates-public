package com.wxxtfxrmx.pirates.screen.levelv2.layer.board.system.rendering;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.wxxtfxrmx.pirates.screen.levelv2.UnstoppableSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.component.BoundsComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.component.RotationComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.component.ScaleComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.component.TextureComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.board.component.TilePickedComponent;

import java.util.Comparator;

public class RenderingSystem extends IteratingSystem implements UnstoppableSystem {

    private final Array<Entity> renderQueue = new Array<Entity>();
    private final ComponentMapper<BoundsComponent> bounds = ComponentMapper.getFor(BoundsComponent.class);
    private final ComponentMapper<ScaleComponent> scale = ComponentMapper.getFor(ScaleComponent.class);
    private final ComponentMapper<TextureComponent> texture = ComponentMapper.getFor(TextureComponent.class);
    private final ComponentMapper<TilePickedComponent> state = ComponentMapper.getFor(TilePickedComponent.class);
    private final ComponentMapper<RotationComponent> rotationMapper = ComponentMapper.getFor(RotationComponent.class);

    private final SpriteBatch batch;

    public RenderingSystem(SpriteBatch batch) {
        super(Family.all(BoundsComponent.class, ScaleComponent.class, TextureComponent.class).get());
        this.batch = batch;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        renderQueue.sort(new Comparator<Entity>() {
            @Override
            public int compare(Entity first, Entity second) {
                return byZ(first, second);
            }
        });

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

        float rotation = rotationMapper.has(entity) ? rotationMapper.get(entity).angle : 0f;

        if (stateComponent != null) {
            batch.draw(
                    border,
                    bounds.x, bounds.y,
                    originX, originY,
                    width, height,
                    scale.x, scale.y,
                    rotation
            );
        }

        batch.draw(
                texture,
                bounds.x, bounds.y,
                originX, originY,
                width, height,
                scale.x, scale.y,
                rotation
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
