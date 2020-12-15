package com.wxxtfxrmx.pirates.screen.levelv2.layer.ui.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.wxxtfxrmx.pirates.screen.levelv2.UnstoppableSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.AiComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.CoinComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.PlayerComponent;
import com.wxxtfxrmx.pirates.uikit.UiImageLabel;
import com.wxxtfxrmx.pirates.uikit.slot.UiSlotMachine;

public class HandleCoinsCountSystem extends IteratingSystem {

    private final ComponentMapper<CoinComponent> coinMapper = ComponentMapper.getFor(CoinComponent.class);
    private final ComponentMapper<PlayerComponent> playerMapper = ComponentMapper.getFor(PlayerComponent.class);
    private final ComponentMapper<AiComponent> aiMapper = ComponentMapper.getFor(AiComponent.class);

    private final UiImageLabel playerCoins;
    private final UiImageLabel aiCoins;
    private final UiSlotMachine slotMachine;
    private final Stage stage;

    public HandleCoinsCountSystem(UiImageLabel playerCoins, UiImageLabel aiCoins, UiSlotMachine slotMachine, Stage stage) {
        super(Family
                .all(CoinComponent.class)
                .one(PlayerComponent.class, AiComponent.class).get());

        this.playerCoins = playerCoins;
        this.aiCoins = aiCoins;
        this.slotMachine = slotMachine;
        this.stage = stage;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        CoinComponent coinComponent = coinMapper.get(entity);
        String coins = String.valueOf(coinComponent.value);
        if (playerMapper.has(entity)) {
            playerCoins.setText(coins);
        } else if (aiMapper.has(entity)) {
            aiCoins.setText(coins);
        }

        if (coinComponent.value >= 10) {
            coinComponent.value -= 10;
            slotMachine.show(stage);
            pause();
        } else if (!slotMachine.hasParent()) {
            resume();
        }
    }

    private void pause() {
        for (EntitySystem system : getEngine().getSystems()) {
            if (system instanceof UnstoppableSystem) continue;

            if (system.getClass() != this.getClass()) {
                system.setProcessing(false);
            }
        }
    }

    private void resume() {
        for (EntitySystem system : getEngine().getSystems()) {
            if (system instanceof UnstoppableSystem) continue;

            if (system.getClass() != this.getClass()) {
                system.setProcessing(true);
            }
        }
    }
}
