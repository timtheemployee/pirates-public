package com.wxxtfxrmx.pirates.screen.levelv2.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.wxxtfxrmx.pirates.screen.level.board.TileType;
import com.wxxtfxrmx.pirates.screen.levelv2.Constants;
import com.wxxtfxrmx.pirates.screen.levelv2.component.BoundsComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.component.ScaleComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.component.SpawnComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.component.TextureComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.component.TileTypeComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.factory.TileTexturesFactory;
import com.wxxtfxrmx.pirates.screen.levelv2.factory.TileTypeFactory;

public class FillUpEmptyEntitySystem extends IteratingSystem {

    private final TileTexturesFactory texturesFactory;
    private final TileTypeFactory typeFactory;
    private final ComponentMapper<ScaleComponent> scale = ComponentMapper.getFor(ScaleComponent.class);
    private final ComponentMapper<TileTypeComponent> type = ComponentMapper.getFor(TileTypeComponent.class);
    private final ComponentMapper<TextureComponent> texture = ComponentMapper.getFor(TextureComponent.class);
    private final ComponentMapper<SpawnComponent> spawn = ComponentMapper.getFor(SpawnComponent.class);
    private final ComponentMapper<BoundsComponent> bounds = ComponentMapper.getFor(BoundsComponent.class);

    public FillUpEmptyEntitySystem(TileTexturesFactory texturesFactory, TileTypeFactory typeFactory) {
        super(Family.all(
                ScaleComponent.class,
                TileTypeComponent.class,
                TextureComponent.class,
                SpawnComponent.class,
                BoundsComponent.class
        ).get());

        this.texturesFactory = texturesFactory;
        this.typeFactory = typeFactory;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        ScaleComponent scaleComponent = scale.get(entity);
        SpawnComponent spawnComponent = spawn.get(entity);

        if (scaleComponent.scale.x != 0f && scaleComponent.scale.y != 0f) return;
        if (isFillUpDisabled(spawnComponent)) return;

        TileType type = typeFactory.getRandomType();
        TextureRegion region = texturesFactory.getLeadingTexture(type);

        TextureComponent textureComponent = texture.get(entity);
        textureComponent.region = region;

        TileTypeComponent tileTypeComponent = this.type.get(entity);
        tileTypeComponent.type = type;

        scaleComponent.scale.x = 1f;
        scaleComponent.scale.y = 1f;

        BoundsComponent boundsComponent = bounds.get(entity);

        boundsComponent.bounds.x = spawnComponent.spawn.x;
        boundsComponent.bounds.y = spawnComponent.spawn.y;
    }

    private boolean isFillUpDisabled(SpawnComponent spawnComponent) {
        if (isOnTop(spawnComponent.spawn.y)) return false;

        Entity top = getTopEntity(spawnComponent.spawn.x, spawnComponent.spawn.y);

        if (top == null) return false;

        ScaleComponent scaleComponent = this.scale.get(top);

        return scaleComponent.scale.x == 1f && scaleComponent.scale.y == 1f;
    }

    private Entity getTopEntity(float x, float y) {
        ImmutableArray<Entity> entities = getEntities();
        float topY = y + Constants.UNIT;

        for (Entity entity : entities) {
            BoundsComponent boundsComponent = this.bounds.get(entity);

            if (boundsComponent.bounds.x == x && boundsComponent.bounds.y == topY) return entity;
        }

        return null;
    }

    private boolean isOnTop(float y) {
        return y == (Constants.MIDDLE_ROUNDED_HEIGHT - 1) * Constants.UNIT;
    }
}
