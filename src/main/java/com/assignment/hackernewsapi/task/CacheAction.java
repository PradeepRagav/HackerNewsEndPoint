package com.assignment.hackernewsapi.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class CacheAction implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private PeriodicFetchService periodicFetchService;

    @Override public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        periodicFetchService.triggerPeriodicFetch();
    }
}
