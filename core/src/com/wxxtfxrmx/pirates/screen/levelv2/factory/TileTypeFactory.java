package com.wxxtfxrmx.pirates.screen.levelv2.factory;

import com.wxxtfxrmx.pirates.screen.levelv2.layer.board.world.TileType;

import java.util.Random;

public class TileTypeFactory {

    private final Random random;
    private final TileType[] typeValues = TileType.values();

    public TileTypeFactory(Random random) {
        this.random = random;
    }

    public TileType getRandomType() {
        return typeValues[random.nextInt(typeValues.length)];
    }
}
