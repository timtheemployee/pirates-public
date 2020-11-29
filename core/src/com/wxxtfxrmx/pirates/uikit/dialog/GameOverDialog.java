package com.wxxtfxrmx.pirates.uikit.dialog;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.wxxtfxrmx.pirates.screen.levelv2.Constants;
import com.wxxtfxrmx.pirates.uikit.DefaultSkin;
import com.wxxtfxrmx.pirates.uikit.HorizontalMargin;
import com.wxxtfxrmx.pirates.uikit.Icon;
import com.wxxtfxrmx.pirates.uikit.UiButton;
import com.wxxtfxrmx.pirates.uikit.UiClickListener;
import com.wxxtfxrmx.pirates.uikit.UiLabel;

public class GameOverDialog extends UiDialog {

    private int shipDestroyed = 0;
    private int coinExists = 0;
    private UiLabel destroyedShipsCount;
    private UiLabel coinsEarnedCount;

    public GameOverDialog() {
        super(new DefaultSkin());
        pad(HorizontalMargin.LARGE.getValue());
        getTitleTable().defaults().height(Constants.UNIT);
        getTitleTable().defaults().width(getPrefWidth() - HorizontalMargin.LARGE.getValue() * 2);
        getButtonTable().defaults().height(Constants.UNIT);
        getButtonTable().defaults().width(getPrefWidth() - HorizontalMargin.LARGE.getValue() * 2);
        createGameOverTitle();
        createContent();
        createExitButton();
        setModal(true);
        setMovable(false);
        setResizable(false);
    }

    private void createContent() {
        UiLabel destroyedShips = new UiLabel("Убитых кораблей");
        destroyedShips.setAlignment(Align.center);
        destroyedShipsCount = new UiLabel(String.valueOf(shipDestroyed));
        destroyedShipsCount.setAlignment(Align.center);

        UiLabel coinsEarned = new UiLabel("Монет заработано");
        coinsEarned.setAlignment(Align.center);
        coinsEarnedCount = new UiLabel(String.valueOf(coinExists));
        coinsEarnedCount.setAlignment(Align.center);

        getContentTable()
                .add(destroyedShips)
                .padLeft(HorizontalMargin.SMALL.getValue())
                .padRight(HorizontalMargin.SMALL.getValue())
                .fillX();

        getContentTable()
                .add(destroyedShipsCount)
                .padLeft(HorizontalMargin.SMALL.getValue())
                .padRight(HorizontalMargin.SMALL.getValue());

        getContentTable().row();

        getContentTable()
                .add(coinsEarned)
                .padLeft(HorizontalMargin.SMALL.getValue())
                .padRight(HorizontalMargin.SMALL.getValue());

        getContentTable()
                .add(coinsEarnedCount)
                .padLeft(HorizontalMargin.SMALL.getValue())
                .padRight(HorizontalMargin.SMALL.getValue());
    }

    private void createGameOverTitle() {
        UiLabel label = new UiLabel("Вас потопили");
        label.setFontScale(1.5f);
        label.setAlignment(Align.center);
        getTitleTable().add(label).center();
    }

    private void createExitButton() {
        UiButton exitButton = new UiButton();
        exitButton.getStyle().imageUp = new TextureRegionDrawable(getSkin().getRegion(Icon.PLAY.getValue()));
        exitButton.addListener(new UiClickListener(new UiClickListener.OnClickListener() {
            @Override
            public void onClick() {
                hide();
            }
        }));
        getButtonTable().add(exitButton);
    }

    public Dialog show(Stage stage, int coinsExists, int shipDestroyed) {
        this.coinExists = coinsExists;
        this.shipDestroyed = shipDestroyed;

        coinsEarnedCount.setText(String.valueOf(coinsExists));
        destroyedShipsCount.setText(String.valueOf(shipDestroyed));
        return super.show(stage);
    }

    @Override
    public float getPrefWidth() {
        return Constants.UNIT * (Constants.WIDTH - 2);
    }

    @Override
    public float getPrefHeight() {
        return Constants.UNIT * (Constants.MIDDLE_ROUNDED_HEIGHT);
    }
}
