package com.wxxtfxrmx.pirates.uikit.slot;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.wxxtfxrmx.pirates.screen.levelv2.Constants;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.board.world.TileType;
import com.wxxtfxrmx.pirates.uikit.UiLabel;

import java.util.Random;

public class UiSlotMachine extends Table {

    private final SlotSkin slotSkin;
    private final Image first = new Image();
    private final Image second = new Image();
    private final Image third = new Image();
    private final Random random = new Random();
    private TileType firstType;
    private TileType secondType;
    private TileType thirdType;

    private OnSpinCompleteListener listener;

    private float currentShuffleTime = 0f;
    private float delayTime = 0f;

    public UiSlotMachine() {
        slotSkin = new SlotSkin();
        setSize(Constants.UNIT * 5, Constants.UNIT * 3);
        configureTitle();
        configureUi();

        firstType = getRandomType();
        secondType = getRandomType();
        thirdType = getRandomType();

        configureImage(first, firstType);
        configureImage(second, secondType);
        configureImage(third, thirdType);

        add(first).colspan(1);
        add(second).colspan(1);
        add(third).colspan(1).expandY();
        row();
    }

    private void updateImage(Image image, TileType tileType) {
        Drawable drawable = slotSkin.getIcon(tileType);
        image.setDrawable(drawable);
    }

    private TileType getRandomType() {
        return TileType.values()[random.nextInt(TileType.values().length)];
    }

    private void configureImage(Image image, TileType tileType) {
        Drawable drawable = slotSkin.getIcon(tileType);
        image.setDrawable(drawable);
        image.setSize(Constants.UNIT, Constants.UNIT);
    }

    private void configureUi() {
        setBackground(slotSkin.getBackground());
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x - getWidth() / 2, y - getHeight() / 2);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (currentShuffleTime <= 1f) {
            currentShuffleTime += delta;
            firstType = getRandomType();
            secondType = getRandomType();
            thirdType = getRandomType();

            updateImage(first, firstType);
            updateImage(second, secondType);
            updateImage(third, thirdType);
        } else if (delayTime <= 1f) {
            delayTime += delta;
        } else {
            processSpin();
            remove();
        }
    }

    private void processSpin() {
        if (listener == null) return;

        if (firstType == secondType && secondType == thirdType) {
            listener.onMatchSuccess(firstType);
        } else {
            listener.onMatchFailure();
        }
    }

    private void configureTitle() {
        UiLabel label = new UiLabel("LUCKY SLOTS");
        label.getStyle().background = null;
        label.getStyle().font.getData().setScale(0.5f);
        add(label).colspan(3).expandY();
        row();
    }

    public interface OnSpinCompleteListener {
        void onMatchSuccess(TileType matchedType);
        void onMatchFailure();
    }
}