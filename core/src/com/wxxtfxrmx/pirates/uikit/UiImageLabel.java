package com.wxxtfxrmx.pirates.uikit;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.wxxtfxrmx.pirates.screen.levelv2.Constants;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.board.world.TileType;

public class UiImageLabel extends Actor {

    private final DefaultSkin defaultSkin = new DefaultSkin();
    private final BitmapFont font = defaultSkin.getDefaultFont();
    private TextureRegion drawable;
    private String text;

    private boolean flip = false;

    public void setDrawable(TileType type) {
        TextureRegion region = defaultSkin.getRegion(type.getAtlasPath());
        drawable = new Sprite(region, 0, 0, Constants.UNIT, Constants.UNIT);
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setFlip(boolean flip) {
        this.flip = flip;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        drawIcon(batch, drawable);
        drawText(batch, text);
    }

    private void drawIcon(Batch batch, TextureRegion drawable) {
        if (drawable == null) return;

        float x = flip ? getX(Align.right) - drawable.getRegionWidth() : getX(Align.left);

        batch.draw(drawable,
                x,
                getY(Align.bottom),
                drawable.getRegionWidth() * 0.5f,
                drawable.getRegionHeight() * 0.5f,
                Constants.UNIT,
                Constants.UNIT,
                0.4f,
                0.4f,
                0f);
    }

    private void drawText(Batch batch, String text) {
        if (text == null) return;

        float x = getX(Align.left);
        float y = getY(Align.bottom);

        //NOTE: idk why this values are correct
        if (drawable != null) {
            x += flip ? - drawable.getRegionWidth() * 1.1f : drawable.getRegionWidth();
            y += drawable.getRegionHeight() * 0.68f;
        }

        font.draw(batch, text, x, y);
    }
}
