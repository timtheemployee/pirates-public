package com.wxxtfxrmx.pirates.system.battlefield.damage;

import com.badlogic.gdx.scenes.scene2d.Event;

public class Damage extends Event {
    private final int damage;

    public Damage(int damage) {
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }
}
