package com.wxxtfxrmx.pirates.uikit;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;

public interface UIComponent {

    void attachTo(Group group);

    void attachTo(Stage stage);

    void detach();
}
