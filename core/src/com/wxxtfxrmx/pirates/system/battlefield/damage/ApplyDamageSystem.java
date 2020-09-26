package com.wxxtfxrmx.pirates.system.battlefield.damage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.wxxtfxrmx.pirates.screen.level.battlefield.BattleContext;
import com.wxxtfxrmx.pirates.screen.level.battlefield.Ship;
import com.wxxtfxrmx.pirates.system.System;

import java.util.Locale;

public class ApplyDamageSystem implements System {

    private final BattleContext context;

    public ApplyDamageSystem(BattleContext context) {
        this.context = context;
    }

    @Override
    public boolean handle(Event event) {
        if (event instanceof Damage) {
            Damage damage = (Damage) event;
            apply(context, damage);
            return true;
        }

        return false;
    }

    private void apply(BattleContext context, Damage damage) {
        Ship attacked = context.getAttacked();
        Ship attacker = context.getAttacker();

        int newHp = attacked.getHp() - damage.getDamage() * attacker.getCannonBallDamage();

        Gdx.app.error("VERIFY DAMAGE", String.format(Locale.ENGLISH, "VERIFY (TURN %s) %d = %d - %d * %d", context.getTurn(), newHp, attacked.getHp(), damage.getDamage(), attacker.getCannonBallDamage()));

        attacker.setHp(newHp);
    }
}
