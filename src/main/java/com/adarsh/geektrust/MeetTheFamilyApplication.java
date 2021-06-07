package com.adarsh.geektrust;

import com.adarsh.geektrust.repositories.FamilyTreeRepository;
import com.adarsh.geektrust.services.FamilyTreeService;
import com.adarsh.geektrust.util.FileProcessor;

public class MeetTheFamilyApplication {
    public static void main(String[] args) {

        String pathOfInputFile = args[0];
        FamilyTreeRepository familyTreeRepository = new FamilyTreeRepository();
        FamilyTreeService service = new FamilyTreeService(familyTreeRepository);

        FileProcessor fileProcessor = new FileProcessor();
        String initFilePath = "src/main/resources/init.txt";
        fileProcessor.processInitFile(service, initFilePath);

        System.out.println("-------------seed data is completed-------------");

        fileProcessor.processInputFile(service, pathOfInputFile);
    }
}
