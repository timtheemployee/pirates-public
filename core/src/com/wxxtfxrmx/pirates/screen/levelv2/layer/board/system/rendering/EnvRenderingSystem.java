package com.wxxtfxrmx.pirates.screen.levelv2.layer.board.system.rendering;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.wxxtfxrmx.pirates.screen.levelv2.Constants;
import com.wxxtfxrmx.pirates.screen.levelv2.UnstoppableSystem;

public class EnvRenderingSystem extends EntitySystem implements UnstoppableSystem {

    private final ShapeRenderer shapeRenderer;
    private final OrthographicCamera camera;
    private final float skyY = (Constants.MIDDLE_ROUNDED_HEIGHT + 1) * Constants.UNIT;
    private final float waterY = (Constants.MIDDLE_ROUNDED_HEIGHT) * Constants.UNIT;
    private final float underWaterY = Constants.MIDDLE_ROUNDED_HEIGHT * Constants.UNIT;
    private final float width = Constants.WIDTH * Constants.UNIT;

    public EnvRenderingSystem(ShapeRenderer shapeRenderer, OrthographicCamera camera) {
        this.shapeRenderer = shapeRenderer;
        this.camera = camera;
        shapeRenderer.setAutoShapeType(true);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        camera.update();
        renderBackground();
    }

    private void renderBackground() {
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin();
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        Color blue = Color.valueOf("#1976D2");
        Color deepBlue = Color.valueOf("#1a237e");
        Color orange = Color.valueOf("#F44336");

        drawSkyRect(blue, orange);
        drawWaterRect(orange, blue);
        drawUnderWaterRect(deepBlue, blue);
        shapeRenderer.end();
    }

    private void drawSkyRect(Color skyColor, Color sunsetColor) {
        shapeRenderer.rect(0, skyY,
                width,
                (Constants.HEIGHT - Constants.MIDDLE_ROUNDED_HEIGHT) * Constants.UNIT,
                sunsetColor,
                sunsetColor,
                skyColor,
                skyColor);
    }

    private void drawUnderWaterRect(Color deep, Color top) {
        shapeRenderer.rect(0, 0,
                width,
                underWaterY,
                deep,
                deep,
                top,
                top);
    }

    private void drawWaterRect(Color sunset, Color water) {
        shapeRenderer.rect(0, waterY, width, Constants.UNIT, water, water, sunset, sunset);
    }
}
