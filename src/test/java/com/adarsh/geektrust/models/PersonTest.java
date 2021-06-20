package com.adarsh.geektrust.models;

import com.adarsh.geektrust.AppConstants;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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

    @Test
    public void isMale_should_return_false_if_person_is_female() {
        Person person = new Person("name", Gender.FEMALE, null);
        assertFalse(person.isMale());
    }

    @Test
    public void isFemale_should_return_true_if_person_is_female() {
        Person person = new Person("name", Gender.FEMALE, null);
        assertTrue(person.isFemale());
    }

    @Test
    public void isFemale_should_return_false_if_person_is_male() {
        Person person = new Person("name", Gender.MALE, null);
        assertFalse(person.isFemale());
    }

    @Test
    void getDaughters_should_return_list_of_daughters() {
        Person daughter1 = mock(Person.class);
        Person daughter2 = mock(Person.class);
        Person son = mock(Person.class);

        Person person = new Person("name", Gender.MALE, null);
        person.addChild(daughter1);
        person.addChild(daughter2);
        person.addChild(son);

        when(daughter1.isFemale()).thenReturn(true);
        when(daughter2.isFemale()).thenReturn(true);
        when(son.isFemale()).thenReturn(false);

        List<Person> daughters = person.getDaughters();
        assertEquals(2, daughters.size());
        assertFalse(daughters.contains(son));
    }


    @Test
    void getDaughters_should_empty_list_if_person_has_no_daughters() {
        Person son = mock(Person.class);

        Person person = new Person("name", Gender.MALE, null);
        person.addChild(son);

        when(son.isFemale()).thenReturn(false);

        List<Person> daughters = person.getDaughters();
        assertEquals(0, daughters.size());
    }

    @Test
    void getSons_should_return_list_of_sons() {
        Person son1 = mock(Person.class);
        Person son2 = mock(Person.class);
        Person daughter = mock(Person.class);

        Person person = new Person("name", Gender.MALE, null);
        person.addChild(son1);
        person.addChild(son2);
        person.addChild(daughter);

        when(son1.isMale()).thenReturn(true);
        when(son2.isMale()).thenReturn(true);
        when(daughter.isMale()).thenReturn(false);

        List<Person> sons = person.getSons();
        assertEquals(2, sons.size());
        assertFalse(sons.contains(daughter));
    }

    @Test
    void getSons__should_empty_list_if_person_has_no_sons() {
        Person daughter = mock(Person.class);

        Person person = new Person("name", Gender.MALE, null);
        person.addChild(daughter);

        when(daughter.isMale()).thenReturn(false);

        List<Person> sons = person.getDaughters();
        assertEquals(0, sons.size());
    }

    @Test
    void getSisterInLaws_should_return_list_of_sister_in_laws() {
        Person mother = mock(Person.class);
        Person spouse = mock(Person.class);
        Person spouseSister = mock(Person.class);
        Person brother = mock(Person.class);
        Person brotherWife = mock(Person.class);
        Person person = new Person("name", Gender.MALE, mother);

        person.setSpouse(spouse);

        when(mother.getSons()).thenReturn(Collections.singletonList(brother));
        when(spouse.getSisters()).thenReturn(Collections.singletonList(spouseSister));
        when(brother.getSpouse()).thenReturn(brotherWife);

        List<Person> sisterInLaws = person.getSisterInLaws();

        assertEquals(2, sisterInLaws.size());
    }

    @Test
    void getSisterInLaws_should_only_return_siblings_wife_if_spouse_is_not_present() {
        Person mother = mock(Person.class);
        Person brother = mock(Person.class);
        Person brotherWife = mock(Person.class);
        Person person = new Person("name", Gender.MALE, mother);


        when(mother.getSons()).thenReturn(Collections.singletonList(brother));
        when(brother.getSpouse()).thenReturn(brotherWife);

        List<Person> sisterInLaws = person.getSisterInLaws();

        assertEquals(1, sisterInLaws.size());
        assertEquals(brotherWife, sisterInLaws.get(0));
    }

    @Test
    void getSisterInLaws_should_only_return_spouse_sisters_if_brother_wife_is_not_present() {
        Person mother = mock(Person.class);
        Person brother = mock(Person.class);
        Person spouse = mock(Person.class);
        Person spouseSister = mock(Person.class);
        Person person = new Person("name", Gender.MALE, mother);

        person.setSpouse(spouse);

        when(spouse.getSisters()).thenReturn(Collections.singletonList(spouseSister));
        when(mother.getSons()).thenReturn(Collections.singletonList(brother));

        List<Person> sisterInLaws = person.getSisterInLaws();

        assertEquals(1, sisterInLaws.size());
        assertEquals(spouseSister, sisterInLaws.get(0));
    }

    @Test
    void getBrotherInLaws_should_return_list_of_brother_in_laws() {
        Person mother = mock(Person.class);
        Person spouse = mock(Person.class);
        Person spouseBrother = mock(Person.class);
        Person sister = mock(Person.class);
        Person sisterHusband = mock(Person.class);
        Person person = new Person("name", Gender.MALE, mother);

        person.setSpouse(spouse);

        when(mother.getDaughters()).thenReturn(Collections.singletonList(sister));
        when(spouse.getBrothers()).thenReturn(Collections.singletonList(spouseBrother));
        when(sister.getSpouse()).thenReturn(sisterHusband);

        List<Person> brotherInLaws = person.getBrotherInLaws();

        assertEquals(2, brotherInLaws.size());
    }

    @Test
    void getBrotherInLaws_should_only_return_spouse_brother_if_sibling_husband_is_not_present() {
        Person mother = mock(Person.class);
        Person spouse = mock(Person.class);
        Person spouseBrother = mock(Person.class);
        Person sister = mock(Person.class);
        Person person = new Person("name", Gender.MALE, mother);

        person.setSpouse(spouse);

        when(mother.getDaughters()).thenReturn(Collections.singletonList(sister));
        when(spouse.getBrothers()).thenReturn(Collections.singletonList(spouseBrother));

        List<Person> brotherInLaws = person.getBrotherInLaws();

        assertEquals(1, brotherInLaws.size());
        assertEquals(spouseBrother, brotherInLaws.get(0));
    }

    @Test
    void getBrotherInLaws_should_only_return_sibling_husband_if_spouse_brothers_are_not_present() {
        Person mother = mock(Person.class);
        Person spouse = mock(Person.class);
        Person sister = mock(Person.class);
        Person sisterHusband = mock(Person.class);
        Person person = new Person("name", Gender.MALE, mother);

        person.setSpouse(spouse);

        when(mother.getDaughters()).thenReturn(Collections.singletonList(sister));
        when(sister.getSpouse()).thenReturn(sisterHusband);

        List<Person> brotherInLaws = person.getBrotherInLaws();

        assertEquals(1, brotherInLaws.size());
        assertEquals(sisterHusband, brotherInLaws.get(0));
    }

    @Test
    void getBrotherInLaws_should_return_empty_list_if_spouse_brother_and_siblings_husband_not_present() {
        Person mother = mock(Person.class);
        Person spouse = mock(Person.class);
        Person sister = mock(Person.class);
        Person person = new Person("name", Gender.MALE, mother);

        person.setSpouse(spouse);

        when(mother.getDaughters()).thenReturn(Collections.singletonList(sister));

        List<Person> brotherInLaws = person.getBrotherInLaws();

        assertEquals(0, brotherInLaws.size());
    }

    @Test
    void getMaternalAunts_should_return_maternal_aunts() {
        Person mother = mock(Person.class);
        Person motherSister = mock(Person.class);
        Person person = new Person("name", Gender.MALE, mother);

        when(mother.getSisters()).thenReturn(Collections.singletonList(motherSister));

        List<Person> maternalAunts = person.getMaternalAunts();
        assertEquals(1, maternalAunts.size());
        assertEquals(motherSister, maternalAunts.get(0));
    }

    @Test
    void getMaternalUncles_should_return_maternal_aunts() {
        Person mother = mock(Person.class);
        Person motherBrother = mock(Person.class);
        Person person = new Person("name", Gender.MALE, mother);

        when(mother.getBrothers()).thenReturn(Collections.singletonList(motherBrother));

        List<Person> maternalAunts = person.getMaternalUncles();
        assertEquals(1, maternalAunts.size());
        assertEquals(motherBrother, maternalAunts.get(0));
    }


    @Test
    void getPaternalAunts_should_return_maternal_aunts() {
        Person mother = mock(Person.class);
        Person father = mock(Person.class);
        Person fatherSister = mock(Person.class);
        when(mother.getSpouse()).thenReturn(father);
        Person person = new Person("name", Gender.MALE, mother);

        when(father.getSisters()).thenReturn(Collections.singletonList(fatherSister));

        List<Person> paternalAunts = person.getPaternalAunts();
        assertEquals(1, paternalAunts.size());
        assertEquals(fatherSister, paternalAunts.get(0));
    }

    @Test
    void getPaternalUncles_should_return_maternal_aunts() {
        Person mother = mock(Person.class);
        Person father = mock(Person.class);
        Person fatherBrother = mock(Person.class);
        when(mother.getSpouse()).thenReturn(father);
        Person person = new Person("name", Gender.MALE, mother);

        when(father.getBrothers()).thenReturn(Collections.singletonList(fatherBrother));

        List<Person> paternalUncles = person.getPaternalUncles();
        assertEquals(1, paternalUncles.size());
        assertEquals(fatherBrother, paternalUncles.get(0));
    }

    @Test
    void getSiblings_should_return_all_siblings() {
        Person mother = mock(Person.class);
        Person brother = mock(Person.class);
        Person sister = mock(Person.class);
        Person person = new Person("name", Gender.MALE, mother);

        when(mother.getSons()).thenReturn(Collections.singletonList(brother));
        when(mother.getDaughters()).thenReturn(Collections.singletonList(sister));

        List<Person> siblings = person.getSiblings();
        assertEquals(2, siblings.size());
    }
}