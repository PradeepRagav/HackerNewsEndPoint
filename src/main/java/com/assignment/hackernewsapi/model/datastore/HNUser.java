package com.assignment.hackernewsapi.model.datastore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HNUser {
    private String id;
    private Integer hnAge;
    private String hnHandle;
}
