package com.wxxtfxrmx.pirates.screen.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.wxxtfxrmx.pirates.component.TileSize;
import com.wxxtfxrmx.pirates.entity.Field;
import com.wxxtfxrmx.pirates.entity.factory.TileFactory;
import com.wxxtfxrmx.pirates.navigation.Navigation;
import com.wxxtfxrmx.pirates.screen.BaseScreen;
import com.wxxtfxrmx.pirates.system.FieldManagementSystem;

public final class LevelScreen extends BaseScreen {

    private final Navigation navigation;
    private final TileFactory factory;
    private Field board;

    public LevelScreen(TileFactory factory, Navigation navigation) {
        this.factory = factory;
        this.navigation = navigation;
    }

    @Override
    public void show() {
        super.show();

        final TileSize size = new TileSize(64, 64);
        final FieldManagementSystem fieldManagementSystem = new FieldManagementSystem(factory, 888);

        board = new Field(size, fieldManagementSystem);
        board.setSize(scene.getViewport().getWorldWidth(), scene.getViewport().getWorldHeight() * 0.5f);

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

        Gdx.app.error("TAG", "TEST");
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }
}
