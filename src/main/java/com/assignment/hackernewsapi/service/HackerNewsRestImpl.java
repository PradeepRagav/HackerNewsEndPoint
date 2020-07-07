package com.assignment.hackernewsapi.service;

import com.assignment.hackernewsapi.model.datastore.HNCommentresponseDTO;
import com.assignment.hackernewsapi.model.datastore.HNStoryResponseDTO;
import com.assignment.hackernewsapi.model.datastore.HNUser;
import com.assignment.hackernewsapi.util.HackerNewsUri;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class HackerNewsRestImpl implements HackerNewsRestInterface {

    @Autowired
    private Environment env;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HackerNewsCacheService hackerNewsCacheService;


    @Override public Long[] getTopStories() {
        System.out.println(System.currentTimeMillis());
        String baseUrl = env.getProperty("hacker.news.end.point");
        String url = new StringBuilder(baseUrl).append(HackerNewsUri.TOP_STORIES).toString();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<Long[]> response = restTemplate.exchange(url, HttpMethod.GET,
            entity, Long[].class);
        return response.getBody();
    }


    @Override public HNStoryResponseDTO getStory(Long storyId) {
        String baseUrl = env.getProperty("hacker.news.end.point");
        String url = new StringBuilder(baseUrl).append(HackerNewsUri.ITEM).toString();
        url = String.format(url, storyId);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<HNStoryResponseDTO> response = restTemplate.exchange(url, HttpMethod.GET,
            entity, HNStoryResponseDTO.class);

        return response.getBody();
    }

    @Override public HNCommentresponseDTO getComment(Long commentId) {
        String baseUrl = env.getProperty("hacker.news.end.point");
        String url = new StringBuilder(baseUrl).append(HackerNewsUri.ITEM).toString();
        url = String.format(url, commentId);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<HNCommentresponseDTO> response = restTemplate.exchange(url, HttpMethod.GET,
            entity, HNCommentresponseDTO.class);

        return response.getBody();
    }

    @Override public HNUser getUser(String userId) {
        String baseUrl = env.getProperty("hacker.news.end.point");
        String url = new StringBuilder(baseUrl).append(HackerNewsUri.USER).toString();
        url = String.format(url, userId);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<HNUser> response = restTemplate.exchange(url, HttpMethod.GET,
            entity, HNUser.class);

        return response.getBody();
    }

}
