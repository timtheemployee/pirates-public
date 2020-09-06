package com.wxxtfxrmx.pirates.screen.level.battlefield;

public final class Ship {

    private int hp;
    private int cannonBallDamage;
    private int evasion;

    public Ship() {
        this.hp = 100;
        this.cannonBallDamage = 5;
        this.evasion = 0;
    }

    public int getHp() {
        return hp;
    }

    public int getCannonBallDamage() {
        return cannonBallDamage;
    }

    public int getEvasion() {
        return evasion;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setCannonBallDamage(int cannonBallDamage) {
        this.cannonBallDamage = cannonBallDamage;
    }

    public void setEvasion(int evasion) {
        this.evasion = evasion;
    }
}
