package com.assignment.hackernewsapi.model.datastore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter
@Service
@NoArgsConstructor
public class HNStoryResponseDTO extends HNStory {
    private List<Long> kids;

    @Override public String toString() {
        return "HNTopicResponseDTO{" + "kids=" + kids + '}';
    }
}
