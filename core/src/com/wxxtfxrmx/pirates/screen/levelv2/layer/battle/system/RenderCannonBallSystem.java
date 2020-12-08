package com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.wxxtfxrmx.pirates.screen.levelv2.component.TextureComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.CannonBallComponent;

public class RenderCannonBallSystem extends IteratingSystem {

    private final SpriteBatch batch;

    private final ComponentMapper<TextureComponent> textureMapper = ComponentMapper.getFor(TextureComponent.class);
    private final ComponentMapper<CannonBallComponent> cannonBallMapper = ComponentMapper.getFor(CannonBallComponent.class);

    public RenderCannonBallSystem(SpriteBatch batch) {
        super(Family.all(TextureComponent.class, CannonBallComponent.class).get());
        this.batch = batch;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        CannonBallComponent cannonBallComponent = cannonBallMapper.get(entity);
        TextureComponent textureComponent = textureMapper.get(entity);

        batch.begin();
        batch.draw(
                textureComponent.region,
                cannonBallComponent.currentPoint.x,
                cannonBallComponent.currentPoint.y,
                textureComponent.region.getRegionWidth(),
                textureComponent.region.getRegionHeight()
        );
        batch.end();
    }
}
