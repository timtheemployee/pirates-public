package com.wxxtfxrmx.pirates.uikit.slot;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.wxxtfxrmx.pirates.screen.levelv2.Constants;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.board.world.TileType;
import com.wxxtfxrmx.pirates.uikit.DefaultSkin;
import com.wxxtfxrmx.pirates.uikit.UiLabel;

import java.util.Random;

public class UiSlotMachine extends Table {

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
        setSkin(new DefaultSkin());
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

    public void setListener(OnSpinCompleteListener listener) {
        this.listener = listener;
    }

    private void updateImage(Image image, TileType tileType) {
        TextureRegion region = getSkin().getRegion(tileType.getAtlasPath());
        TextureRegion iconRegion = new TextureRegion(region, 0, 0, Constants.UNIT, Constants.UNIT);
        image.setDrawable(new TextureRegionDrawable(iconRegion));
    }

    private TileType getRandomType() {
        return TileType.values()[random.nextInt(TileType.values().length)];
    }

    private void configureImage(Image image, TileType tileType) {
        updateImage(image, tileType);
        image.setSize(Constants.UNIT, Constants.UNIT);
    }

    private void configureUi() {
        NinePatch patch = getSkin().getPatch("pane-background");
        NinePatchDrawable patchDrawable = new NinePatchDrawable(patch);
        setBackground(patchDrawable);
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
            currentShuffleTime = 0f;
            delayTime = 0f;
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
        add(label).colspan(3).expandY();
        row();
    }

    public interface OnSpinCompleteListener {
        void onMatchSuccess(TileType matchedType);

        void onMatchFailure();
    }
}