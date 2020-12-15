package com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.CurrentTurnComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.TextureSkeletonComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.board.component.ReadyToReuseComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.board.component.TileTypeComponent;

public class PostProcessShotSystem extends IteratingSystem {

    //NOTE: used current turn component for defender because at this moment, already swapped turns (bombs are moving to target)
    private final Family defenderFamily = Family.all(TextureSkeletonComponent.class, CurrentTurnComponent.class).get();

    private final ComponentMapper<TextureSkeletonComponent> skeletonMapper = ComponentMapper.getFor(TextureSkeletonComponent.class);

    public PostProcessShotSystem() {
        super(Family.all(ReadyToReuseComponent.class).exclude(TileTypeComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Entity defender = getEngine().getEntitiesFor(defenderFamily).first();
        TextureSkeletonComponent skeletonComponent = skeletonMapper.get(defender);
        skeletonComponent.skeleton.setHitTextureDrawing(true);
        entity.remove(ReadyToReuseComponent.class);
        getEngine().removeEntity(entity);
    }
}
