package com.wxxtfxrmx.pirates.uikit.dialog;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.wxxtfxrmx.pirates.screen.levelv2.Constants;
import com.wxxtfxrmx.pirates.uikit.HorizontalMargin;
import com.wxxtfxrmx.pirates.uikit.Icon;
import com.wxxtfxrmx.pirates.uikit.UiButton;
import com.wxxtfxrmx.pirates.uikit.UiClickListener;

public class PauseDialog extends Dialog {

    private OnDialogHideListener hideListener;
    private OnDialogShowListener showListener;

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

    //TODO Override it in base dialog
    @Override
    public void hide() {
        if (hideListener != null) {
            hideListener.onHide();
        }

        super.hide();
    }

    //TODO Override it in base dialog
    @Override
    public Dialog show(Stage stage) {
        if (showListener != null) {
            showListener.onShow();
        }

        return super.show(stage);
    }

    //TODO Remove it to base dialog
    public void setOnHideListener(OnDialogHideListener listener) {
        this.hideListener = listener;
    }

    //TODO Remove it to base dialog
    public void setOnShowListener(OnDialogShowListener listener) {
        this.showListener = listener;
    }

    @Override
    public float getPrefWidth() {
        return Constants.UNIT * (Constants.WIDTH - 2);
    }

    @Override
    public float getPrefHeight() {
        return Constants.UNIT * (Constants.HEIGHT - 4);
    }

    public interface OnDialogHideListener {
        void onHide();
    }

    public interface OnDialogShowListener {
        void onShow();
    }
}
