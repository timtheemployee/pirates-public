package com.wxxtfxrmx.pirates.uikit.dialog;

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
        UiLabel killedShips = new UiLabel("Убитых кораблей");
        killedShips.setAlignment(Align.center);
        UiLabel killedShipsCount = new UiLabel("0");
        killedShipsCount.setAlignment(Align.center);

        UiLabel coinsEarned = new UiLabel("Монет заработано");
        coinsEarned.setAlignment(Align.center);
        UiLabel coinsEarnedCount = new UiLabel("0");
        coinsEarnedCount.setAlignment(Align.center);

        getContentTable()
                .add(killedShips)
                .padLeft(HorizontalMargin.SMALL.getValue())
                .padRight(HorizontalMargin.SMALL.getValue())
                .fillX();

        getContentTable()
                .add(killedShipsCount)
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
        UiLabel label = new UiLabel("Игра окончена");
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

    @Override
    public float getPrefWidth() {
        return Constants.UNIT * (Constants.WIDTH - 2);
    }

    @Override
    public float getPrefHeight() {
        return Constants.UNIT * (Constants.MIDDLE_ROUNDED_HEIGHT);
    }
}
