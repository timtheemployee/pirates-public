package com.wxxtfxrmx.pirates.uikit.slot;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.wxxtfxrmx.pirates.screen.levelv2.Constants;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.board.world.TileType;
import com.wxxtfxrmx.pirates.uikit.DefaultSkin;
import com.wxxtfxrmx.pirates.uikit.HorizontalMargin;
import com.wxxtfxrmx.pirates.uikit.UiLabel;
import com.wxxtfxrmx.pirates.uikit.dialog.UiDialog;

import java.util.Random;

public class UiSlotMachine extends UiDialog {

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
        super(new DefaultSkin());
        pad(HorizontalMargin.LARGE.getValue());
        getTitleTable().defaults().height(Constants.UNIT);
        getTitleTable().defaults().width(getPrefWidth() - HorizontalMargin.LARGE.getValue() * 2);
        getButtonTable().defaults().height(Constants.UNIT);
        getButtonTable().defaults().width(getPrefWidth() - HorizontalMargin.LARGE.getValue() * 2);
        configureTitle();
        configureUi();

        firstType = getRandomType();
        secondType = getRandomType();
        thirdType = getRandomType();

        configureImage(first, firstType);
        configureImage(second, secondType);
        configureImage(third, thirdType);

        getContentTable().add(first).colspan(1);
        getContentTable().add(second).colspan(1);
        getContentTable().add(third).colspan(1).expandY();
        getContentTable().row();
        setModal(true);
        setMovable(false);
        setResizable(false);
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
    public void act(float delta) {
        super.act(delta);

        if (!ascendantsVisible()) {
            return;
        }

        if (currentShuffleTime <= 1f) {
            currentShuffleTime += delta;
            firstType = getRandomType();
            secondType = getRandomType();
            thirdType = getRandomType();

            updateImage(first, firstType);
            updateImage(second, secondType);
            updateImage(third, thirdType);
        } else if (delayTime <= 1.4f) {
            delayTime += delta;
        } else {
            currentShuffleTime = 0f;
            delayTime = 0f;
            processSpin();
            hide();
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
        label.setFontScale(1.5f);
        label.setAlignment(Align.center);
        getTitleTable().add(label).center();
    }

    @Override
    public float getPrefWidth() {
        //Three in row and paddings
        return Constants.UNIT * (Constants.WIDTH - 2);
    }

    @Override
    public float getPrefHeight() {
        //Title, three in row and bottom
        return Constants.UNIT * 3;
    }

    public interface OnSpinCompleteListener {
        void onMatchSuccess(TileType matchedType);

        void onMatchFailure();
    }
}