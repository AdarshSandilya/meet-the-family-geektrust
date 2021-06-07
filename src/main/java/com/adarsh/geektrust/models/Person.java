package com.adarsh.geektrust.models;

import com.adarsh.geektrust.AppConstants;

import java.util.*;

public class Person {

    private final String name;
    private final Gender gender;
    private static HashMap<Person, RelationshipType> relations;
    private Person spouse;

    public void setSpouse(Person spouse) {
        this.spouse = spouse;
    }

    private final Person mother;

    public Person(String name, Gender gender, Person mother) {
        if (name.isEmpty() || Objects.isNull(gender))
            throw new IllegalArgumentException(AppConstants.Message.ILLEGAL_ARGUMENT);
        this.name = name;
        this.gender = gender;
        this.mother = mother;
        relations = new HashMap<>();
        assignParents(mother);
    }

    private void assignParents(Person mother) {
        if (mother == null) return;
        relations.put(mother, RelationshipType.MOTHER);
        relations.put(mother.getSpouse(), RelationshipType.FATHER);
    }

    public void addChild(Person child) {
        relations.put(child, RelationshipType.CHILD);
    }

    public void addRelative(Person person, RelationshipType type) {
        relations.put(person, type);
    }

    public List<Person> getRelativesBy(RelationshipType type) {
        ArrayList<Person> relatives = new ArrayList<>();
        for (Map.Entry<Person, RelationshipType> entry : relations.entrySet()) {
            if (entry.getValue().equals(type))
                relatives.add(entry.getKey());
        }
        return relatives;
    }

    public Person getMother() {
        return this.mother;
    }

    public boolean isMale() {
        return Gender.MALE == gender;
    }

    public boolean isFemale() {
        return Gender.FEMALE == gender;
    }

    public String getName() {
        return this.name;
    }

    public Person getSpouse() {
        return spouse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(name, person.name) && gender == person.gender && Objects.equals(spouse, person.spouse) && Objects.equals(mother, person.mother);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", gender=" + gender +
                ", spouse=" + spouse +
                ", mother=" + mother +
                '}';
    }
}
