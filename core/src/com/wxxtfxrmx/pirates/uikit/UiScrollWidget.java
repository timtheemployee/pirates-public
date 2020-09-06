package com.wxxtfxrmx.pirates.uikit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class UiScrollWidget extends Group {

    private final ScrollPane scroll;
    private final Table container;

    public UiScrollWidget() {

        container = new Table();
        scroll = new ScrollPane(container, new Skin(Gdx.files.internal("ui/uikit.json")));
        scroll.setFlickScroll(true);
        scroll.setScrollingDisabled(true, false);
        addActor(scroll);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        scroll.draw(batch, parentAlpha);
    }

    public void addItem(Actor actor) {


        container
                .add(actor)
                .fillX()
                .expandX()
                .left()
                .height(actor.getHeight())
                .padBottom(8f);

        container.row();
    }

    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);
        scroll.setSize(width, height);
        container.setWidth(width);
    }


}
