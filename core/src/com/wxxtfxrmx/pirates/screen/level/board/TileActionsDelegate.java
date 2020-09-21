package com.wxxtfxrmx.pirates.screen.level.board;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

import java.util.Locale;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.scaleTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public final class TileActionsDelegate {

    private final GridContext context;
    private int actionsCount = 0;

    public TileActionsDelegate(GridContext context) {
        this.context = context;
    }

    public void match(Actor actor, Runnable completionListener) {
        ScaleToAction scale = scaleTo(0.0f, 0.0f, 0.3f);

        if (completionListener != null) {
            RunnableAction completion = run(completionListener);
            perform(actor, scale, completion);
        } else {
            perform(actor, scale);
        }
    }

    public void create(Actor actor) {
        actor.setScale(0f);
        ScaleToAction scale = scaleTo(1f, 1f, 0.3f);

        perform(actor, scale);
    }

    public void move(Actor actor, float x, float y) {
        MoveToAction move = moveTo(x, y, 0.3f);

        perform(actor, move);
    }

    private void perform(Actor actor, Action... actions) {
        Gdx.app.error("TAG", String.format(Locale.ENGLISH, "PERFORMING ACTIONS CONTEXT %d", actionsCount));
        RunnableAction increase = run(this::updateActionsCount);
        RunnableAction decreaseAndUpdate = run(this::updateGridContext);
        SequenceAction sequence = sequence();
        sequence.addAction(increase);
        for (Action action: actions) {
            sequence.addAction(action);
        }
        sequence.addAction(decreaseAndUpdate);
        actor.addAction(sequence);
        Gdx.app.error("", "");
    }

    private void updateActionsCount() {
        actionsCount += 1;
    }

    private void updateGridContext() {
        actionsCount -= 1;
        Gdx.app.error("TAG", String.format(Locale.ENGLISH, "UPDATING GRID CONTEXT %d", actionsCount));
        context.setLockedUntilAnimation(actionsCount != 0);
    }
}
