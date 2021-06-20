package com.adarsh.geektrust.strategies;

import com.adarsh.geektrust.models.Person;

import java.util.List;

public interface RelationStrategy {
    List<Person> apply(Person person);
}
