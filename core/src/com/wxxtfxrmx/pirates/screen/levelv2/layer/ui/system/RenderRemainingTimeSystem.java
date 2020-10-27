package com.wxxtfxrmx.pirates.screen.levelv2.layer.ui.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.RemainedTimeComponent;
import com.wxxtfxrmx.pirates.uikit.UiLabel;

import java.util.Locale;

public class RenderRemainingTimeSystem extends IteratingSystem {

    private final UiLabel timer;
    private final ComponentMapper<RemainedTimeComponent> remainedTimeMapper = ComponentMapper.getFor(RemainedTimeComponent.class);

    public RenderRemainingTimeSystem(UiLabel timer) {
        super(Family.all(RemainedTimeComponent.class).get());
        this.timer = timer;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        RemainedTimeComponent remainedTimeComponent = remainedTimeMapper.get(entity);

        timer.setText(String.format(Locale.ENGLISH,"%02d", remainedTimeComponent.remainedTime));
    }
}
