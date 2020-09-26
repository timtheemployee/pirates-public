package com.wxxtfxrmx.pirates.screen.level.battlefield;

public final class BattleContext {

    private Turn turn = Turn.PLAYER;
    private final Ship ai;
    private final Ship player;

    public BattleContext() {
        ai = new Ship();
        player = new Ship();
    }

    public Turn getTurn() {
        return turn;
    }

    public Ship getAttacked() {
        return turn == Turn.PLAYER ? ai : player;
    }

    public Ship getAttacker() {
        return turn == Turn.PLAYER ? player : ai;
    }

    public void setOppositeTurn(Turn lastTurn) {
        turn = lastTurn == Turn.PLAYER ? Turn.AI : Turn.PLAYER;
    }
}
