package com.wxxtfxrmx.pirates.screen.levelv2.layer.board.system.animation;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.wxxtfxrmx.pirates.screen.levelv2.component.AnimationComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.component.TilePickedComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.component.TileTypeComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.factory.TileTexturesFactory;

public class SetTileAnimationSystem extends IteratingSystem {

    private ComponentMapper<AnimationComponent> animationMapper = ComponentMapper.getFor(AnimationComponent.class);
    private ComponentMapper<TileTypeComponent> typeMapper = ComponentMapper.getFor(TileTypeComponent.class);
    private final PooledEngine pooledEngine;
    private final TileTexturesFactory tileTexturesFactory;

    public SetTileAnimationSystem(PooledEngine pooledEngine, TileTexturesFactory tileTexturesFactory) {
        super(Family.all(TilePickedComponent.class, TileTypeComponent.class).get());
        this.pooledEngine = pooledEngine;
        this.tileTexturesFactory = tileTexturesFactory;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        if (animationMapper.has(entity)) return;

        TileTypeComponent typeComponent = typeMapper.get(entity);

        AnimationComponent animationComponent = pooledEngine.createComponent(AnimationComponent.class);
        animationComponent.animation = tileTexturesFactory.getAnimation(typeComponent.type, 0.2f);
        animationComponent.animation.setPlayMode(Animation.PlayMode.LOOP);

        entity.add(animationComponent);
    }
}
