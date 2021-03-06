package com.example.reactivedataaccess.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JokeResponse {
    private String type;
    private Value value;
}
