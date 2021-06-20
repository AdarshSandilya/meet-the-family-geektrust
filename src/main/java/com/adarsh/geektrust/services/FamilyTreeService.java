package com.adarsh.geektrust.services;

import com.adarsh.geektrust.AppConstants;
import com.adarsh.geektrust.exceptions.InvalidMotherGenderException;
import com.adarsh.geektrust.exceptions.PersonNotFoundException;
import com.adarsh.geektrust.models.Gender;
import com.adarsh.geektrust.models.Person;
import com.adarsh.geektrust.models.RelationshipType;
import com.adarsh.geektrust.repositories.FamilyTreeRepository;
import com.adarsh.geektrust.strategies.FetchRelationStrategyFactory;
import com.adarsh.geektrust.strategies.RelationStrategy;

import java.util.List;

public class FamilyTreeService {

    private final FamilyTreeRepository familyTreeRepository;
    private final FetchRelationStrategyFactory strategyFactory;

    public FamilyTreeService(FamilyTreeRepository familyTreeRepository, FetchRelationStrategyFactory strategyFactory) {
        this.familyTreeRepository = familyTreeRepository;
        this.strategyFactory = strategyFactory;
    }

    public String addChild(String name, String motherName, String gender) throws PersonNotFoundException, InvalidMotherGenderException {
        Gender personGender = Gender.valueOf(gender.toUpperCase());
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
        Person person = familyTreeRepository.findPersonByName(name);
        if (person == null)
            throw new PersonNotFoundException(AppConstants.Message.PERSON_NOT_FOUND);
        RelationshipType relationshipType = RelationshipType.fromString(relation);
        RelationStrategy strategy = strategyFactory.getStrategy(relationshipType);
        return strategy.apply(person);
    }

    public void initializeFamily(String familyHeadName, String gender) {
        Gender personGender = Gender.valueOf(gender.toUpperCase());
        Person familyHead = new Person(familyHeadName, personGender, null);
        familyTreeRepository.addPerson(familyHead);
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
        familyTreeRepository.addPerson(person);
    }
}
