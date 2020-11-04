package com.wxxtfxrmx.pirates.uikit.dialog;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;

public abstract class UiDialog extends Dialog {

    private OnDialogHideListener hideListener;
    private OnDialogShowListener showListener;

    public UiDialog(UiDialogSkin skin) {
        super("", skin);
    }

    public void setHideListener(OnDialogHideListener listener) {
        this.hideListener = listener;
    }

    public void setShowListener(OnDialogShowListener listener) {
        this.showListener = listener;
    }

    @Override
    public void hide() {
        if (hideListener != null) {
            hideListener.onHide();
        }

        super.hide();
    }

    @Override
    public Dialog show(Stage stage) {
        if (showListener != null) {
            showListener.onShow();
        }

        return super.show(stage);
    }

    public interface OnDialogHideListener {
        void onHide();
    }

    public interface OnDialogShowListener {
        void onShow();
    }
}
