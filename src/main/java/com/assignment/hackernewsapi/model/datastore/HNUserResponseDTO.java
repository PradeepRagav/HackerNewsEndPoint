package com.assignment.hackernewsapi.model.datastore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class HNUserResponseDTO {
    String hnHandle;
    Integer hnAge;
}
