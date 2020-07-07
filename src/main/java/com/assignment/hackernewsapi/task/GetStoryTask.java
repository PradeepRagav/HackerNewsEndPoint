package com.assignment.hackernewsapi.task;

import com.assignment.hackernewsapi.model.datastore.HNStoryResponseDTO;
import com.assignment.hackernewsapi.service.HackerNewsRestImpl;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

@Setter
@AllArgsConstructor
public class GetStoryTask implements Callable {

    private static final Logger logger = LoggerFactory.getLogger(GetStoryTask.class);

    private Long storyId;
    private HackerNewsRestImpl hackerNewsRest;

    @Override public Object call() throws Exception {

        logger.info("Fetching storyId : {} ",storyId);
        HNStoryResponseDTO story = hackerNewsRest.getStory(storyId);
        logger.info("Done Fetching storyId : {} ",storyId);
        return story;
    }

}
