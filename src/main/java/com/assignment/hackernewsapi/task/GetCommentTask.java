package com.assignment.hackernewsapi.task;

import com.assignment.hackernewsapi.service.HackerNewsRestImpl;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

@AllArgsConstructor
public class GetCommentTask implements Callable {

    private static final Logger logger = LoggerFactory.getLogger(GetCommentTask.class);

    private Long commentId;
    private HackerNewsRestImpl hackerNewsRest;

    @Override public Object call() throws Exception {
        logger.info("Fetching comment : {}", commentId);
        return hackerNewsRest.getComment(commentId);
    }
}
