package com.assignment.hackernewsapi.task;

import com.assignment.hackernewsapi.model.datastore.HNTopicResponseDTO;
import com.assignment.hackernewsapi.service.HackerNewsCacheService;
import com.assignment.hackernewsapi.service.HackerNewsRestImpl;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Setter
@AllArgsConstructor
public class UpdateTopStoriesTask implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(UpdateTopStoriesTask.class);

    private HackerNewsRestImpl hackerNewsRest;

    private HackerNewsCacheService hackerNewsCacheService;

    private ExecutorService executorService;


    @Override
    public void run() {
        try {
            processTask();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void processTask() throws ExecutionException, InterruptedException {

        logger.info("Started fetching top stories");

        Long[] idsOftopStories = hackerNewsRest.getTopStories();

        //TODO : Call asyncronously
        List<HNTopicResponseDTO> topStories = new ArrayList<>();
        List<Future> callBacks = new ArrayList<>();
        for(int i=0;i<idsOftopStories.length;i++){
            Long storyId = idsOftopStories[i];
            Future submit = executorService.submit(new GetStoryTask(storyId, hackerNewsRest));
            callBacks.add(submit);
        }

        for(Future future : callBacks){
            HNTopicResponseDTO story = (HNTopicResponseDTO) future.get();
//            logger.info(story.toString());
            topStories.add((HNTopicResponseDTO) future.get());
        }

        logger.info("Done fetching all stories : {}",topStories.size());

        //Sort by rank
        Collections.sort(topStories, (hnTopicResponseDTO, t1) -> (int) (t1.getScore() - hnTopicResponseDTO.getScore()));

        //Get top 10 stories
        List<HNTopicResponseDTO> topTenStories = new ArrayList<>();

        //TODO : Could be configured
        for(int i=0;i<10;i++){
            HNTopicResponseDTO hnTopicResponseDTO = topStories.get(i);

            //Update comments
            executorService.submit(new UpdateCommentsTask(hnTopicResponseDTO.getId(),hackerNewsCacheService,hackerNewsRest));

            //Cache story
            hackerNewsCacheService.saveStory(topStories.get(i));
            topTenStories.add(topStories.get(i));
        }

        //Cache top ten stories
        hackerNewsCacheService.saveTopTenStories(topTenStories);

        logger.info("Done fetching top stories");

    }


}
