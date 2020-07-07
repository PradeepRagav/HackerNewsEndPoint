package com.assignment.hackernewsapi.configuration;

public class ApplicationConstants {

    public static final String API_END_POINT = "hacker.news.end.point";
    public static final String REDIS_HOST = "redis.host";
    public static final String REDIS_PORT = "redis.port";
    public static final String REDIS_IDLE_CONNECTIONS = "redis.min.idle.connections";
    public static final String REDIS_MAX_CONNECTIONS = "redis.max.connections";
    public static final String EXECUTOR_SERVICE_POOL_SIZE = "executor.service.core.pool.size";
    public static final String EXECUTOR_SERVICE_MAX_POOL_SIZE = "executor.service.max.pool.size";
    public static final String EXECUTOR_SERVICE_KEEP_ALIVE_TIME = "executor.service.keep.alive.time";
    public static final String SCHEDULED_EXECUTOR_SERVICE_POOL_SIZE = "scheduled.executor.service.pool.size";

    public class RedisConstants{
        public static final String STORY_ID_MAP = "story.id.map";
        public static final String USER_ID_MAP = "user.id.map";
        public static final String TOP_STORIES = "top.stories";
        public static final String TOP_COMMENTS = "top.comments";
        public static final String STORIES_SET = "stories.set";
    }
}
