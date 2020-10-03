package com.example.reactivedataaccess.assignment.section1;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Car {
    public enum Brand {
        VOLKSWAGEN, TOYOTA, HONDA
    }
    private Brand brand;
    private String model;
}
