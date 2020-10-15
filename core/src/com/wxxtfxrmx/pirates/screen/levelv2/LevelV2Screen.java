package com.wxxtfxrmx.pirates.screen.levelv2;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.wxxtfxrmx.pirates.screen.levelv2.factory.TileTexturesFactory;
import com.wxxtfxrmx.pirates.screen.levelv2.factory.TileTypeFactory;
import com.wxxtfxrmx.pirates.screen.levelv2.system.ApplyBoardTouchSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.system.CleanupEntitiesOnEmptyTouchesSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.system.CleanupLessThan3PickedSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.system.CollectPickedEntitiesSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.system.DropDownTilesSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.system.MoveEntityToDestinationSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.system.RenderingSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.system.SetEntitiesTouchedSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.system.SetTileTypeSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.system.SpawnUsedTileSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.system.ValidatePreviouslyTouchedTilesSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.system.animation.PlayAnimationSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.system.animation.RemoveAnimationSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.system.animation.SetTileAnimationSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.system.animation.UpdateAnimationStateSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.world.BoardWorld;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class LevelV2Screen extends ScreenAdapter {

    private final PooledEngine engine;
    private final AssetManager assets;
    private final BoardWorld boardWorld;
    private final OrthographicCamera camera = new OrthographicCamera(
            Constants.WIDTH * Constants.UNIT,
            Constants.HEIGHT * Constants.UNIT
    );

    private final List<EntitySystem> inputSystems;
    private final List<EntitySystem> gameLogicSystems;
    private final List<EntitySystem> renderingSystems;

    public LevelV2Screen(SpriteBatch batch) {
        engine = new PooledEngine();
        assets = new AssetManager();
        camera.position.set(
                Constants.WIDTH * Constants.UNIT / 2f,
                Constants.HEIGHT * Constants.UNIT / 2f,
                0f
        );
        Random random = new Random(888L);
        TileTypeFactory typeFactory = new TileTypeFactory(random);
        TileTexturesFactory tileTexturesFactory = new TileTexturesFactory();
        boardWorld = new BoardWorld(engine, typeFactory, tileTexturesFactory);

        inputSystems = Collections.singletonList(
                new ApplyBoardTouchSystem(camera, engine)
        );

        gameLogicSystems = Arrays.asList(
                new CleanupEntitiesOnEmptyTouchesSystem(),
                new ValidatePreviouslyTouchedTilesSystem(),
                new SetEntitiesTouchedSystem(engine),
                new CleanupLessThan3PickedSystem(),
                new CollectPickedEntitiesSystem(engine),
                new SetTileAnimationSystem(engine, tileTexturesFactory),
                new UpdateAnimationStateSystem(),
                new PlayAnimationSystem(),
                new RemoveAnimationSystem(),
                new MoveEntityToDestinationSystem(engine),
                new DropDownTilesSystem(),
                new SetTileTypeSystem(tileTexturesFactory, typeFactory),
                new SpawnUsedTileSystem()
        );

        renderingSystems = Collections.singletonList(
                new RenderingSystem(camera, batch)
        );

        inputSystems.forEach(engine::addSystem);
        gameLogicSystems.forEach(engine::addSystem);
        renderingSystems.forEach(engine::addSystem);
    }

    @Override
    public void show() {
        boardWorld.create();
    }

    @Override
    public void render(float delta) {
        float newDelta = normalize(delta);
        engine.update(newDelta);
    }

    private float normalize(float delta) {
        return Math.min(delta, 0.1f);
    }

    @Override
    public void resume() {
        updateSystems(true);
    }

    @Override
    public void pause() {
        updateSystems(false);
    }

    private void updateSystems(Boolean processing) {
        for (EntitySystem system : engine.getSystems()) {
            system.setProcessing(processing);
        }
    }
}
