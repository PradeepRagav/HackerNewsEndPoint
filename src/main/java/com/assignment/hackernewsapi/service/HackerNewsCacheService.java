package com.assignment.hackernewsapi.service;

import com.assignment.hackernewsapi.configuration.ApplicationConstants;
import com.assignment.hackernewsapi.model.datastore.HNCommentresponseDTO;
import com.assignment.hackernewsapi.model.datastore.HNStoryResponseDTO;
import com.assignment.hackernewsapi.model.datastore.HNUser;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class HackerNewsCacheService implements HackerNewsCacheInterface {

    private static final Logger logger = LoggerFactory.getLogger(HackerNewsCacheService.class);

    @Autowired
    private RedissonClient redissonClient;


    @Override public HNStoryResponseDTO getStory(Long storyId) {
        RMapCache<Object, Object> mapCache =
            redissonClient.getMapCache(ApplicationConstants.RedisConstants.STORY_ID_MAP);
        if(mapCache.size() != 0 && mapCache.get(String.valueOf(storyId)) != null){
            return (HNStoryResponseDTO) mapCache.get(String.valueOf(storyId));
        }
        return new HNStoryResponseDTO();
    }

    @Override public List<HNStoryResponseDTO> getTopStories() {
        RMapCache<Object, Object> mapCache =
            redissonClient.getMapCache(ApplicationConstants.RedisConstants.TOP_STORIES);
        String key = "topstories";
        if(mapCache.size() != 0){
            return (List<HNStoryResponseDTO>) mapCache.get(key);
        }
        return new ArrayList<>();
    }

    @Override public List<HNCommentresponseDTO> getTopComments(Long storyId) {
        RMapCache<Object, Object> mapCache =
            redissonClient.getMapCache(ApplicationConstants.RedisConstants.TOP_COMMENTS);
        List<HNCommentresponseDTO> hnCommentresponseDTOS = (List<HNCommentresponseDTO>) mapCache
            .get(String.valueOf(storyId));
        return hnCommentresponseDTOS;
    }

    @Override public void saveStory(HNStoryResponseDTO hnTopicResponseDTO) {
        RMapCache<Object, Object> mapCache =
            redissonClient.getMapCache(ApplicationConstants.RedisConstants.STORY_ID_MAP);
        mapCache.put(String.valueOf(hnTopicResponseDTO.getId()), hnTopicResponseDTO);
    }


    @Override public void saveTopTenStories(List<HNStoryResponseDTO> hnTopicResponseDTOS) {
        RMapCache<Object, Object> mapCache =
            redissonClient.getMapCache(ApplicationConstants.RedisConstants.TOP_STORIES);
        String key = "topstories";
        mapCache.put(key, hnTopicResponseDTOS);
        updateStoriesSet(hnTopicResponseDTOS);
    }

    @Override public void saveTopTenComments(Long storyId, List<HNCommentresponseDTO> hnTopicResponseDTOS) {
        RMapCache<Object, Object> mapCache =
            redissonClient.getMapCache(ApplicationConstants.RedisConstants.TOP_COMMENTS);
        mapCache.put(String.valueOf(storyId),hnTopicResponseDTOS);
    }

    public Set<Long> getPastStoryIds(){
        RMapCache<Object, Object> mapCache =
            redissonClient.getMapCache(ApplicationConstants.RedisConstants.STORIES_SET);
        String key = "storyIdSet";
        return (Set<Long>) mapCache.get(key);
    }

    @Override public HNUser getUser(String userId) {
        RMapCache<Object, Object> mapCache =
            redissonClient.getMapCache(ApplicationConstants.RedisConstants.USER_ID_MAP);
        if(mapCache.size() != 0 && mapCache.get(String.valueOf(userId)) != null){
            return (HNUser) mapCache.get(String.valueOf(userId));
        }
        return new HNUser();
    }

    @Override public void saveUser(HNUser hnUser) {
        RMapCache<Object, Object> mapCache =
            redissonClient.getMapCache(ApplicationConstants.RedisConstants.USER_ID_MAP);
        mapCache.put(String.valueOf(hnUser.getId()), hnUser);
    }

    private void updateStoriesSet(List<HNStoryResponseDTO> hnTopicResponseDTOS){
        RMapCache<Object, Object> mapCache =
            redissonClient.getMapCache(ApplicationConstants.RedisConstants.STORIES_SET);
        String key = "storyIdSet";
        if(mapCache.size() == 0){
            HashSet<Long> set = new HashSet<>();
            for(HNStoryResponseDTO hnTopicResponseDTO : hnTopicResponseDTOS){
                set.add(hnTopicResponseDTO.getId());
            }
            mapCache.put(key, set);
        }else{
            HashSet<Long> set = (HashSet<Long>) mapCache.get(key);
            for(HNStoryResponseDTO hnTopicResponseDTO : hnTopicResponseDTOS){
                set.add(hnTopicResponseDTO.getId());
            }
            mapCache.put(key, set);
        }
    }
}
