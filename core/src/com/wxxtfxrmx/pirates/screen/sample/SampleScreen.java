package com.wxxtfxrmx.pirates.screen.sample;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.wxxtfxrmx.pirates.screen.BaseScreen;
import com.wxxtfxrmx.pirates.uikit.UiButton;
import com.wxxtfxrmx.pirates.uikit.UiScrollWidget;

public class SampleScreen extends BaseScreen {

    private final UiButton uiButton;
    private final UiScrollWidget scrollWidget;

    public SampleScreen() {
        this.uiButton = new UiButton();

        uiButton.setSize(128, 64);
        uiButton.setPosition(0, Gdx.graphics.getHeight() - uiButton.getHeight());

        scrollWidget = new UiScrollWidget();

    }

    @Override
    public void show() {
        super.show();

        scrollWidget.setSize(Gdx.graphics.getWidth() - 16f, Gdx.graphics.getHeight() / 3f);
        scrollWidget.setPosition(8f, Gdx.graphics.getHeight() - uiButton.getHeight() - scrollWidget.getHeight());
        scene.addActor(uiButton);
        scene.addActor(scrollWidget);


        Skin skin = new Skin(Gdx.files.internal("ui/uikit.json"));
        for (char i = 'А'; i <= 'Я'; i++) {
            Label label = new Label(Character.toString(i), skin, "default");
            label.setHeight(64);

            scrollWidget.addItem(label);
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        scene.act(delta);
    }
}
