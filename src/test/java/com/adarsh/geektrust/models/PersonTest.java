package com.adarsh.geektrust.models;

import com.adarsh.geektrust.AppConstants;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

    @Test
    public void should_throw_exception_is_name_is_null() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Person("22", null, null));
        assertEquals(exception.getMessage(), AppConstants.Message.ILLEGAL_ARGUMENT);
    }

    @Test
    public void isMale_should_return_true_if_person_is_male() {
        Person person = new Person("name", Gender.MALE, null);
        assertTrue(person.isMale());
    }
//
    @Test
    public void isMale_should_return_false_if_person_is_female() {
        Person person = new Person("name", Gender.FEMALE, null);
        assertFalse(person.isMale());
    }

    @Test
    public void isFemale_should_return_true_if_person_is_female() {
        Person person = new Person("name", Gender.FEMALE,null);
        assertTrue(person.isFemale());
    }

    @Test
    public void isFemale_should_return_false_if_person_is_male() {
        Person person = new Person("name", Gender.MALE, null);
        assertFalse(person.isFemale());
    }

    @Test
    void getRelativesBy_should_return_relatives_of_given_relation() {
        Person person = new Person("name", Gender.MALE, null);
        Person uncle = new Person("uncle", Gender.MALE, null);

        person.addRelative(uncle, RelationshipType.MATERNAL_UNCLE);
        List<Person> relatives = person.getRelativesBy(RelationshipType.MATERNAL_UNCLE);

        assertEquals(1, relatives.size());
        assertEquals(uncle, relatives.get(0));
    }

    @Test
    void getRelativesBy_should_return_empty_list_if_no_relation_exist_with_the_type() {
        Person person = new Person("name", Gender.MALE, null);
        List<Person> relatives = person.getRelativesBy(RelationshipType.MATERNAL_UNCLE);

        assertEquals(0, relatives.size());
    }

    @Test
    void getRelativesBy_should_return_siblings_of_given_relation() {
        Person mother = new Person("mother", Gender.FEMALE, null);
        Person person = new Person("name", Gender.MALE, mother);
        Person brother = new Person("brother", Gender.MALE, mother);
        Person sister = new Person("sister", Gender.FEMALE, mother);
        Person uncle = new Person("uncle", Gender.FEMALE, null);

        person.addRelative(brother, RelationshipType.SIBLINGS);
        person.addRelative(sister, RelationshipType.SIBLINGS);
        person.addRelative(uncle, RelationshipType.MATERNAL_UNCLE);

        List<Person> siblings = person.getRelativesBy(RelationshipType.SIBLINGS);
        System.out.println(Arrays.toString(siblings.toArray()));

        assertEquals(2, siblings.size());
        assertTrue(siblings.contains(brother));
        assertTrue(siblings.contains(sister));
        assertFalse(siblings.contains(uncle));
    }
//
    @Test
    void getSiblings_should_return_empty_list_if_there_are_no_siblings() {
        Person mother = new Person("mother", Gender.FEMALE, null);
        Person person = new Person("name", Gender.MALE, mother);
        Person uncle = new Person("uncle", Gender.FEMALE, null);

        person.addRelative(uncle, RelationshipType.MATERNAL_UNCLE);

        List<Person> siblings = person.getRelativesBy(RelationshipType.SIBLINGS);
        System.out.println(Arrays.toString(siblings.toArray()));

        assertEquals(0, siblings.size());
    }


    //
//    @Test
//    public void isQueen_should_return_true_if_person_is_ultimate_queen() {
//        Person person = new Person.Builder("name", Gender.FEMALE).build();
//        assertTrue(person.isQueen());
//    }
//
//    @Test
//    public void isQueen_should_return_false_if_person_is_not_ultimate_queen() {
//        Person mother = new Person.Builder("mother", Gender.FEMALE).build();
//        Person person = new Person.Builder("name", Gender.MALE).withMother(mother).build();
//        assertFalse(person.isQueen());
//    }
//
//    @Test
//    public void isQueen_should_return_false_if_person_is_male() {
//        Person person = new Person.Builder("name", Gender.MALE).build();
//        assertFalse(person.isQueen());
//    }
//
}