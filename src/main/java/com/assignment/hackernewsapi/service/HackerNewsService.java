package com.assignment.hackernewsapi.service;

import com.assignment.hackernewsapi.model.datastore.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

@Service
public class HackerNewsService implements HackerNewsInterface {

    @Autowired
    private HackerNewsCacheService hackerNewsCacheService;


    @Override public List<HNStory> getTopStories() {
        List<HNStoryResponseDTO> topStories = hackerNewsCacheService.getTopStories();
        List<HNStory> hnStories = new ArrayList<>();
        topStories.stream().forEach(hnTopicResponseDTO -> {
            HNStory hnStory = new HNStory();
            hnStory.setId(hnTopicResponseDTO.getId());
            hnStory.setUrl(hnTopicResponseDTO.getUrl());
            hnStory.setTitle(hnTopicResponseDTO.getTitle());
            hnStory.setScore(hnTopicResponseDTO.getScore());
            hnStory.setBy(hnTopicResponseDTO.getBy());
            hnStory.setTime(hnTopicResponseDTO.getTime());
            hnStories.add(hnStory);
        });
        return hnStories;
    }

    @Override public List<HNComment> getTopComments(Long storyId) {
        List<HNCommentresponseDTO> topComments = hackerNewsCacheService.getTopComments(storyId);
        List<HNComment> comments = new ArrayList<>();
        topComments.stream().forEach(hnCommentresponseDTO -> {
            HNComment hnComment = new HNComment();
            hnComment.setId(hnCommentresponseDTO.getId());
            hnComment.setBy(hnCommentresponseDTO.getBy());
            hnComment.setText(hnCommentresponseDTO.getText());
            hnComment.setTime(hnCommentresponseDTO.getTime());
            HNUser user = hackerNewsCacheService.getUser(hnCommentresponseDTO.getBy());
            HNUserResponseDTO hnUserResponseDTO = new HNUserResponseDTO();

            //TODO : To handle timezone
            int currYear = Calendar.getInstance().get(Calendar.YEAR);
            Calendar instance = Calendar.getInstance();
            instance.setTimeInMillis(user.getCreated() * 1000);
            int createdYear = instance.get(Calendar.YEAR);

            hnUserResponseDTO.setHnAge(currYear - createdYear);
            hnUserResponseDTO.setHnHandle(user.getId());
            hnComment.setHnUser(hnUserResponseDTO);
            comments.add(hnComment);
        });
        return comments;
    }

    @Override public ArrayList<HNStory> getPastStories() {
        Set<Long> pastStoryIds = hackerNewsCacheService.getPastStoryIds();
        ArrayList<HNStory> pastStories = new ArrayList<>();
        for(Long storyId : pastStoryIds){
            HNStoryResponseDTO story = hackerNewsCacheService.getStory(storyId);
            HNStory hnStory = new HNStory();
            hnStory.setId(story.getId());
            hnStory.setUrl(story.getUrl());
            hnStory.setTitle(story.getTitle());
            hnStory.setScore(story.getScore());
            hnStory.setBy(story.getBy());
            hnStory.setTime(story.getTime());
            pastStories.add(hnStory);
        }
        return pastStories;
    }
}
