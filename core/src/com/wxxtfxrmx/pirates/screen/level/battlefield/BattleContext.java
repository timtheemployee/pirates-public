package com.wxxtfxrmx.pirates.screen.level.battlefield;

import java.util.ArrayList;
import java.util.List;

public final class BattleContext {

    private final List<Chain> lastChain = new ArrayList<>();
    private Turn turn = Turn.PLAYER;
    private final Ship ai;
    private final Ship player;

    private boolean hasWinner = false;

    public BattleContext() {
        ai = new Ship();
        player = new Ship();
    }

    public void updateChains(List<Chain> updatedChains) {
        lastChain.clear();
        lastChain.addAll(updatedChains);
    }

    public List<Chain> getLastChain() {
        return lastChain;
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

    public void setHasWinner(boolean hasWinner) {
        this.hasWinner = hasWinner;
    }

    public boolean isHasWinner() {
        return hasWinner;
    }

    public void switchShips() {
        turn = turn == Turn.PLAYER ? Turn.AI : Turn.PLAYER;
    }
}
