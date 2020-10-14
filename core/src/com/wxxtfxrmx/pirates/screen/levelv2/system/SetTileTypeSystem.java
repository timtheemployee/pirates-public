package com.wxxtfxrmx.pirates.screen.levelv2.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.wxxtfxrmx.pirates.screen.levelv2.component.ReadyToReuseComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.component.TextureComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.component.TileTypeComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.factory.TileTexturesFactory;
import com.wxxtfxrmx.pirates.screen.levelv2.factory.TileTypeFactory;
import com.wxxtfxrmx.pirates.screen.levelv2.world.TileType;

public class SetTileTypeSystem extends IteratingSystem {

    private final ComponentMapper<TileTypeComponent> typeMapper = ComponentMapper.getFor(TileTypeComponent.class);
    private final ComponentMapper<TextureComponent> textureMapper = ComponentMapper.getFor(TextureComponent.class);
    private final TileTexturesFactory texturesFactory;
    private final TileTypeFactory typeFactory;

    public SetTileTypeSystem(TileTexturesFactory texturesFactory, TileTypeFactory typeFactory) {
        super(Family.all(
                ReadyToReuseComponent.class,
                TileTypeComponent.class,
                TextureComponent.class
        ).get());

        this.texturesFactory = texturesFactory;
        this.typeFactory = typeFactory;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TileType type = typeFactory.getRandomType();
        TextureRegion texture = texturesFactory.getLeadingTexture(type);

        TileTypeComponent typeComponent = typeMapper.get(entity);
        typeComponent.type = type;

        TextureComponent textureComponent = textureMapper.get(entity);
        textureComponent.region = texture;
    }
}
