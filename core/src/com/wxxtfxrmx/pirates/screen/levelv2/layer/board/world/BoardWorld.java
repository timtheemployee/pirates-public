package com.wxxtfxrmx.pirates.screen.levelv2.layer.board.world;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.wxxtfxrmx.pirates.screen.levelv2.Constants;
import com.wxxtfxrmx.pirates.screen.levelv2.component.BoundsComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.component.ScaleComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.component.TextureComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.board.component.TileTypeComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.board.component.TouchChainComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.factory.TileTexturesFactory;
import com.wxxtfxrmx.pirates.screen.levelv2.factory.TileTypeFactory;

public class BoardWorld {
    private final PooledEngine engine;
    private final TileTypeFactory typeFactory;
    private final TileTexturesFactory texturesFactory;

    public BoardWorld(PooledEngine engine, TileTypeFactory typeFactory, TileTexturesFactory texturesFactory) {
        this.engine = engine;
        this.typeFactory = typeFactory;
        this.texturesFactory = texturesFactory;
    }

    public void create() {

        Entity touch = engine.createEntity();
        TouchChainComponent touchChainComponent = engine.createComponent(TouchChainComponent.class);
        touch.add(touchChainComponent);
        engine.addEntity(touch);

        for (int width = 0; width < Constants.WIDTH; width++) {
            for (int height = 0; height < Constants.MIDDLE_ROUNDED_HEIGHT; height++) {
                Entity entity = createEntity(engine, width, height);
                engine.addEntity(entity);
            }
        }
    }

    private Entity createEntity(PooledEngine engine, int x, int y) {
        Entity entity = engine.createEntity();

        ScaleComponent scaleComponent = engine.createComponent(ScaleComponent.class);
        scaleComponent.scale.x = 1f;
        scaleComponent.scale.y = 1f;

        BoundsComponent boundsComponent = engine.createComponent(BoundsComponent.class);
        boundsComponent.bounds.x = x * Constants.UNIT;
        boundsComponent.bounds.y = y * Constants.UNIT;
        boundsComponent.bounds.width = Constants.UNIT;
        boundsComponent.bounds.height = Constants.UNIT;
        boundsComponent.z = 0f;

        TileType type = typeFactory.getRandomType();

        TileTypeComponent typeComponent = engine.createComponent(TileTypeComponent.class);
        typeComponent.type = type;

        TextureComponent textureComponent = engine.createComponent(TextureComponent.class);
        textureComponent.region = texturesFactory.getLeadingTexture(type);
        textureComponent.border = texturesFactory.getTileBoundsTexture();

        entity.add(scaleComponent);
        entity.add(boundsComponent);
        entity.add(typeComponent);
        entity.add(textureComponent);

        return entity;
    }
}
