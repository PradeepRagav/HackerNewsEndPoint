package com.assignment.hackernewsapi.task;

import com.assignment.hackernewsapi.model.datastore.HNCommentresponseDTO;
import com.assignment.hackernewsapi.model.datastore.HNTopicResponseDTO;
import com.assignment.hackernewsapi.service.HackerNewsCacheService;
import com.assignment.hackernewsapi.service.HackerNewsRestImpl;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@AllArgsConstructor
public class UpdateCommentsTask implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(UpdateCommentsTask.class);

    private Long storyId;

    private HackerNewsCacheService hackerNewsCacheService;

    private HackerNewsRestImpl hackerNewsRest;


    @Override public void run() {

        logger.info("Started to fetch comments for storyId : {}",storyId);

        HNTopicResponseDTO story = hackerNewsCacheService.getStory(storyId);
        List<Long> commentIds = story.getKids();
        List<HNCommentresponseDTO> allComments = new ArrayList<>();
        List<HNCommentresponseDTO> topTenComments = new ArrayList<>();
        for(Long commentId : commentIds){
            //TODO : Can be done asyncronously
            HNCommentresponseDTO comment = hackerNewsRest.getComment(commentId);
//            logger.info(comment.toString());
            allComments.add(comment);
        }

        Collections.sort(allComments, (hnCommentresponseDTO, t1) -> {
            int otherCount = t1.getKids() != null ? t1.getKids().size() : 0;
            int thisCount = hnCommentresponseDTO.getKids() != null ? hnCommentresponseDTO.getKids().size() : 0;
            return otherCount - thisCount;
        });


        for(int i=0;i<10;i++){
            HNCommentresponseDTO hnCommentresponseDTO = allComments.get(i);
//            TODO : Not required to cache a comment
//            hackerNewsCacheService.saveComment(hnCommentresponseDTO);
            topTenComments.add(hnCommentresponseDTO);
        }

        hackerNewsCacheService.saveTopTenComments(storyId, topTenComments);

        logger.info("Done fetching comments for storyId : {}",storyId);
    }
}
