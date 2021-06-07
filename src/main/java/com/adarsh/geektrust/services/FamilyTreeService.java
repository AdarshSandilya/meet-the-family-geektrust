package com.adarsh.geektrust.services;

import com.adarsh.geektrust.AppConstants;
import com.adarsh.geektrust.exceptions.InvalidMotherGenderException;
import com.adarsh.geektrust.exceptions.PersonNotFoundException;
import com.adarsh.geektrust.models.Gender;
import com.adarsh.geektrust.models.Person;
import com.adarsh.geektrust.models.RelationshipType;
import com.adarsh.geektrust.repositories.FamilyTreeRepository;

import java.util.List;

public class FamilyTreeService {

    private final FamilyTreeRepository familyTreeRepository;

    public FamilyTreeService(FamilyTreeRepository familyTreeRepository) {
        this.familyTreeRepository = familyTreeRepository;
    }

    public String addChild(String name, String motherName, String gender) throws PersonNotFoundException, InvalidMotherGenderException {
        Gender personGender = Gender.valueOf(gender.toUpperCase());
        System.out.println(" adding the child -----" + name);
        Person mother = familyTreeRepository.findPersonByName(motherName);
        if (mother == null)
            throw new PersonNotFoundException(AppConstants.Message.PERSON_NOT_FOUND);
        if (mother.isMale())
            throw new InvalidMotherGenderException(AppConstants.Message.CHILD_ADDITION_FAILED);
        Person child = new Person(name, personGender, mother);
        mother.addChild(child);
        familyTreeRepository.addPerson(child);
        return AppConstants.Message.CHILD_ADDITION_SUCCEEDED;
    }

    public List<Person> getRelativesOf(String name, String relation) throws PersonNotFoundException {
        System.out.printf("getting relationship %s for %s%n", relation, name);
        Person person = familyTreeRepository.findPersonByName(name);
        if (person == null)
            throw new PersonNotFoundException(AppConstants.Message.PERSON_NOT_FOUND);

        RelationshipType relationshipType = RelationshipType.fromString(relation);

        return person.getRelativesBy(relationshipType);
    }

    public void initializeFamily(String familyHeadName, String gender) {
        Gender personGender = Gender.valueOf(gender.toUpperCase());
        Person familyHead = new Person(familyHeadName, personGender, null);
        familyTreeRepository.addPerson(familyHead);
        System.out.println("added the king ---" + familyHeadName);
    }

    public void addSpouse(String personName, String spouseName, String gender) throws PersonNotFoundException {
        Person spouse = familyTreeRepository.findPersonByName(spouseName);
        if (spouse == null) {
            String message = String.format("%s does not belong to  the family", spouseName);
            throw new PersonNotFoundException(message);
        }
        Gender personGender = Gender.valueOf(gender.toUpperCase());
        Person person = new Person(personName, personGender, spouse.getMother());
        person.setSpouse(spouse);
        spouse.setSpouse(person);
        System.out.println("adding the person -----" + personName);
        familyTreeRepository.addPerson(person);
    }
}
