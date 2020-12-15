package com.wxxtfxrmx.pirates.screen.levelv2.layer.battle;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.wxxtfxrmx.pirates.screen.levelv2.Layer;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.system.ApplyCoinsSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.system.ApplyDamageSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.system.ApplyEvasionSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.system.ApplyRepairSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.system.CountDownTimeSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.system.HandleRepairHammerAnimationTimeSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.system.PostProcessShotSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.system.ProcessShotDistributionSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.system.RenderHpSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.system.SwitchTurnSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.system.TextureSkeletonRenderingSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.system.ValidateAiHpSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.system.ValidatePlayerHpSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.world.BattleWorld;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.world.ShipTextureLoader;

import java.util.Arrays;
import java.util.List;

public class BattleLayer implements Layer {

    private final ShipTextureLoader loader = new ShipTextureLoader();
    private final BattleWorld world;
    private final List<? extends EntitySystem> inputSystems;
    private final List<? extends EntitySystem> logicSystems;
    private final List<? extends EntitySystem> renderingSystems;

    public BattleLayer(PooledEngine engine, SpriteBatch batch, ShapeRenderer renderer) {
        world = new BattleWorld(loader, engine);
        inputSystems = Arrays.asList();
        logicSystems = Arrays.asList(
                new ApplyRepairSystem(loader, engine),
                new ApplyEvasionSystem(),
                new ApplyDamageSystem(engine),
                new ApplyCoinsSystem(),
                new ValidateAiHpSystem(),
                new ValidatePlayerHpSystem(engine),
                new CountDownTimeSystem(),
                new SwitchTurnSystem(engine),
                new ProcessShotDistributionSystem(engine, loader),
                //FIXME Fix for non blocking board, because, if any entity has ReadyToReuseComponent, enemy input is blocked
                new PostProcessShotSystem(),
                new HandleRepairHammerAnimationTimeSystem()
        );

        renderingSystems = Arrays.asList(
                new TextureSkeletonRenderingSystem(batch),
                new RenderHpSystem(renderer)
        );

        for (EntitySystem inputSystem : inputSystems) {
            engine.addSystem(inputSystem);
        }
        for (EntitySystem logicSystem : logicSystems) {
            engine.addSystem(logicSystem);
        }
        for (EntitySystem renderingSystem : renderingSystems) {
            engine.addSystem(renderingSystem);
        }
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

    private void update(List<? extends EntitySystem> systems, boolean enabled) {
        for (EntitySystem system : systems) {
            system.setProcessing(enabled);
        }
    }
}
