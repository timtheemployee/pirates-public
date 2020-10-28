package com.wxxtfxrmx.pirates.screen.levelv2.layer.battle;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.PooledEngine;
import com.wxxtfxrmx.pirates.screen.levelv2.Layer;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.system.CountDownTimeSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.system.SwitchTurnSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.world.BattleWorld;

import java.util.Arrays;
import java.util.List;

public class BattleLayer implements Layer {

    private final BattleWorld world;
    private final List<EntitySystem> inputSystems;
    private final List<EntitySystem> logicSystems;
    private final List<EntitySystem> renderingSystems;

    public BattleLayer(PooledEngine engine) {
        world = new BattleWorld(engine);
        inputSystems = Arrays.asList();
        logicSystems = Arrays.asList(
                new CountDownTimeSystem(),
                new SwitchTurnSystem()
        );
        renderingSystems = Arrays.asList();

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
