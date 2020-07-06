package com.assignment.hackernewsapi.service;

import com.assignment.hackernewsapi.model.datastore.HNCommentresponseDTO;
import com.assignment.hackernewsapi.model.datastore.HNTopicResponseDTO;
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


    private Map<Long,HNTopicResponseDTO> topicIdToTopicMap;
    private List<HNTopicResponseDTO> topTopics;

    public Map<Long, HNTopicResponseDTO> getTopicIdToTopicMap() {
        if(topicIdToTopicMap == null) topicIdToTopicMap = new HashMap<>();
        return topicIdToTopicMap;
    }

    public List<HNTopicResponseDTO> getTopTopics() {
        if(topTopics == null) topTopics = new ArrayList<>();
        return topTopics;
    }

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
//        List<HNTopicResponseDTO> hnTopicResponseDTOS = new ArrayList<>();
//
//        for(int i=0;i<stories.length;i++){
//            Long storyId = stories[i];
////            HNTopicResponseDTO story = getTopicIdToTopicMap().get(storyId);
//            HNTopicResponseDTO story = hackerNewsCacheService.getStory(storyId);
////            if(story == null) {
////                story = getStory(storyId);
////                getTopicIdToTopicMap().put(storyId, story);
////            }
//            hnTopicResponseDTOS.add(story);
//        }
//        Collections.sort(hnTopicResponseDTOS, new Comparator<HNTopicResponseDTO>() {
//            @Override public int compare(HNTopicResponseDTO hnTopicResponseDTO, HNTopicResponseDTO t1) {
//                return (int) (t1.getScore() - hnTopicResponseDTO.getScore());
//            }
//        });
//
//        getTopTopics().addAll(hnTopicResponseDTOS);
//
//        System.out.println(System.currentTimeMillis());
//        return hnTopicResponseDTOS;
    }

    @Override public List<HNCommentresponseDTO> getTopComments(Long storyId) {
        HNTopicResponseDTO story = getStory(storyId);
        List<Long> kids = story.getKids();
        List<HNCommentresponseDTO> comments = new ArrayList<>();
        for(Long kid : kids) {
            HNCommentresponseDTO comment = getComment(kid);
            comments.add(comment);
        }

        Collections.sort(comments, new Comparator<HNCommentresponseDTO>() {
            @Override public int compare(HNCommentresponseDTO hnCommentresponseDTO, HNCommentresponseDTO t1) {
                int otherCount = t1.getKids() != null ? t1.getKids().size() : 0;
                int thisCount = hnCommentresponseDTO.getKids() != null ? hnCommentresponseDTO.getKids().size() : 0;
                return otherCount - thisCount;
            }
        });
        return comments;
    }

    @Override public void getPastStories() {

    }


    @Override public HNTopicResponseDTO getStory(Long storyId) {
        String baseUrl = env.getProperty("hacker.news.end.point");
        String url = new StringBuilder(baseUrl).append(HackerNewsUri.ITEM).toString();
        url = String.format(url, storyId);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<HNTopicResponseDTO> response = restTemplate.exchange(url, HttpMethod.GET,
            entity, HNTopicResponseDTO.class);

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
}
