package com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.wxxtfxrmx.pirates.screen.levelv2.UnstoppableSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.TextureSkeletonComponent;

public class TextureSkeletonRenderingSystem extends IteratingSystem implements UnstoppableSystem {

    private final ComponentMapper<TextureSkeletonComponent> skeletonMapper = ComponentMapper.getFor(TextureSkeletonComponent.class);
    private final SpriteBatch batch;

    public TextureSkeletonRenderingSystem(SpriteBatch batch) {
        super(Family.all(TextureSkeletonComponent.class).get());
        this.batch = batch;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TextureSkeletonComponent skeletonComponent = skeletonMapper.get(entity);

        skeletonComponent.skeleton.draw(batch);
    }
}
