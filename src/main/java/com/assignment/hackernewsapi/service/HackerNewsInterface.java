package com.assignment.hackernewsapi.service;

import com.assignment.hackernewsapi.model.datastore.HNComment;
import com.assignment.hackernewsapi.model.datastore.HNStory;

import java.util.ArrayList;
import java.util.List;

public interface HackerNewsInterface {
    List<HNStory> getTopStories();
    List<HNComment> getTopComments(Long storyId);
    ArrayList<HNStory> getPastStories();
}
