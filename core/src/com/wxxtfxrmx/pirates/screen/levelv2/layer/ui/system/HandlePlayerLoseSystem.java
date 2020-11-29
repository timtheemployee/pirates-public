package com.wxxtfxrmx.pirates.screen.levelv2.layer.ui.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.CoinComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.PlayerComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.PlayerLoseComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.ui.component.LevelContextComponent;
import com.wxxtfxrmx.pirates.uikit.dialog.GameOverDialog;

public class HandlePlayerLoseSystem extends IteratingSystem {

    private final GameOverDialog dialog;
    private final Stage stage;

    private final Family playerCoinsFamily = Family.all(CoinComponent.class, PlayerComponent.class).get();
    private final ComponentMapper<CoinComponent> coinsMapper = ComponentMapper.getFor(CoinComponent.class);
    private final Family levelContextFamily = Family.all(LevelContextComponent.class).get();
    private final ComponentMapper<LevelContextComponent> levelContextMapper = ComponentMapper.getFor(LevelContextComponent.class);

    public HandlePlayerLoseSystem(GameOverDialog dialog, Stage stage) {
        super(Family.all(
                PlayerLoseComponent.class
        ).get());

        this.dialog = dialog;
        this.stage = stage;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Entity coinsEntity = getEngine().getEntitiesFor(playerCoinsFamily).first();
        Entity levelContextEntity = getEngine().getEntitiesFor(levelContextFamily).first();

        int coins = coinsMapper.get(coinsEntity).value;
        int shipDestroyed = levelContextMapper.get(levelContextEntity).shipDestroyed;

        dialog.show(stage, coins, shipDestroyed);
        getEngine().removeEntity(entity);
    }
}
