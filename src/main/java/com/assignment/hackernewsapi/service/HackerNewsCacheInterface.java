package com.assignment.hackernewsapi.service;

import com.assignment.hackernewsapi.model.datastore.HNCommentresponseDTO;
import com.assignment.hackernewsapi.model.datastore.HNStoryResponseDTO;
import com.assignment.hackernewsapi.model.datastore.HNUser;

import java.util.List;
import java.util.Set;

public interface HackerNewsCacheInterface {
    HNStoryResponseDTO getStory(Long storyId);
    List<HNStoryResponseDTO> getTopStories();
    List<HNCommentresponseDTO> getTopComments(Long storyId);
    Set<Long> getPastStoryIds();
    HNUser getUser(String userId);
    void saveStory(HNStoryResponseDTO hnTopicResponseDTO);
    void saveTopTenStories(List<HNStoryResponseDTO> hnTopicResponseDTOS);
    void saveTopTenComments(Long storyId, List<HNCommentresponseDTO> hnTopicResponseDTOS);
    void saveUser(HNUser user);
}
