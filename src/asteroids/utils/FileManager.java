package asteroids.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 * A series of static methods that handle interactions with local files
 */
public class FileManager {

    /* The path to store e.g. high score file. A relative path from the root dir of proj */
    public static final String GAMEDATA_DIR = "gamedata/";

    /* File that stores score record */
    public static final String SCORES_FILE = "scores.txt";

    // Method to create a file if it doesn't exist
    public static void createFile(String fileName) {
        File file = new File(GAMEDATA_DIR + fileName);
        try {
            if (file.createNewFile()) {
                System.out.println("File created: " + fileName);
            } else {
                System.out.println("File already exists: " + fileName);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while creating the file: " + e.getMessage());
        }
    }

    // Method to write content to a file
    public static void writeToFile(String fileName, String content) throws FileNotFoundException {
        try (FileWriter writer = new FileWriter(GAMEDATA_DIR + fileName, true)) {
            writer.write(content + System.lineSeparator());
            System.out.println("Content has been appended to file: " + fileName);
            writer.close();
        } catch (FileNotFoundException e) {
            throw e;
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }

    public static void writeOrCreate(String fileName, String content) {
        System.out.println("[writeOrCreate]");
        try {
            writeToFile(fileName, content);
            return;
        } catch (FileNotFoundException e) {
            createFile(fileName);
            try {
                writeToFile(fileName, content);
            } catch (Exception e2) {
                System.out.println("An error occurred while writing to the file: " + e2.getMessage());
            }
        } catch (Exception e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }

    // Method to read content from a file
    public static String readFile(String fileName) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(GAMEDATA_DIR + fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }
        return content.toString();
    }

}