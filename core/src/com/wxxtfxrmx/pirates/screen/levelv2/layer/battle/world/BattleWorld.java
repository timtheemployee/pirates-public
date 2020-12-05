package com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.world;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.wxxtfxrmx.pirates.screen.levelv2.Constants;
import com.wxxtfxrmx.pirates.screen.levelv2.component.BoundsComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.AiComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.CoinComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.CurrentTurnComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.DamageComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.EvasionComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.HpComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.PlayerComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.RemainedTimeComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.ShipPartComponent;

import java.util.ArrayList;
import java.util.List;

public class BattleWorld {

    private final PooledEngine engine;
    private final TextureAtlas shipParts = new TextureAtlas(Gdx.files.internal("sprite/ship_sprite.atlas"));

    public BattleWorld(PooledEngine engine) {
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
        hpComponent.value = 5;

        DamageComponent damageComponent = engine.createComponent(DamageComponent.class);
        damageComponent.value = 1;

        EvasionComponent evasionComponent = engine.createComponent(EvasionComponent.class);
        evasionComponent.percent = 0.1f;

        CoinComponent coinComponent = engine.createComponent(CoinComponent.class);
        coinComponent.value = 0;

        TextureRegion backBottomSail = loadPart(ShipTexture.BACK_BOTTOM_SAIL, false);
        ShipPart backBottomSailPart = new ShipPart();
        backBottomSailPart.texture = backBottomSail;
        backBottomSailPart.offset = new Offset(0, 0);
        backBottomSailPart.flipped = false;

        TextureRegion backTopSail = loadPart(ShipTexture.BACK_TOP_SAIL, false);
        ShipPart backTopSailPart = new ShipPart();
        backTopSailPart.texture = backTopSail;
        backTopSailPart.offset = new Offset(0, backBottomSail.getRegionHeight() * 0.85f);
        backTopSailPart.flipped = false;

        TextureRegion mainShip = loadPart(ShipTexture.MAIN_SHIP, false);
        ShipPart mainShipPart = new ShipPart();
        mainShipPart.texture = mainShip;
        mainShipPart.offset = new Offset(-16, -backBottomSail.getRegionHeight() * 0.3f);
        mainShipPart.flipped = false;

        TextureRegion frontSail = loadPart(ShipTexture.FRONT_SAIL, false);
        ShipPart frontPart = new ShipPart();
        frontPart.texture = frontSail;
        frontPart.offset = new Offset(-(backBottomSail.getRegionWidth() * 0.3f), Constants.UNIT * 0.7f);
        frontPart.flipped = false;

        List<ShipPart> parts = new ArrayList<ShipPart>();
        parts.add(backBottomSailPart);
        parts.add(backTopSailPart);
        parts.add(mainShipPart);
        parts.add(frontPart);

        ShipPartComponent partComponent = engine.createComponent(ShipPartComponent.class);
        partComponent.parts = parts;

        BoundsComponent boundsComponent = engine.createComponent(BoundsComponent.class);
        boundsComponent.bounds = new Rectangle();
        boundsComponent.bounds.setWidth(Constants.UNIT * 3);
        boundsComponent.bounds.setHeight(Constants.UNIT * 4);
        boundsComponent.bounds.setX(Constants.WIDTH * Constants.UNIT - boundsComponent.bounds.width);
        boundsComponent.bounds.setY(Constants.MIDDLE_ROUNDED_HEIGHT * Constants.UNIT + Constants.UNIT);

        entity.add(playerComponent);
        entity.add(currentTurnComponent);
        entity.add(hpComponent);
        entity.add(damageComponent);
        entity.add(evasionComponent);
        entity.add(coinComponent);
        entity.add(partComponent);
        entity.add(boundsComponent);

        return entity;
    }

    private Entity createAi(PooledEngine engine) {
        Entity entity = engine.createEntity();

        AiComponent playerComponent = engine.createComponent(AiComponent.class);
        HpComponent hpComponent = engine.createComponent(HpComponent.class);
        hpComponent.value = 5;

        DamageComponent damageComponent = engine.createComponent(DamageComponent.class);
        damageComponent.value = 1;

        EvasionComponent evasionComponent = engine.createComponent(EvasionComponent.class);
        evasionComponent.percent = 0.1f;

        CoinComponent coinComponent = engine.createComponent(CoinComponent.class);
        coinComponent.value = 0;

        entity.add(playerComponent);
        entity.add(hpComponent);
        entity.add(damageComponent);
        entity.add(evasionComponent);
        entity.add(coinComponent);

        return entity;
    }

    private TextureRegion loadPart(ShipTexture texture, boolean flipped) {
        TextureAtlas.AtlasRegion region = shipParts.findRegion(texture.getTextureName());
        region.flip(flipped, false);
        return new TextureRegion(region);
    }

    private Entity createContext(PooledEngine engine) {
        Entity entity = engine.createEntity();

        RemainedTimeComponent timeComponent = engine.createComponent(RemainedTimeComponent.class);
        timeComponent.remainedTime = 10;

        entity.add(timeComponent);

        return entity;
    }
}
