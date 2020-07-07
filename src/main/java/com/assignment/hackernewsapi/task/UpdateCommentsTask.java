package com.assignment.hackernewsapi.task;

import com.assignment.hackernewsapi.model.datastore.HNCommentresponseDTO;
import com.assignment.hackernewsapi.model.datastore.HNStoryResponseDTO;
import com.assignment.hackernewsapi.model.datastore.HNUser;
import com.assignment.hackernewsapi.service.HackerNewsCacheService;
import com.assignment.hackernewsapi.service.HackerNewsRestImpl;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@AllArgsConstructor
public class UpdateCommentsTask implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(UpdateCommentsTask.class);

    private Long storyId;

    private HackerNewsCacheService hackerNewsCacheService;

    private HackerNewsRestImpl hackerNewsRest;

    private ExecutorService executorService;


    @SneakyThrows @Override public void run() {

        logger.info("Started to fetch comments for storyId : {}",storyId);

        HNStoryResponseDTO story = hackerNewsCacheService.getStory(storyId);
        List<Long> commentIds = story.getKids();
        List<HNCommentresponseDTO> allComments = new ArrayList<>();
        List<HNCommentresponseDTO> topTenComments = new ArrayList<>();

        List<Future> commentsCallBackList = new ArrayList<>();
        for(Long commentId : commentIds){
            Future future =
                executorService.submit(new GetCommentTask(commentId, hackerNewsRest));
            commentsCallBackList.add(future);
        }

        for(Future future : commentsCallBackList){
            allComments.add((HNCommentresponseDTO) future.get());
        }

        logger.info("Done fetching comments : {}",allComments.size());

        Collections.sort(allComments, (hnCommentresponseDTO, t1) -> {
            int otherCount = t1.getKids() != null ? t1.getKids().size() : 0;
            int thisCount = hnCommentresponseDTO.getKids() != null ? hnCommentresponseDTO.getKids().size() : 0;
            return otherCount - thisCount;
        });


        List<Future> usersCallBackList = new ArrayList<>();
        List<HNUser> hnUsers = new ArrayList<>();
        for(int i=0;i<10;i++){
            HNCommentresponseDTO hnCommentresponseDTO = allComments.get(i);
            topTenComments.add(hnCommentresponseDTO);
            Future submit = executorService.submit(new GetUserTask(hnCommentresponseDTO.getBy(), hackerNewsRest));
            usersCallBackList.add(submit);
        }

        for(Future future : usersCallBackList){
            HNUser hnUser = (HNUser) future.get();
            hackerNewsCacheService.saveUser(hnUser);
            hnUsers.add(hnUser);
        }

        hackerNewsCacheService.saveTopTenComments(storyId, topTenComments);

        logger.info("Done fetching comments for storyId : {}",storyId);
    }
}
