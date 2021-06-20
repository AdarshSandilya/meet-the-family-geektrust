package com.adarsh.geektrust.models;

import com.adarsh.geektrust.AppConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Person {
    
    private final String name;
    private final Gender gender;
    private final Person mother;
    private final Person father;
    private Person spouse;
    private final List<Person> children = new ArrayList<>();

    public void setSpouse(Person spouse) {
        this.spouse = spouse;
    }

    public Person(String name, Gender gender, Person mother) {
        if (name.isEmpty() || Objects.isNull(gender))
            throw new IllegalArgumentException(AppConstants.Message.ILLEGAL_ARGUMENT);
        this.name = name;
        this.gender = gender;
        this.mother = mother;
        this.father = assignFather(this.mother);
    }

    private Person assignFather(Person mother) {
        if (mother == null) return null;
        return mother.getSpouse();
    }

    public List<Person> getDaughters() {
        return children.stream().filter(Person::isFemale).collect(Collectors.toList());
    }

    public List<Person> getSons() {
        return children.stream().filter(Person::isMale).collect(Collectors.toList());
    }

    public List<Person> getSisterInLaws() {
        List<Person> spouseSisters = new ArrayList<>();
        List<Person> siblingsWife = new ArrayList<>();
        if (spouse != null) {
            spouseSisters = spouse.getSisters();
        }
        if (!getBrothers().isEmpty())
            siblingsWife = getBrothers().stream()
                    .map(Person::getSpouse)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        return Stream.concat(spouseSisters.stream(), siblingsWife.stream()).collect(Collectors.toList());
    }

    public List<Person> getBrotherInLaws() {
        List<Person> spouseBrothers = new ArrayList<>();
        List<Person> siblingsHusband = new ArrayList<>();
        if (spouse != null) {
            spouseBrothers = spouse.getBrothers();
        }
        if (!getSisters().isEmpty())
            siblingsHusband = getSisters().stream()
                    .map(Person::getSpouse)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        return Stream.concat(spouseBrothers.stream(), siblingsHusband.stream()).collect(Collectors.toList());
    }

    public List<Person> getMaternalAunts() {
        return mother.getSisters();
    }

    public List<Person> getMaternalUncles() {
        return mother.getBrothers();
    }

    public List<Person> getPaternalAunts() {
        return father.getSisters();
    }

    public List<Person> getPaternalUncles() {
        return father.getBrothers();
    }

    public List<Person> getSisters() {
        List<Person> daughters = mother.getDaughters();
        if(isFemale())
            daughters.remove(this);
        return daughters;
    }

    public List<Person> getBrothers() {
        List<Person> brothers = mother.getSons();
        if(isMale())
            brothers.remove(this);
        return brothers;
    }

    public void addChild(Person child) {
        children.add(child);
    }

    public List<Person> getSiblings() {
        return Stream.concat(getSisters().stream(), getBrothers().stream()).collect(Collectors.toList());
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
