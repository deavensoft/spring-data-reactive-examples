package com.example.reactivedataaccess.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PersonsWithSameName {
	String name;
	List<Person> personList;
}
