package com.wxxtfxrmx.pirates.screen.level;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.wxxtfxrmx.pirates.component.TileSize;
import com.wxxtfxrmx.pirates.entity.Board;
import com.wxxtfxrmx.pirates.entity.ParallaxBackground;
import com.wxxtfxrmx.pirates.entity.factory.TextureFactory;
import com.wxxtfxrmx.pirates.entity.factory.TileFactory;
import com.wxxtfxrmx.pirates.navigation.Navigation;
import com.wxxtfxrmx.pirates.screen.BaseScreen;
import com.wxxtfxrmx.pirates.system.FieldManagementSystem;

public final class LevelScreen extends BaseScreen {

    private final Navigation navigation;
    private final TileFactory tiles;
    private final TextureFactory images;
    private Board board;
    private final ParallaxBackground parallaxBackground;

    public LevelScreen(TileFactory tiles, TextureFactory images, Navigation navigation) {
        this.tiles = tiles;
        this.images = images;
        this.navigation = navigation;

        parallaxBackground = new ParallaxBackground(images.getTexture("back.png"));
    }

    @Override
    public void show() {
        super.show();

        final TileSize size = new TileSize(64, 64);
        final FieldManagementSystem fieldManagementSystem = new FieldManagementSystem(tiles, 888);

        board = new Board(size, fieldManagementSystem);
        board.setSize(scene.getViewport().getWorldWidth(), scene.getViewport().getWorldHeight() * 0.5f);

        scene.addActor(parallaxBackground);

        scene.addActor(board);
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

    @Override
    public void render(float delta) {
        super.render(delta);
    }
}
