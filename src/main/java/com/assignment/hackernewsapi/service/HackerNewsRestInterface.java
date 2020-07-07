package com.assignment.hackernewsapi.service;

import com.assignment.hackernewsapi.model.datastore.HNCommentresponseDTO;
import com.assignment.hackernewsapi.model.datastore.HNStoryResponseDTO;
import com.assignment.hackernewsapi.model.datastore.HNUser;

import java.util.List;

public interface HackerNewsRestInterface {
    Long[] getTopStories();
    HNStoryResponseDTO getStory(Long storyId);
    HNCommentresponseDTO getComment(Long commentId);
    HNUser getUser(String userId);
}
