package com.example.projekt_dva.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class LoadFile {

    private static final Logger logger = LoggerFactory.getLogger(LoadFile.class);

    private List<String> personIDList = new ArrayList<>();

    public LoadFile() {
        // Pokud chceš načítat soubor hned při vytvoření instance, můžeš to udělat zde
        // loadPersonIDFromFile("dataPersonId.txt");
    }

    public List<String> getPersonIDList() {
        return personIDList;
    }

    public void setPersonIDList(List<String> personIDList) {
        this.personIDList = personIDList;
    }

    public void loadPersonIDFromFile(String fileName) {
        try (InputStream inputStream = getClass().getResourceAsStream("/" + fileName)) {
            if (inputStream == null) {
                logger.error("Soubor nenalezen: " + fileName);
                throw new RuntimeException("Soubor nenalezen: " + fileName);
            }

            try (Scanner scanner = new Scanner(inputStream)) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    personIDList.add(line);
                }
            }
        } catch (Exception e) {
            logger.error("Chyba při načítání souboru: " + fileName, e);
            throw new RuntimeException("Chyba při načítání souboru: " + fileName, e);
        }
    }
}
