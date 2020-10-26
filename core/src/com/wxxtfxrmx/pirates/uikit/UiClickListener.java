package com.wxxtfxrmx.pirates.uikit;


import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class UiClickListener extends ClickListener {

    private final UiClickListener.OnClickListener listener;

    public UiClickListener(UiClickListener.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        listener.onClick();
    }

    public interface OnClickListener {
        void onClick();
    }
}
