package com.assignment.hackernewsapi.model.datastore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HNTopic extends HNBaseModel {
    private String title;
    private String url;
    private Long score;

    @Override public String toString() {
        return "HNTopic{" + "title='" + title + '\'' + ", url='" + url + '\'' + ", score=" + score + '}';
    }
}
