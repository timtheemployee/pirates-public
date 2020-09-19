package com.wxxtfxrmx.pirates.screen.level.battlefield;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;
import com.wxxtfxrmx.pirates.uikit.UiLabel;

public final class BattleField extends Group {

    private final BattleContext context;

    private final UiLabel shipLabel;

    public BattleField(final BattleContext context) {
        this.context = context;
        this.shipLabel = new UiLabel(context.getTurn().toString());
        addActor(shipLabel);
    }

    @Override
    protected void setStage(Stage stage) {
        super.setStage(stage);
        stage.addActor(shipLabel);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        shipLabel.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        shipLabel.setText(context.getTurn().toString());
        shipLabel.act(delta);
    }

    @Override
    public void setBounds(float x, float y, float width, float height) {
        super.setBounds(x, y, width, height);
        shipLabel.setPosition(getX(Align.center) - shipLabel.getWidth() / 2, getY(Align.center) - shipLabel.getHeight() / 2);
    }
}
