package com.assignment.hackernewsapi.model.datastore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HNBaseModel {
    private Long id;
    private Long time;
    private String by;

    @Override public String toString() {
        return "HNBaseModel{" + "id=" + id + ", time=" + time + ", by='" + by + '\'' + '}';
    }
}
