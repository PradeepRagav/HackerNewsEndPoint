package com.assignment.hackernewsapi.service;

import com.assignment.hackernewsapi.model.datastore.HNComment;
import com.assignment.hackernewsapi.model.datastore.HNTopic;

import java.util.ArrayList;
import java.util.List;

public interface HackerNewsInterface {
    List<HNTopic> getTopStories();
    List<HNComment> getTopComments(Long storyId);
    ArrayList<HNTopic> getPastStories();
}
