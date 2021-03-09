// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0

package org.terasology.xmas.weather;

import org.terasology.engine.entitySystem.entity.EntityManager;
import org.terasology.engine.entitySystem.entity.EntityRef;
import org.terasology.engine.entitySystem.event.ReceiveEvent;
import org.terasology.engine.entitySystem.systems.BaseComponentSystem;
import org.terasology.engine.entitySystem.systems.RegisterMode;
import org.terasology.engine.entitySystem.systems.RegisterSystem;
import org.terasology.engine.logic.delay.DelayManager;
import org.terasology.engine.logic.delay.PeriodicActionTriggeredEvent;
import org.terasology.engine.registry.In;
import org.terasology.weatherManager.systems.WeatherManagerSystem;

@RegisterSystem(RegisterMode.AUTHORITY)
public class WinterWeather extends BaseComponentSystem {

    private static final String LET_IT_SNOW = "Xmas:LetItSnow";

    private static final int MINUTES = 1000 * 60;
    private static final int SECONDS = 1000;

    @In
    DelayManager delayManager;

    @In
    EntityManager entityManager;

    @In
    WeatherManagerSystem weatherManager;

    EntityRef winterWeather;

    @Override
    public void postBegin() {
        super.postBegin();
        winterWeather = entityManager.create();

        delayManager.addPeriodicAction(winterWeather, LET_IT_SNOW, 5 * SECONDS, 1 * MINUTES);
    }

    @ReceiveEvent
    public void onWeatherTrigger(PeriodicActionTriggeredEvent event, EntityRef entity) {
        if (event.getActionId().equals(LET_IT_SNOW)) {
            weatherManager.makeSnow(50 * SECONDS);
        }
    }
}
