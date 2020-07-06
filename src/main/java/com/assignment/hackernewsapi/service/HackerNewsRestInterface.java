package com.assignment.hackernewsapi.service;

import com.assignment.hackernewsapi.model.datastore.HNCommentresponseDTO;
import com.assignment.hackernewsapi.model.datastore.HNTopicResponseDTO;

import java.util.List;

public interface HackerNewsRestInterface {
    Long[] getTopStories();
    List<HNCommentresponseDTO> getTopComments(Long storyId);
    void getPastStories();
    HNTopicResponseDTO getStory(Long storyId);
    HNCommentresponseDTO getComment(Long commentId);
}
