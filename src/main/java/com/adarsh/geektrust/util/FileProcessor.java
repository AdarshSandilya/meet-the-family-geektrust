package com.adarsh.geektrust.util;

import com.adarsh.geektrust.AppConstants;
import com.adarsh.geektrust.exceptions.InvalidCommandException;
import com.adarsh.geektrust.exceptions.InvalidMotherGenderException;
import com.adarsh.geektrust.exceptions.PersonNotFoundException;
import com.adarsh.geektrust.models.Person;
import com.adarsh.geektrust.services.FamilyTreeService;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class FileProcessor {

    public void processInputFile(FamilyTreeService service, String inputFilePath) {
        File file = new File(inputFilePath);
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String[] commands = sc.nextLine().split(" ");
                String action = commands[0];
                switch (action) {
                    case AppConstants.Commands.ADD_CHILD:
                        try {
                            String res = service.addChild(commands[2], commands[1], commands[3]);
                            System.out.println(res);
                        } catch (PersonNotFoundException | InvalidMotherGenderException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case AppConstants.Commands.ADD_SPOUSE:
                        try {
                            service.addSpouse(commands[2], commands[1], commands[3]);
                        }
                        catch (PersonNotFoundException e){
                            System.out.println(e.getMessage());}
                        break;
                    case AppConstants.Commands.GET_RELATIONSHIP:
                        try {
                            List<Person> res = service.getRelativesOf(commands[1], commands[2]);
                            if (res.isEmpty())
                                System.out.println(AppConstants.Message.NONE);
                            else {
                                List<String> relatives = res.stream().map(Person::getName).collect(Collectors.toList());
                                System.out.println(String.join(" ", relatives));
                            }
                        } catch (PersonNotFoundException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    default:
                        System.out.println("Invalid command " +  action);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Oops! File Not Found!! Please provide valid file path");
        }
    }

    public void processInitFile(FamilyTreeService service, String initFilePath) {
        File file = new File(initFilePath);
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String[] commands = sc.nextLine().split(" ");
                String action = commands[0];
                switch (action) {
                    case AppConstants.Commands.ADD_KING:
                        service.initializeFamily(commands[1], commands[2]);
                        break;
                    case AppConstants.Commands.ADD_SPOUSE:
                        service.addSpouse(commands[2], commands[1], commands[3]);
                        break;
                    case AppConstants.Commands.ADD_CHILD:
                        service.addChild(commands[2], commands[1], commands[3]);
                        break;
                    default:
                        String message = String.format("Invalid command %s", action);
                        throw new InvalidCommandException(message);
                }
            }
        } catch (FileNotFoundException | InvalidCommandException | PersonNotFoundException | InvalidMotherGenderException e) {
            System.out.println(e.getMessage());
        }
    }
}
