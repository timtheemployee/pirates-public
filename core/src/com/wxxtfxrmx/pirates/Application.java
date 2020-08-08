package com.wxxtfxrmx.pirates;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.wxxtfxrmx.pirates.component.Size;
import com.wxxtfxrmx.pirates.component.TileSize;
import com.wxxtfxrmx.pirates.entity.ActorBuilder;
import com.wxxtfxrmx.pirates.entity.Field;
import com.wxxtfxrmx.pirates.entity.bomb.BombBuilder;
import com.wxxtfxrmx.pirates.entity.coin.CoinBuilder;
import com.wxxtfxrmx.pirates.entity.helm.HelmBuilder;
import com.wxxtfxrmx.pirates.entity.sample.SampleBuilder;
import com.wxxtfxrmx.pirates.system.AnimationsSystem;
import com.wxxtfxrmx.pirates.system.FieldManagementSystem;

import java.util.ArrayList;
import java.util.List;

import static com.badlogic.gdx.Application.LOG_DEBUG;

public class Application extends ApplicationAdapter {
    private Stage scene;

    private final AnimationsSystem animationsSystem;
    private FieldManagementSystem fieldManagementSystem;
    private Field field;

    public Application() {
        animationsSystem = new AnimationsSystem();
    }

    private List<ActorBuilder> createActorBuilders() {
        final List<ActorBuilder> builders = new ArrayList<>();

        builders.add(
                new CoinBuilder(
                        animationsSystem.getAnimation("coin_sheet.png")
                )
        );

        builders.add(
                new BombBuilder(
                        animationsSystem.getAnimation("bomb_sheet.png", new Size(9, 1))
                )
        );

        builders.add(
                new SampleBuilder(
                        animationsSystem.getAnimation("sample.png", new Size(1, 1))
                )
        );

        builders.add(
                new HelmBuilder(
                        animationsSystem.getAnimation("helm_sheet.png", new Size(3, 1))
                )
        );

        return builders;
    }

    @Override
    public void create() {
        Gdx.app.setLogLevel(LOG_DEBUG);

        final List<ActorBuilder> builders = createActorBuilders();
        fieldManagementSystem = new FieldManagementSystem(
                builders,
                900L
        );

        final TileSize tileSize = new TileSize(
                64, 64
        );

        field = new Field(tileSize, fieldManagementSystem);

        scene = new Stage(new ScreenViewport());

        Gdx.input.setInputProcessor(scene);

        field.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() * 0.5f);
        scene.addActor(field);

        scene.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (field.onTouchDown(x, y)) {
                    return true;
                }

                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        scene.act();
        scene.draw();
    }

    @Override
    public void dispose() {
        scene.dispose();
        animationsSystem.release();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        scene.getViewport().update(width, height);
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }
}
