package com.assignment.hackernewsapi.task;

import com.assignment.hackernewsapi.service.HackerNewsCacheService;
import com.assignment.hackernewsapi.service.HackerNewsRestImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class PeriodicFetchService {

    @Autowired
    private HackerNewsRestImpl hackerNewsRest;

    @Autowired
    private HackerNewsCacheService hackerNewsCacheService;

    @Autowired
    private ScheduledExecutorService scheduledExecutorService;

    @Autowired
    private ExecutorService executorService;


    public void triggerPeriodicFetch(){
        scheduledExecutorService.scheduleAtFixedRate(
            new UpdateTopStoriesTask(hackerNewsRest, hackerNewsCacheService, executorService),
            0,
            10*60, // Every 10 minutes
            TimeUnit.SECONDS);
    }
}
