package com.wxxtfxrmx.pirates.uikit.dialog;

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.wxxtfxrmx.pirates.screen.levelv2.Constants;
import com.wxxtfxrmx.pirates.uikit.DefaultSkin;
import com.wxxtfxrmx.pirates.uikit.HorizontalMargin;
import com.wxxtfxrmx.pirates.uikit.Icon;
import com.wxxtfxrmx.pirates.uikit.UiButton;
import com.wxxtfxrmx.pirates.uikit.UiClickListener;
import com.wxxtfxrmx.pirates.uikit.UiLabel;

public class PauseDialog extends UiDialog {

    public PauseDialog() {
        super(new DefaultSkin());
        pad(HorizontalMargin.LARGE.getValue());
        getTitleTable().defaults().height(Constants.UNIT);
        getTitleTable().defaults().width(getPrefWidth() - HorizontalMargin.LARGE.getValue() * 2);
        getButtonTable().defaults().height(Constants.UNIT);
        getButtonTable().defaults().width(getPrefWidth() - HorizontalMargin.LARGE.getValue() * 2);
        createPauseTitle();
        createResumeButton();
        setModal(true);
        setMovable(false);
        setResizable(false);
    }

    private void createPauseTitle() {
        UiLabel label = new UiLabel("Пауза");
        label.setFontScale(1.5f);
        label.setAlignment(Align.center);

        getTitleTable().add(label);
    }

    private void createResumeButton() {
        UiButton resumeButton = new UiButton();
        resumeButton.getStyle().imageUp = new TextureRegionDrawable(getSkin().getRegion(Icon.PLAY.getValue()));

        resumeButton.addListener(new UiClickListener(new UiClickListener.OnClickListener() {
            @Override
            public void onClick() {
                hide();
            }
        }));

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
