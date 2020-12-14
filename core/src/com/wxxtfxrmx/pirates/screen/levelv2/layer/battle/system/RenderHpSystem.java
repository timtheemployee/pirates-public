package com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.wxxtfxrmx.pirates.screen.levelv2.Constants;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.HpComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.TextureSkeletonComponent;

public class RenderHpSystem extends IteratingSystem {

    private static final float HEALTH_BAR_HEIGHT = 8f;

    private final ComponentMapper<HpComponent> hpMapper = ComponentMapper.getFor(HpComponent.class);
    private final ComponentMapper<TextureSkeletonComponent> skeletonMapper = ComponentMapper.getFor(TextureSkeletonComponent.class);

    private final ShapeRenderer renderer;

    public RenderHpSystem(ShapeRenderer renderer) {
        super(Family.all(
                HpComponent.class,
                TextureSkeletonComponent.class
        ).get());
        this.renderer = renderer;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TextureSkeletonComponent textureSkeletonComponent = skeletonMapper.get(entity);
        Rectangle skeletonBounds = textureSkeletonComponent.skeleton.getBounds();

        HpComponent hpComponent = hpMapper.get(entity);
        float hpRatio = hpComponent.value / (float) hpComponent.maxValue;

        float healthBarX = skeletonBounds.x;
        float heathBarY = skeletonBounds.y + skeletonBounds.height + Constants.UNIT;

        float healthBarWidth = skeletonBounds.width;
        float actualHealthBarWidth = healthBarWidth * hpRatio;

        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.rect(healthBarX, heathBarY, healthBarWidth, HEALTH_BAR_HEIGHT, Color.RED, Color.RED, Color.RED, Color.RED);
        renderer.rect(healthBarX, heathBarY, actualHealthBarWidth, HEALTH_BAR_HEIGHT, Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN);
        renderer.end();
    }
}
