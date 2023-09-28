package com.landak.develab;

import com.landak.develab.cron.ParkingAvailabilityCron;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class ContextStartedListener implements ApplicationListener<ContextRefreshedEvent> {
    
    @Autowired
    private ParkingAvailabilityCron cron;

    @Override
    public void onApplicationEvent(@NotNull ContextRefreshedEvent event) {
        cron.cron();
    }

}
