package com.wxxtfxrmx.pirates.screen.level.battlefield;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;
import com.wxxtfxrmx.pirates.event.ExternalEvent;
import com.wxxtfxrmx.pirates.event.ExternalEventBridge;
import com.wxxtfxrmx.pirates.system.battlefield.damage.ApplyDamageSystem;
import com.wxxtfxrmx.pirates.system.battlefield.match.MatchAccumulationSystem;
import com.wxxtfxrmx.pirates.uikit.UiLabel;

public final class BattleField extends Group {

    private final BattleContext context;
    private final UiLabel shipLabel;

    private final MatchAccumulationSystem matchAccumulation;
    private final ApplyDamageSystem applyDamageSystem;

    public BattleField() {
        this.context = new BattleContext();
        this.shipLabel = new UiLabel(context.getTurn().toString());
        this.matchAccumulation = new MatchAccumulationSystem(this);
        this.applyDamageSystem = new ApplyDamageSystem(context);
        addActor(shipLabel);

        addListener(this::handleEvent);
    }

    private boolean handleEvent(Event event) {
        if (matchAccumulation.handle(event)) {
            return true;
        }

        if (applyDamageSystem.handle(event)) {
            return true;
        }

        return false;
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
