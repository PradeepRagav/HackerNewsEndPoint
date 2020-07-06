package com.assignment.hackernewsapi.model.datastore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HNComment extends HNBaseModel {
    private String text;
    private String hnHandle;
    private Long hnAge;

    @Override public String toString() {
        return "HNComment{" + "text='" + text + '\'' + ", hnHandle='" + hnHandle + '\'' + ", hnAge=" + hnAge + '}';
    }
}
