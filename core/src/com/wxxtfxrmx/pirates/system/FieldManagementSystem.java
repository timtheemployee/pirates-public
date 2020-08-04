package com.wxxtfxrmx.pirates.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.wxxtfxrmx.pirates.component.IntegerPair;
import com.wxxtfxrmx.pirates.entity.ActorBuilder;
import java.util.List;
import java.util.Random;

public final class FieldManagementSystem {

    private final List<ActorBuilder> builders;
    private final Random random;
    private Actor[][] actors;

    private Actor pickedActor = null;
    private Actor targetActor = null;

    public FieldManagementSystem(final List<ActorBuilder> tilesBuilders,
                                 final long fieldSsid) {

        this.builders = tilesBuilders;
        this.random = new Random(fieldSsid);
    }

    public void setSize(final int tilesInColumn, final int tilesInRow) {
        actors = new Actor[tilesInColumn][tilesInRow];

        for (int i = 0; i < tilesInColumn; i++) {
            actors[i] = generate(tilesInRow);
        }
    }

    public Actor getTile(final float x, final float y) {
        int column = (int) x / 64;
        int row = (int) y / 64;

        return actors[column][row];
    }

    private Actor[] generate(final int tilesInRow) {
        final Actor[] row = new Actor[tilesInRow];

        for (int i = 0; i < tilesInRow; i++) {
            row[i] = generate(builders);
        }

        return row;
    }

    //FIXME: Перерисовывать когда были совершены изменения
    public void act(float delta) {
        for (Actor[] row: actors) {
            for (Actor tile : row) {
                tile.act(delta);
            }
        }
    }

    private Actor generate(final List<ActorBuilder> builders) {
        final int index = random.nextInt(builders.size());
        final ActorBuilder builder = builders.get(index);

        final Actor tile = builder.build();
        tile.setTouchable(Touchable.enabled);
        tile.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                final Actor touchedActor = event.getListenerActor();
                Gdx.app.error("CLICKED", "CLICKED");
                if (pickedActor == null) {
                    pickedActor = touchedActor;
                } else {
                    targetActor = touchedActor;
                }

                makeSwapIfPossible();
            }
        });

        return tile;
    }

    private void makeSwapIfPossible() {
        if (pickedActor == null || targetActor == null) {
            throw new IllegalStateException("Swap only picked actors");
        }

        final MoveToAction pickedAction = new MoveToAction();
        pickedAction.setPosition(targetActor.getX(), targetActor.getY());
        pickedAction.setDuration(300);
        pickedAction.setInterpolation(Interpolation.smooth);

        final MoveToAction targetAction = new MoveToAction();
        targetAction.setPosition(pickedActor.getX(), pickedActor.getY());
        targetAction.setDuration(300);
        targetAction.setInterpolation(Interpolation.smooth);

        final IntegerPair pickedActorCoordinates = getTileCoordinates(pickedActor);
        final IntegerPair targetActorCoordinates = getTileCoordinates(targetActor);

        actors[pickedActorCoordinates.first][pickedActorCoordinates.second] = targetActor;
        actors[targetActorCoordinates.first][targetActorCoordinates.second] = pickedActor;

        pickedActor.addAction(pickedAction);
        targetActor.addAction(targetAction);
    }

    private IntegerPair getTileCoordinates(Actor tile) {
        for (int i = 0; i < actors.length; i++) {
            final Actor[] column = actors[i];
            for (int j = 0; j < column.length; j++) {
                if (actors[i][j] == tile) {
                    return new IntegerPair(i, j);
                }
            }
        }

        throw new IllegalStateException("No tile in field");
    }
}
