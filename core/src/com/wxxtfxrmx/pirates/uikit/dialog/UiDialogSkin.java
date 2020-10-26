package com.wxxtfxrmx.pirates.uikit.dialog;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.wxxtfxrmx.pirates.screen.levelv2.Constants;

public class UiDialogSkin extends Skin {
    private final TextureAtlas uiAtlas = new TextureAtlas(
            Gdx.files.internal("uikit/uikit.atlas")
    );

    public UiDialogSkin() {
        Window.WindowStyle windowStyle = new Window.WindowStyle();
        addRegions(uiAtlas);
        windowStyle.background = getBackground();
        windowStyle.stageBackground = getStageBackground();
        windowStyle.titleFont = new BitmapFont();
        add("default", windowStyle);
    }

    private Drawable getBackground() {
        NinePatch ninePatch = getPatch("pane-background");
        return new NinePatchDrawable(ninePatch);
    }

    private Drawable getStageBackground() {
        Pixmap stageBg = new Pixmap(
                Constants.WIDTH * Constants.UNIT,
                Constants.HEIGHT * Constants.UNIT,
                Pixmap.Format.RGB888);

        stageBg.setColor(Color.BLACK);
        stageBg.setBlending(Pixmap.Blending.None);
        stageBg.fill();

        Image image = new Image(new Texture(stageBg));
        image.getColor().a = 0.5f;

        return image.getDrawable();
    }
}
