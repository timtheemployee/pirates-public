package com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.world;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.wxxtfxrmx.pirates.screen.levelv2.Constants;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.AiComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.CoinComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.CurrentTurnComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.DamageComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.EvasionComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.HpComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.PlayerComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.RemainedTimeComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.TextureSkeletonComponent;
import com.wxxtfxrmx.pirates.uikit.TextureSkeleton;

public class BattleWorld {

    private final ShipTextureLoader loader;
    private final PooledEngine engine;

    public BattleWorld(ShipTextureLoader loader, PooledEngine engine) {
        this.loader = loader;
        this.engine = engine;
    }

    public void create() {
        Entity player = createPlayer(engine);
        Entity ai = createAi(engine);
        Entity context = createContext(engine);

        engine.addEntity(player);
        engine.addEntity(ai);
        engine.addEntity(context);
    }

    private Entity createPlayer(PooledEngine engine) {
        Entity entity = engine.createEntity();

        PlayerComponent playerComponent = engine.createComponent(PlayerComponent.class);
        CurrentTurnComponent currentTurnComponent = engine.createComponent(CurrentTurnComponent.class);
        HpComponent hpComponent = engine.createComponent(HpComponent.class);
        hpComponent.value = 100;
        hpComponent.maxValue = 100;

        DamageComponent damageComponent = engine.createComponent(DamageComponent.class);
        damageComponent.value = 1;

        EvasionComponent evasionComponent = engine.createComponent(EvasionComponent.class);
        evasionComponent.percent = 0.1f;

        CoinComponent coinComponent = engine.createComponent(CoinComponent.class);
        coinComponent.value = 0;

        TextureRegion bottomSail = loader.load(ShipTexture.BACK_BOTTOM_SAIL, false);
        TextureSkeleton.TextureSkeletonEntity bottomSailEntity = new TextureSkeleton.TextureSkeletonEntity(bottomSail);
        TextureSkeleton skeleton = new TextureSkeleton(bottomSailEntity);
        skeleton.setAnchorPosition((Constants.WIDTH - 2.5f) * Constants.UNIT, (Constants.MIDDLE_ROUNDED_HEIGHT + 1) * Constants.UNIT);

        TextureRegion topSail = loader.load(ShipTexture.BACK_TOP_SAIL, false);
        TextureSkeleton.TextureSkeletonEntity topSailEntity = new TextureSkeleton.TextureSkeletonEntity(topSail);
        topSailEntity.setVerticalBias(0.85f);
        skeleton.addEntity(topSailEntity, bottomSailEntity);

        TextureRegion ship = loader.load(ShipTexture.MAIN_SHIP, false);
        TextureSkeleton.TextureSkeletonEntity shipEntity = new TextureSkeleton.TextureSkeletonEntity(ship);
        shipEntity.setVerticalBias(-0.3f);
        shipEntity.setHorizontalBias(-0.13f);
        skeleton.addEntity(shipEntity, bottomSailEntity);

        TextureRegion frontSail = loader.load(ShipTexture.FRONT_SAIL, false);
        TextureSkeleton.TextureSkeletonEntity frontSailEntity = new TextureSkeleton.TextureSkeletonEntity(frontSail);
        frontSailEntity.setVerticalBias(0.8f);
        frontSailEntity.setHorizontalBias(-0.2f);
        skeleton.addEntity(frontSailEntity, shipEntity);

        TextureSkeletonComponent textureSkeletonComponent = engine.createComponent(TextureSkeletonComponent.class);
        textureSkeletonComponent.skeleton = skeleton;

        entity.add(playerComponent);
        entity.add(currentTurnComponent);
        entity.add(hpComponent);
        entity.add(damageComponent);
        entity.add(evasionComponent);
        entity.add(coinComponent);
        entity.add(textureSkeletonComponent);

        return entity;
    }

    private Entity createAi(PooledEngine engine) {
        Entity entity = engine.createEntity();

        AiComponent aiComponent = engine.createComponent(AiComponent.class);
        HpComponent hpComponent = engine.createComponent(HpComponent.class);
        hpComponent.value = 100;
        hpComponent.maxValue = 100;

        DamageComponent damageComponent = engine.createComponent(DamageComponent.class);
        damageComponent.value = 1;

        EvasionComponent evasionComponent = engine.createComponent(EvasionComponent.class);
        evasionComponent.percent = 0.1f;

        CoinComponent coinComponent = engine.createComponent(CoinComponent.class);
        coinComponent.value = 0;

        TextureRegion bottomSail = loader.load(ShipTexture.BACK_BOTTOM_SAIL, true);
        TextureSkeleton.TextureSkeletonEntity bottomSailEntity = new TextureSkeleton.TextureSkeletonEntity(bottomSail);
        TextureSkeleton skeleton = new TextureSkeleton(bottomSailEntity);
        skeleton.setAnchorPosition(0.1f * Constants.UNIT, (Constants.MIDDLE_ROUNDED_HEIGHT + 1) * Constants.UNIT);

        TextureRegion topSail = loader.load(ShipTexture.BACK_TOP_SAIL, true);
        TextureSkeleton.TextureSkeletonEntity topSailEntity = new TextureSkeleton.TextureSkeletonEntity(topSail);
        topSailEntity.setVerticalBias(0.85f);
        topSailEntity.setHorizontalBias(0.27f);
        skeleton.addEntity(topSailEntity, bottomSailEntity);

        TextureRegion ship = loader.load(ShipTexture.MAIN_SHIP, true);
        TextureSkeleton.TextureSkeletonEntity shipEntity = new TextureSkeleton.TextureSkeletonEntity(ship);
        shipEntity.setVerticalBias(-0.3f);
        shipEntity.setHorizontalBias(0.27f);
        skeleton.addEntity(shipEntity, bottomSailEntity);

        TextureRegion frontSail = loader.load(ShipTexture.FRONT_SAIL, true);
        TextureSkeleton.TextureSkeletonEntity frontSailEntity = new TextureSkeleton.TextureSkeletonEntity(frontSail);
        frontSailEntity.setVerticalBias(0.8f);
        frontSailEntity.setHorizontalBias(0.35f);
        skeleton.addEntity(frontSailEntity, shipEntity);

        TextureSkeletonComponent textureSkeletonComponent = engine.createComponent(TextureSkeletonComponent.class);
        textureSkeletonComponent.skeleton = skeleton;

        entity.add(aiComponent);
        entity.add(hpComponent);
        entity.add(damageComponent);
        entity.add(evasionComponent);
        entity.add(coinComponent);
        entity.add(textureSkeletonComponent);

        return entity;
    }

    private Entity createContext(PooledEngine engine) {
        Entity entity = engine.createEntity();

        RemainedTimeComponent timeComponent = engine.createComponent(RemainedTimeComponent.class);
        timeComponent.remainedTime = 10;

        entity.add(timeComponent);

        return entity;
    }
}
