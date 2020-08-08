package com.wxxtfxrmx.pirates.system;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.wxxtfxrmx.pirates.component.IntegerPair;
import com.wxxtfxrmx.pirates.entity.ActorBuilder;
import com.wxxtfxrmx.pirates.entity.BaseActor;

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
        for (Actor[] row : actors) {
            for (Actor tile : row) {
                tile.act(delta);
            }
        }

        makeSwapIfPossible();
    }

    private Actor generate(final List<ActorBuilder> builders) {
        final int index = random.nextInt(builders.size());
        final ActorBuilder builder = builders.get(index);
        return builder.build();
    }

    private void makeSwapIfPossible() {
        if (pickedActor == null || targetActor == null) {
            return;
        }

        makeSwapTransaction(pickedActor, targetActor);

        pickedActor = null;
        targetActor = null;
    }

    private void makeSwapTransaction(Actor pickedActor, Actor targetActor) {
        final IntegerPair pickedActorCoordinates = getTileCoordinates(pickedActor);
        final IntegerPair targetActorCoordinates = getTileCoordinates(targetActor);

        final float pickedX = pickedActor.getX();
        final float pickedY = pickedActor.getY();

        pickedActor.setPosition(targetActor.getX(), targetActor.getY());
        targetActor.setPosition(pickedX, pickedY);

        actors[pickedActorCoordinates.first][pickedActorCoordinates.second] = targetActor;
        actors[targetActorCoordinates.first][targetActorCoordinates.second] = pickedActor;

        ((BaseActor) pickedActor).changeState();
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

    public boolean onTouchDown(float x, float y) {
        for (Actor[] value : actors) {
            for (final Actor actor : value) {
                final float startX = actor.getX();
                final float endX = actor.getX(Align.right);

                final float bottomY = actor.getY();
                final float topY = actor.getY(Align.top);

                if (x >= startX && x <= endX && y >= bottomY && y <= topY) {
                    if (pickedActor == null) {
                        pickedActor = actor;
                        ((BaseActor) pickedActor).changeState();
                    } else if (targetActor == null) {
                        if (isInPickedActorBounds(x, y)) {
                            targetActor = actor;
                        } else {
                            ((BaseActor) pickedActor).changeState();
                            pickedActor = null;
                        }
                    }

                    return true;
                }
            }
        }

        return false;
    }

    private boolean isInPickedActorBounds(float x, float y) {
        if (pickedActor == null)
            throw new IllegalStateException("Call this method after picking pickedActor");

        final float pickedStart = pickedActor.getX();
        final float pickedEnd = pickedActor.getX(Align.right);

        final float pickedBottom = pickedActor.getY();
        final float pickedTop = pickedActor.getY(Align.top);

        return isInHorizontalBounds(
                x, y,
                pickedStart, pickedEnd,
                pickedBottom, pickedTop,
                pickedActor.getWidth() // Accepted tile is a square
        ) || isInVerticalBounds(
                x, y,
                pickedStart, pickedEnd,
                pickedBottom, pickedTop,
                pickedActor.getWidth()
        );
    }

    private boolean isInHorizontalBounds(float x, float y, float tileStart, float tileEnd, float tileBottom, float tileTop, float tileSize) {
        if (y <= tileBottom || y >= tileTop) return false;

        boolean isBeforeStart = x < tileStart && x >= tileStart - tileSize;
        boolean isAfterEnd = x > tileEnd && x <= tileEnd + tileSize;

        return isBeforeStart || isAfterEnd;
    }

    private boolean isInVerticalBounds(float x, float y, float tileStart, float tileEnd, float tileBottom, float tileTop, float tileSize) {
        if (x <= tileStart || x >= tileEnd) return false;

        boolean isBeforeBottom = y < tileBottom && y >= tileBottom - tileSize;
        boolean isAfterTop = y > tileTop && y <= tileTop + tileSize;

        return isBeforeBottom || isAfterTop;
    }

    private void checkMatch() {
        for (int i = 0; i < actors.length; i++) {
            Actor[] row = actors[i];

            for (Actor tile : row) {

            }
        }
    }
}
