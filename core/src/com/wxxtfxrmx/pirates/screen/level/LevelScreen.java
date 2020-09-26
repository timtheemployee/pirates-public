package com.wxxtfxrmx.pirates.screen.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.wxxtfxrmx.pirates.component.TileSize;
import com.wxxtfxrmx.pirates.entity.ParallaxBackground;
import com.wxxtfxrmx.pirates.entity.factory.TextureFactory;
import com.wxxtfxrmx.pirates.entity.factory.TileFactory;
import com.wxxtfxrmx.pirates.navigation.Navigation;
import com.wxxtfxrmx.pirates.screen.BaseScreen;
import com.wxxtfxrmx.pirates.screen.level.battlefield.BattleField;
import com.wxxtfxrmx.pirates.screen.level.board.Board;
import com.wxxtfxrmx.pirates.screen.level.hud.LevelHud;

import java.util.Random;

public final class LevelScreen extends BaseScreen {

    private final Navigation navigation;

    private final BattleField field;
    private final Board board;
    private final LevelHud hud;
    private final ParallaxBackground parallaxBackground;


    public LevelScreen(TileFactory tiles, TextureFactory images, Navigation navigation) {
        this.navigation = navigation;

        parallaxBackground = new ParallaxBackground(
                images.getTexture("clouds_front.png"),
                images.getTexture("clouds_back.png"),
                images.getTexture("water_sprite.png")
        );


        Random random = new Random(888);
        final TileSize size = new TileSize(64, 64);
        board = new Board(size, tiles, random);
        field = new BattleField();

        board.setBridge(field::fire);
        field.setBridge(board::fire);

        hud = new LevelHud();
    }

    @Override
    public void show() {
        super.show();
        applyViewport(scene.getViewport());
        scene.addActor(parallaxBackground);
        scene.addActor(board);
        scene.addActor(field);
        scene.addActor(hud);

        scene.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (board.onTouchDown(x, y)) {
                    return true;
                }

                return super.touchDown(event, x, y, pointer, button);
            }
        });

    }

    private void applyViewport(Viewport viewport) {
        board.setBounds(0, 0, viewport.getWorldWidth(), viewport.getWorldHeight() * 0.5f);
        field.setBounds(0, viewport.getWorldHeight() * 0.5f, viewport.getWorldWidth(), viewport.getWorldHeight() * 0.5f - 64);
        parallaxBackground.setBounds(0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        hud.setBounds(0, viewport.getWorldHeight() - 64, viewport.getWorldWidth(), viewport.getWorldHeight() - 64);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.18f, 0.74f, 1f, 1f);
        super.render(delta);
    }
}
