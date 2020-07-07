package com.assignment.hackernewsapi.rest;

import com.assignment.hackernewsapi.model.datastore.HNComment;
import com.assignment.hackernewsapi.model.datastore.HNStory;
import com.assignment.hackernewsapi.service.HackerNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class HackerNewsController {

    @Autowired
    private HackerNewsService hackerNewsService;

    @GetMapping(value ="/top-stories")
    public List<HNStory> getTopStories(){
        return hackerNewsService.getTopStories();
    }

    @GetMapping(value ="/comments/{storyId}")
    public List<HNComment> getComments(@PathVariable Long storyId){
        return hackerNewsService.getTopComments(storyId);
    }

    @GetMapping(value = "/past-stories")
    public ArrayList<HNStory> getPastStories(){
        return hackerNewsService.getPastStories();
    }
}
