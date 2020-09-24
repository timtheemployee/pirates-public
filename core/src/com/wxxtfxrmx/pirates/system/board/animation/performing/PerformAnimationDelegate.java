package com.wxxtfxrmx.pirates.system.board.animation.performing;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.wxxtfxrmx.pirates.screen.level.board.Tile;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.scaleTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public final class PerformAnimationDelegate {

    private final Group parent;

    public PerformAnimationDelegate(Group parent) {
        this.parent = parent;
    }

    public void scaleDown(Actor actor, Runnable completionListener) {
        ScaleToAction scale = scaleTo(0.0f, 0.0f, 0.3f);

        if (completionListener != null) {
            RunnableAction completion = run(completionListener);
            perform(actor, scale, completion);
        } else {
            perform(actor, scale);
        }
    }

    public void scaleUp(Actor actor) {
        actor.setScale(0f);
        ScaleToAction scale = scaleTo(1f, 1f, 0.3f);

        perform(actor, scale);
    }

    public void move(Actor actor, float x, float y) {
        MoveToAction move = moveTo(x, y, 0.3f);

        perform(actor, move);
    }

    private void perform(Actor actor, Action... actions) {
        fireAnimationStart(actor);
        SequenceAction sequence = sequence(actions);
        sequence.addAction(fireAnimationCompleteAction(actor));
        actor.addAction(sequence);
    }

    private RunnableAction fireAnimationCompleteAction(Actor actor) {
        return run(() -> parent.fire(new CompleteAnimation(actor)));
    }

    private void fireAnimationStart(Actor actor) {
        parent.fire(new StartAnimation(actor));
    }

    public void remove(Actor target) {
        target.remove();
        parent.fire(new CompleteAnimation(target));
    }
}
