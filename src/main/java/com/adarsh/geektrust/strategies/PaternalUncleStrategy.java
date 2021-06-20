package com.adarsh.geektrust.strategies;

import com.adarsh.geektrust.models.Person;

import java.util.List;

public class PaternalUncleStrategy implements RelationStrategy {

    @Override
    public List<Person> apply(Person person) {
        return person.getPaternalUncles();
    }
}
