package com.adarsh.geektrust.repositories;

import com.adarsh.geektrust.models.Person;

import java.util.HashSet;

public class FamilyTreeRepository {

    HashSet<Person> persons;

    public FamilyTreeRepository() {
        this.persons = new HashSet<>();
    }

    public Person findPersonByName(String name) {
        return persons.stream().filter(person -> person.getName().equals(name)).findFirst().orElse(null);
    }

    public void addPerson(Person person) {
        persons.add(person);
    }
}
