package com.wxxtfxrmx.pirates.uikit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.wxxtfxrmx.pirates.screen.levelv2.Constants;

public class DefaultSkin extends Skin {

    private final static String DEFAULT = "default";
    private final BitmapFont defaultFont = new BitmapFont(Gdx.files.internal("ui/font/pirates-font.fnt"));
    private final TextureAtlas uikitAtlas = new TextureAtlas(Gdx.files.internal("ui/uikit.atlas"));
    private final TextureAtlas iconPackAtlas = new TextureAtlas(Gdx.files.internal("ui/icon/icon-pack.atlas"));
    private final TextureAtlas tileSheetAtlas = new TextureAtlas(Gdx.files.internal("sprite/tiles-sheet.atlas"));

    public DefaultSkin() {
        addRegions(uikitAtlas);
        addRegions(iconPackAtlas);
        addRegions(tileSheetAtlas);

        add(DEFAULT, defaultFont, BitmapFont.class);
        add(DEFAULT, createImageButtonStyle(), ImageButton.ImageButtonStyle.class);
        add(DEFAULT, createLabelStyle(), Label.LabelStyle.class);
        add(DEFAULT, createWindowStyle(), Window.WindowStyle.class);
        add(DEFAULT, createTextButtonStyle(), TextButton.TextButtonStyle.class);
        add(DEFAULT, createImageTextButtonStyle(), ImageTextButton.ImageTextButtonStyle.class);
    }

    private ImageButton.ImageButtonStyle createImageButtonStyle() {
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.down = getNinePatchDrawable("shadowed-clicked");
        style.up = getNinePatchDrawable("shadowed-idle");

        return style;
    }

    private TextButton.TextButtonStyle createTextButtonStyle() {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = getFont(DEFAULT);

        return style;
    }

    private ImageTextButton.ImageTextButtonStyle createImageTextButtonStyle() {
        ImageTextButton.ImageTextButtonStyle style = new ImageTextButton.ImageTextButtonStyle();
        style.font = getFont(DEFAULT);

        return style;
    }

    private Label.LabelStyle createLabelStyle() {
        Label.LabelStyle style = new Label.LabelStyle();
        style.background = getNinePatchDrawable("pane-background");
        style.font = getFont(DEFAULT);

        return style;
    }

    // Dialog style
    private Window.WindowStyle createWindowStyle() {
        Window.WindowStyle style = new Window.WindowStyle();
        style.background = getNinePatchDrawable("pane-background");
        style.stageBackground = getStageBackground();
        style.titleFont = getFont(DEFAULT);

        return style;
    }

    // FIXME: Under window layer background
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

    private NinePatchDrawable getNinePatchDrawable(String name) {
        NinePatch patch = getPatch(name);
        return new NinePatchDrawable(patch);
    }

    public BitmapFont getDefaultFont() {
        return getFont(DEFAULT);
    }
}
