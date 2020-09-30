package com.wxxtfxrmx.pirates.screen.levelv2.world;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.wxxtfxrmx.pirates.screen.level.board.TileType;
import com.wxxtfxrmx.pirates.screen.levelv2.Constants;
import com.wxxtfxrmx.pirates.screen.levelv2.component.BoundsComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.component.ScaleComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.component.SpawnComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.component.TextureComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.component.TileMatchComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.component.TileStateComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.component.TileTypeComponent;

import java.util.Random;

public class BoardWorld {
    private final PooledEngine engine;
    private final Random random;
    private final TileType[] typeValues = TileType.values();
    private final TextureAtlas levelAtlas = new TextureAtlas("sprite/tiles-sheet.atlas");

    public BoardWorld(PooledEngine engine, long ssid) {
        this.engine = engine;
        this.random = new Random(ssid);
    }

    public void create() {
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

        SpawnComponent spawnComponent = engine.createComponent(SpawnComponent.class);
        spawnComponent.spawn.x = x * Constants.UNIT;
        spawnComponent.spawn.y = y * Constants.UNIT;

        TileType type = getType();

        TileTypeComponent typeComponent = engine.createComponent(TileTypeComponent.class);
        typeComponent.type = type;

        TileStateComponent stateComponent = engine.createComponent(TileStateComponent.class);

        TextureComponent textureComponent = engine.createComponent(TextureComponent.class);
        textureComponent.region = getLeadingTexture(type);

        TileMatchComponent matchComponent = engine.createComponent(TileMatchComponent.class);
        matchComponent.matched = false;

        entity.add(scaleComponent);
        entity.add(boundsComponent);
        entity.add(spawnComponent);
        entity.add(typeComponent);
        entity.add(stateComponent);
        entity.add(textureComponent);
        entity.add(matchComponent);

        return entity;
    }

    private TileType getType() {
        return typeValues[random.nextInt(typeValues.length)];
    }

    private TextureRegion getLeadingTexture(TileType type) {
        String path = type.getSheet();
        TextureAtlas.AtlasRegion region = levelAtlas.findRegion(path);

        return new TextureRegion(region, 0, 0, Constants.UNIT, Constants.UNIT);
    }
}
