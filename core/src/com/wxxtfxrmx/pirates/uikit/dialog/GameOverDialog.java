package com.wxxtfxrmx.pirates.uikit.dialog;

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.wxxtfxrmx.pirates.screen.levelv2.Constants;
import com.wxxtfxrmx.pirates.uikit.HorizontalMargin;
import com.wxxtfxrmx.pirates.uikit.Icon;
import com.wxxtfxrmx.pirates.uikit.UiButton;
import com.wxxtfxrmx.pirates.uikit.UiClickListener;

public class GameOverDialog extends UiDialog {

    public GameOverDialog(UiDialogSkin skin) {
        super(skin);
        pad(HorizontalMargin.LARGE.getValue());
        getButtonTable().defaults().height(Constants.UNIT);
        getButtonTable().defaults().width(getPrefWidth() - HorizontalMargin.LARGE.getValue() * 2);
        createExitButton();
        setModal(true);
        setMovable(false);
        setResizable(false);
    }

    private void createExitButton() {
        UiButton exitButton = new UiButton();
        exitButton.getStyle().imageUp = new TextureRegionDrawable(getSkin().getRegion(Icon.PLAY.getValue()));
        exitButton.addListener(new UiClickListener(this::hide));
        getButtonTable().add(exitButton);
    }

    @Override
    public float getPrefWidth() {
        return Constants.UNIT * (Constants.WIDTH - 2);
    }

    @Override
    public float getPrefHeight() {
        return Constants.UNIT * (Constants.MIDDLE_ROUNDED_HEIGHT);
    }
}
