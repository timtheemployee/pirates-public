package com.wxxtfxrmx.pirates.screen.start;

import com.wxxtfxrmx.pirates.navigation.Destination;
import com.wxxtfxrmx.pirates.navigation.Navigator;
import com.wxxtfxrmx.pirates.screen.BaseScreen;
import com.wxxtfxrmx.pirates.screen.levelv2.Constants;
import com.wxxtfxrmx.pirates.uikit.Icon;
import com.wxxtfxrmx.pirates.uikit.UiButton;
import com.wxxtfxrmx.pirates.uikit.UiClickListener;

public class StartScreen extends BaseScreen {

    private final UiButton button = new UiButton();

    public StartScreen(Navigator navigator) {
        super(navigator);
    }

    @Override
    public void show() {
        super.show();
        button.setIcon(Icon.PLAY);
        button.setBounds(
                Constants.UNIT,
                (Constants.MIDDLE_ROUNDED_HEIGHT - 1) * Constants.UNIT,
                (Constants.WIDTH - 2) * Constants.UNIT,
                Constants.UNIT
        );

        UiClickListener.OnClickListener clickListener = new UiClickListener.OnClickListener() {
            @Override
            public void onClick() {
                openLevelScreen();
            }
        };

        button.addListener(new UiClickListener(clickListener));
        stage.addActor(button);
    }

    private void openLevelScreen() {
        navigator.open(Destination.LEVEL);
    }
}
