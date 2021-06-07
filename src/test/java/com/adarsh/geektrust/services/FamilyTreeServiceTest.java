package com.adarsh.geektrust.services;

import com.adarsh.geektrust.AppConstants;
import com.adarsh.geektrust.exceptions.InvalidMotherGenderException;
import com.adarsh.geektrust.exceptions.PersonNotFoundException;
import com.adarsh.geektrust.models.Person;
import com.adarsh.geektrust.models.RelationshipType;
import com.adarsh.geektrust.repositories.FamilyTreeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FamilyTreeServiceTest {
    @Mock
    private FamilyTreeRepository familyTreeRepository;
    @Mock
    private Person person;
    @InjectMocks
    private FamilyTreeService service;

    @Test
    void addChild_should_add_child_to_the_given_mother() {
        String motherName = "motherName";
        Person mother = mock(Person.class);
        when(familyTreeRepository.findPersonByName(motherName)).thenReturn(mother);

        String res = assertDoesNotThrow(() -> service.addChild("name", motherName, "male"));

        verify(familyTreeRepository, times(1)).addPerson(any(Person.class));
        verify(mother, times(1)).addChild(any(Person.class));
        assertEquals(AppConstants.Message.CHILD_ADDITION_SUCCEEDED, res);
    }

    @Test
    void addChild_should_throw_exception_if_mother_is_not_present_in_the_tree() throws PersonNotFoundException {
        String motherName = "motherName";
        Person mother = mock(Person.class);
        Person father = mock(Person.class);
        when(familyTreeRepository.findPersonByName(motherName)).thenReturn(null);

        PersonNotFoundException exception = assertThrows(PersonNotFoundException.class,
                () -> service.addChild("name", motherName, "male"));

        verify(familyTreeRepository, times(0)).addPerson(any(Person.class));
        verify(mother, times(0)).addChild(any(Person.class));
        verify(father, times(0)).addChild(any(Person.class));
        assertEquals(AppConstants.Message.PERSON_NOT_FOUND, exception.getMessage());
    }

    @Test
    void addChild_should_throw_exception_if_given_mother_gender_is_male() throws PersonNotFoundException {
        String motherName = "motherName";
        Person mother = mock(Person.class);
        when(familyTreeRepository.findPersonByName(motherName)).thenReturn(mother);
        when(mother.isMale()).thenReturn(true);

        InvalidMotherGenderException exception = assertThrows(InvalidMotherGenderException.class,
                () -> service.addChild("name", motherName, "male"));

        verify(familyTreeRepository, times(0)).addPerson(any(Person.class));
        verify(mother, times(0)).addChild(any(Person.class));
        assertEquals(AppConstants.Message.CHILD_ADDITION_FAILED, exception.getMessage());
    }


    @Test
    void getRelativesOf_should_call_person_fetch_relation_by_type() {
        when(familyTreeRepository.findPersonByName(person.getName())).thenReturn(person);
        assertDoesNotThrow(() -> service.getRelativesOf(person.getName(),
                RelationshipType.MATERNAL_UNCLE.getValue()));

        verify(person, times(1)).getRelativesBy(RelationshipType.MATERNAL_UNCLE);
    }

    @Test
    void getRelativesOf_throw_exception_if_given_person_is_not_present_in_the_tree() {
        when(familyTreeRepository.findPersonByName(person.getName())).thenReturn(null);
        PersonNotFoundException exception = assertThrows(PersonNotFoundException.class,
                () -> service.getRelativesOf(person.getName(), RelationshipType.MATERNAL_UNCLE.getValue()));

        verify(person, times(0)).getRelativesBy(any(RelationshipType.class));
        assertEquals(AppConstants.Message.PERSON_NOT_FOUND, exception.getMessage());
    }


    @Test
    void initializeFamily_should_initialize_family_tree() {
        service.initializeFamily("head_name", "Male");
        verify(familyTreeRepository, times(1)).addPerson(any(Person.class));
    }

    @Test
    void addSpouse_should_throw_exception_if_given_spouse_does_not_exist_in_the_family() {
        assertThrows(PersonNotFoundException.class, () ->
                service.addSpouse("new_member_name", "random_spouse_name", "female"));
    }

    @Test
    void addSpouse_should_add_new_child_and_set_spouse_if_spouse_exists_in_the_family(){
        String spouseName = "random_spouse_name";
        Person spouse = mock(Person.class);
        when(familyTreeRepository.findPersonByName(spouseName)).thenReturn(spouse);

        assertDoesNotThrow(() ->
                service.addSpouse("new_member_name", spouseName, "female"));

        verify(spouse, times(1)).setSpouse(any(Person.class));
        verify(familyTreeRepository, times(1)).addPerson(any(Person.class));
    }
}