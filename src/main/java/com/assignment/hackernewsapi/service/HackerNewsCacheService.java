package com.assignment.hackernewsapi.service;

import com.assignment.hackernewsapi.model.datastore.HNCommentresponseDTO;
import com.assignment.hackernewsapi.model.datastore.HNTopicResponseDTO;
import com.assignment.hackernewsapi.task.UpdateCommentsTask;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class HackerNewsCacheService implements HackerNewsCacheInterface {

    private static final Logger logger = LoggerFactory.getLogger(HackerNewsCacheService.class);

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private HackerNewsRestImpl hackerNewsRest;

    private static String STORY_ID_MAP = "STORY_ID_MAP";
    private static String TOP_STORIES = "TOP_STORIES";
    private static String TOP_COMMENTS = "TOP_COMMENTS";
    private static String STORIES_SET = "STORIES_SET";

    @Override public HNTopicResponseDTO getStory(Long storyId) {
        RMapCache<Object, Object> mapCache = redissonClient.getMapCache(STORY_ID_MAP);
        if(mapCache.size() != 0 && mapCache.get(String.valueOf(storyId)) != null){
            return (HNTopicResponseDTO) mapCache.get(String.valueOf(storyId));
        }else{
            HNTopicResponseDTO story = hackerNewsRest.getStory(storyId);
            saveStory(story);
            return story;
        }
    }

    @Override public List<HNTopicResponseDTO> getTopStories() {
        RMapCache<Object, Object> mapCache = redissonClient.getMapCache(TOP_STORIES);
        String key = "topstories";
        if(mapCache.size() != 0){
            return (List<HNTopicResponseDTO>) mapCache.get(key);
        }
        return null;
    }

    @Override public List<HNCommentresponseDTO> getTopComments(Long storyId) {
        RMapCache<Object, Object> mapCache = redissonClient.getMapCache(TOP_COMMENTS);
        List<HNCommentresponseDTO> hnCommentresponseDTOS = (List<HNCommentresponseDTO>) mapCache
            .get(String.valueOf(storyId));
        return hnCommentresponseDTOS;
    }

    @Override public void saveStory(HNTopicResponseDTO hnTopicResponseDTO) {
        RMapCache<Object, Object> mapCache = redissonClient.getMapCache(STORY_ID_MAP);
        mapCache.put(String.valueOf(hnTopicResponseDTO.getId()), hnTopicResponseDTO);
    }


    @Override public void saveTopTenStories(List<HNTopicResponseDTO> hnTopicResponseDTOS) {
        RMapCache<Object, Object> mapCache = redissonClient.getMapCache(TOP_STORIES);
        String key = "topstories";
        mapCache.put(key, hnTopicResponseDTOS);
        updateStoriesSet(hnTopicResponseDTOS);
    }

    @Override public void saveTopTenComments(Long storyId, List<HNCommentresponseDTO> hnTopicResponseDTOS) {
        RMapCache<Object, Object> mapCache = redissonClient.getMapCache(TOP_COMMENTS);
        mapCache.put(String.valueOf(storyId),hnTopicResponseDTOS);
    }

    public Set<Long> getPastStoryIds(){
        RMapCache<Object, Object> mapCache = redissonClient.getMapCache(STORIES_SET);
        String key = "storyIdSet";
        return (Set<Long>) mapCache.get(key);
    }


    private void updateStoriesSet(List<HNTopicResponseDTO> hnTopicResponseDTOS){
        RMapCache<Object, Object> mapCache = redissonClient.getMapCache(STORIES_SET);
        String key = "storyIdSet";
        if(mapCache.size() == 0){
            HashSet<Long> set = new HashSet<>();
            for(HNTopicResponseDTO hnTopicResponseDTO : hnTopicResponseDTOS){
                set.add(hnTopicResponseDTO.getId());
            }
            mapCache.put(key, set);
        }else{
            HashSet<Long> set = (HashSet<Long>) mapCache.get(key);
            for(HNTopicResponseDTO hnTopicResponseDTO : hnTopicResponseDTOS){
                set.add(hnTopicResponseDTO.getId());
            }
            mapCache.put(key, set);
        }

    }
}
