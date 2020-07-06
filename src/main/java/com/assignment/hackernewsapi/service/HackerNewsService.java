package com.assignment.hackernewsapi.service;

import com.assignment.hackernewsapi.model.datastore.HNComment;
import com.assignment.hackernewsapi.model.datastore.HNCommentresponseDTO;
import com.assignment.hackernewsapi.model.datastore.HNTopic;
import com.assignment.hackernewsapi.model.datastore.HNTopicResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class HackerNewsService implements HackerNewsInterface {

    @Autowired
    private HackerNewsCacheService hackerNewsCacheService;


    @Override public List<HNTopic> getTopStories() {
        List<HNTopicResponseDTO> topStories = hackerNewsCacheService.getTopStories();
        List<HNTopic> hnTopics = new ArrayList<>();
        topStories.stream().forEach(hnTopicResponseDTO -> {
            HNTopic hnTopic = new HNTopic();
            hnTopic.setId(hnTopicResponseDTO.getId());
            hnTopic.setUrl(hnTopicResponseDTO.getUrl());
            hnTopic.setTitle(hnTopicResponseDTO.getTitle());
            hnTopic.setScore(hnTopicResponseDTO.getScore());
            hnTopic.setBy(hnTopicResponseDTO.getBy());
            hnTopic.setTime(hnTopicResponseDTO.getTime());
            hnTopics.add(hnTopic);
        });
        return hnTopics;
    }

    @Override public List<HNComment> getTopComments(Long storyId) {
        List<HNCommentresponseDTO> topComments = hackerNewsCacheService.getTopComments(storyId);
        List<HNComment> comments = new ArrayList<>();
        topComments.stream().forEach(hnCommentresponseDTO -> {
            HNComment hnComment = new HNComment();
            hnComment.setId(hnCommentresponseDTO.getId());
            hnComment.setBy(hnCommentresponseDTO.getBy());
            hnComment.setText(hnCommentresponseDTO.getText());
            hnComment.setHnAge(hnCommentresponseDTO.getHnAge());
            hnComment.setTime(hnCommentresponseDTO.getTime());
            hnComment.setHnHandle(hnComment.getHnHandle());
            comments.add(hnComment);
        });
        return comments;
    }

    @Override public ArrayList<HNTopic> getPastStories() {
        Set<Long> pastStoryIds = hackerNewsCacheService.getPastStoryIds();
        ArrayList<HNTopic> pastStories = new ArrayList<>();
        for(Long storyId : pastStoryIds){
            HNTopicResponseDTO story = hackerNewsCacheService.getStory(storyId);
            HNTopic hnTopic = new HNTopic();
            hnTopic.setId(story.getId());
            hnTopic.setUrl(story.getUrl());
            hnTopic.setTitle(story.getTitle());
            hnTopic.setScore(story.getScore());
            hnTopic.setBy(story.getBy());
            hnTopic.setTime(story.getTime());
            pastStories.add(hnTopic);
        }
        return pastStories;
    }
}
