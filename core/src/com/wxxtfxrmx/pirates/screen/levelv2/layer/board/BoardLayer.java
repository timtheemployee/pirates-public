package com.wxxtfxrmx.pirates.screen.levelv2.layer.board;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.wxxtfxrmx.pirates.screen.levelv2.Layer;
import com.wxxtfxrmx.pirates.screen.levelv2.factory.TileTexturesFactory;
import com.wxxtfxrmx.pirates.screen.levelv2.factory.TileTypeFactory;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.system.ContinueRandomChainSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.system.TouchRandomTileSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.board.system.ApplyBoardTouchSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.board.system.CleanupEntitiesOnEmptyTouchesSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.board.system.CleanupLessThan3PickedSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.board.system.MarkMatchedEntitiesSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.board.system.DropDownTilesSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.board.system.MoveEntityToDestinationSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.board.system.SendCollectedTilesSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.board.system.SetEntitiesTouchedSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.board.system.SetTileTypeSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.board.system.SpawnUsedTileSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.board.system.ValidatePreviouslyTouchedTilesSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.board.system.animation.PlayAnimationSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.board.system.animation.RemoveAnimationSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.board.system.animation.SetTileAnimationSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.board.system.animation.UpdateAnimationStateSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.board.system.rendering.EnvRenderingSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.board.system.rendering.RenderingSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.board.world.BoardWorld;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BoardLayer implements Layer {

    private final List<EntitySystem> inputSystems;
    private final List<EntitySystem> logicSystems;
    private final List<EntitySystem> renderingSystems;
    private final BoardWorld world;

    public BoardLayer(final PooledEngine engine,
                      final Random random,
                      final OrthographicCamera camera,
                      final SpriteBatch batch) {

        TileTexturesFactory tileTexturesFactory = new TileTexturesFactory();
        TileTypeFactory tileTypeFactory = new TileTypeFactory(random);

        inputSystems = Arrays.asList(
                new ApplyBoardTouchSystem(camera, engine),
                new TouchRandomTileSystem(engine),
                new ContinueRandomChainSystem(engine)
        );

        logicSystems = Arrays.asList(
                new CleanupEntitiesOnEmptyTouchesSystem(),
                new ValidatePreviouslyTouchedTilesSystem(),
                new SetEntitiesTouchedSystem(engine),
                new CleanupLessThan3PickedSystem(),
                new MarkMatchedEntitiesSystem(engine),
                new SendCollectedTilesSystem(engine),
                new SetTileAnimationSystem(engine, tileTexturesFactory),
                new UpdateAnimationStateSystem(),
                new PlayAnimationSystem(),
                new RemoveAnimationSystem(),
                new MoveEntityToDestinationSystem(engine),
                new DropDownTilesSystem(),
                new SetTileTypeSystem(tileTexturesFactory, tileTypeFactory),
                new SpawnUsedTileSystem()
        );

        ShapeRenderer shapeRenderer = new ShapeRenderer();

        renderingSystems = Arrays.asList(
                new EnvRenderingSystem(shapeRenderer, camera),
                new RenderingSystem(camera, batch)
        );

        world = new BoardWorld(engine, tileTypeFactory, tileTexturesFactory);

        inputSystems.forEach(engine::addSystem);
        logicSystems.forEach(engine::addSystem);
        renderingSystems.forEach(engine::addSystem);
    }

    @Override
    public void create() {
        world.create();
    }

    @Override
    public void setEnabled(boolean enabled) {
        update(inputSystems, enabled);
        update(logicSystems, enabled);
        update(renderingSystems, enabled);
    }

    private void update(List<EntitySystem> systems, boolean enabled) {
        systems.forEach((system) -> system.setProcessing(enabled));
    }
}
