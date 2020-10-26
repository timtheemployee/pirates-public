package com.wxxtfxrmx.pirates.uikit.dialog;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.wxxtfxrmx.pirates.screen.levelv2.Constants;
import com.wxxtfxrmx.pirates.uikit.HorizontalMargin;
import com.wxxtfxrmx.pirates.uikit.Icon;
import com.wxxtfxrmx.pirates.uikit.UiButton;
import com.wxxtfxrmx.pirates.uikit.UiClickListener;

public class PauseDialog extends Dialog {

    public PauseDialog(UiDialogSkin skin) {
        super("", skin);
        pad(HorizontalMargin.LARGE.getValue());
        getButtonTable().defaults().height(Constants.UNIT);
        getButtonTable().defaults().width(getPrefWidth() - HorizontalMargin.LARGE.getValue() * 2);
        createResumeButton();
        setModal(true);
        setMovable(false);
        setResizable(false);
    }

    private void createResumeButton() {
        UiButton resumeButton = new UiButton();
        resumeButton.getStyle().imageUp = new TextureRegionDrawable(getSkin().getRegion(Icon.PLAY.getValue()));
        resumeButton.addListener(new UiClickListener(this::hide));
        getButtonTable().add(resumeButton);
    }

    @Override
    public float getPrefWidth() {
        return Constants.UNIT * (Constants.WIDTH - 2);
    }

    @Override
    public float getPrefHeight() {
        return Constants.UNIT * (Constants.HEIGHT - 4);
    }
}
