package com.wxxtfxrmx.pirates.screen.levelv2.layer.board.system.animation;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.component.AnimationComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.component.TilePickedComponent;

public class UpdateAnimationStateSystem extends IteratingSystem {

    private final ComponentMapper<AnimationComponent> animationMapper = ComponentMapper.getFor(AnimationComponent.class);

    public UpdateAnimationStateSystem() {
        super(Family.all(AnimationComponent.class, TilePickedComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        animationMapper.get(entity).frameDelta += deltaTime;
    }
}
