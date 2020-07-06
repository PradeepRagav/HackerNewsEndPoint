package com.assignment.hackernewsapi.service;

import com.assignment.hackernewsapi.model.datastore.HNCommentresponseDTO;
import com.assignment.hackernewsapi.model.datastore.HNTopicResponseDTO;

import java.util.List;
import java.util.Set;

public interface HackerNewsCacheInterface {
    HNTopicResponseDTO getStory(Long storyId);
    List<HNTopicResponseDTO> getTopStories();
    List<HNCommentresponseDTO> getTopComments(Long storyId);
    public Set<Long> getPastStoryIds();
    void saveStory(HNTopicResponseDTO hnTopicResponseDTO);
    void saveTopTenStories(List<HNTopicResponseDTO> hnTopicResponseDTOS);
    void saveTopTenComments(Long storyId, List<HNCommentresponseDTO> hnTopicResponseDTOS);
}
