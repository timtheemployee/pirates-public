package com.wxxtfxrmx.pirates.screen.level.battlefield;

public final class Ship {

    private final static int HP = 100;

    private int hp;
    private int cannonBallDamage;
    private float evasion;

    public Ship() {
        this.hp = 100;
        this.cannonBallDamage = 5;
        this.evasion = 0f;
    }

    public int getHp() {
        return hp;
    }

    public int getCannonBallDamage() {
        return cannonBallDamage;
    }

    public float getEvasion() {
        return evasion;
    }

    public void setHp(int hp) {
        if (hp > HP) {
            this.hp = HP;
        } else {
            this.hp = hp;
        }
    }

    public void setCannonBallDamage(int cannonBallDamage) {
        this.cannonBallDamage = cannonBallDamage;
    }

    public void setEvasion(float evasion) {
        this.evasion = evasion;
    }
}
