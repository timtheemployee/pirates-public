package com.wxxtfxrmx.pirates.screen.levelv2.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.wxxtfxrmx.pirates.screen.level.board.TileType;

import java.util.EnumMap;

public class TileAnimationComponent implements Component {
    public EnumMap<TileType, Animation<TextureRegion>> animations = new EnumMap<>(TileType.class);
}
