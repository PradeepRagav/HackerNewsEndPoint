package com.assignment.hackernewsapi.model.datastore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HNComment extends HNBaseModel {
    private String text;
    private HNUserResponseDTO hnUser;

    @Override public String toString() {
        return "HNComment{" + "text='" + text + '\'' + ", hnUser=" + hnUser + '}';
    }
}
