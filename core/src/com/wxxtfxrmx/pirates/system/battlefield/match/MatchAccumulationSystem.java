package com.wxxtfxrmx.pirates.system.battlefield.match;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.wxxtfxrmx.pirates.screen.level.board.TileType;
import com.wxxtfxrmx.pirates.system.System;
import com.wxxtfxrmx.pirates.system.battlefield.damage.Damage;

import java.util.Map;

public final class MatchAccumulationSystem implements System {

    private final Group parent;

    public MatchAccumulationSystem(Group parent) {
        this.parent = parent;
    }

    @Override
    public boolean handle(Event event) {
        if (event instanceof MatchedTiles) {
            MatchedTiles matched = (MatchedTiles) event;
            Map<TileType, Integer> info = matched.getMatchedTiles();

            if (info.containsKey(TileType.BOMB)) {
                applyDamage(info.get(TileType.BOMB));
            }

            if (info.containsKey(TileType.HELM)) {
                applyEvasion(info.get(TileType.HELM));
            }

            if (info.containsKey(TileType.REPAIR)) {
                applyRepair(info.get(TileType.REPAIR));
            }

            if (info.containsKey(TileType.COIN)) {
                applyCoin(info.get(TileType.COIN));
            }

            return true;
        }

        return false;
    }

    //TODO получение монет атакующим кораблем
    private void applyCoin(int coin) {

    }

    //TODO получение урона атакуемым кораблем
    private void applyDamage(int damage) {
        parent.fire(new Damage(damage));
    }

    //TODO получение уклонения атакующим кораблем
    private void applyEvasion(int evasion) {
    }

    //TODO получение восстанавления хп атакующим кораблем
    private void applyRepair(int repair) {

    }
}
