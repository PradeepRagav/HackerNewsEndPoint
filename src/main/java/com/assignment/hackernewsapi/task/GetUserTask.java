package com.assignment.hackernewsapi.task;

import com.assignment.hackernewsapi.service.HackerNewsRestImpl;
import lombok.AllArgsConstructor;

import java.util.concurrent.Callable;

@AllArgsConstructor
public class GetUserTask implements Callable {

    private String userId;
    private HackerNewsRestImpl hackerNewsRest;

    @Override public Object call() throws Exception {
        return hackerNewsRest.getUser(userId);
    }
}
