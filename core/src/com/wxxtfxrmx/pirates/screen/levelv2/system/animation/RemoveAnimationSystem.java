package com.wxxtfxrmx.pirates.screen.levelv2.system.animation;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.component.AnimationComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.component.TextureComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.component.TilePickedComponent;

public class RemoveAnimationSystem extends IteratingSystem {

    private final ComponentMapper<TextureComponent> textureMapper = ComponentMapper.getFor(TextureComponent.class);
    private final ComponentMapper<AnimationComponent> animationMapper = ComponentMapper.getFor(AnimationComponent.class);

    public RemoveAnimationSystem() {
        super(Family.all(TextureComponent.class, AnimationComponent.class).exclude(TilePickedComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        AnimationComponent animationComponent = animationMapper.get(entity);
        TextureComponent textureComponent = textureMapper.get(entity);

        textureComponent.region = animationComponent.animation.getKeyFrame(0);

        animationComponent.frameDelta = 0f;
        entity.remove(AnimationComponent.class);
    }
}
