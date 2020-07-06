package com.assignment.hackernewsapi.model.datastore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class HNCommentresponseDTO extends HNComment {
    private List<Long> kids;
    private Long parent;

    @Override public String toString() {
        return "HNCommentresponseDTO{" + "kids=" + kids + ", parent=" + parent + '}';
    }
}
