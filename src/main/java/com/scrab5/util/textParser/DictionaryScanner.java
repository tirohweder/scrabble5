package com.scrab5.util.textParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import com.scrab5.util.database.CreateDatabase;
import com.scrab5.util.database.Database;
import com.scrab5.util.database.FillDatabase;

public class DictionaryScanner {

  /**
   * Scans the created file of the dictionary for the word searchedWord and returns if its
   * contained.
   *
   * @param searchedWord the String of the laid Word to check
   * @return boolean of the file contains a searched word
   * @author lengist
   */

  // TO-DO gameSession.getCurrentDic();
  public static boolean scan(String searchedWord) {
    /*
     * File fileOne = new File(System.getProperty("user.dir") + System.getProperty("file.separator")
     * + "src/main/resources/com/scrab5/util/textParser/" + DictionaryParser.getNewFileName());
     */
    File fileOne = new File(System.getProperty("user.dir") + System.getProperty("file.separator")
        + "src/main/resources/com/scrab5/util/textParser/"
        + "Built-In Standard DictionaryParsed.txt");
    boolean found = false;
    // String[] possibleLetters = UseDatabase.getAllLetters();
    // String test;

    try {
      Scanner scanner = new Scanner(fileOne);
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        /*
         * if (searchedWord.contains("*")) { for (int j = 0; j < possibleLetters.length; j++) { test
         * = searchedWord.replace((char) 42, possibleLetters[j].charAt(0)); if (line.contains(test))
         * { scanner.close(); return true; } } }
         */
        if (line.contains(searchedWord)) {
          found = true;
        }
      }
      scanner.close();
    } catch (FileNotFoundException e) {
      System.out.println("File not found");
    }
    return found;
  }

  /**
   * main method to test the implementation.
   * 
   * @author lengist
   * @param args
   */
  public static void main(String[] args) {
    Database db = new Database();
    CreateDatabase cdb = new CreateDatabase();
    FillDatabase fd = new FillDatabase();
    fd.fillLetters();


    // DictionaryParser.setCurrentDictionary("words.txt");
    // DictionaryParser.parseFile("words.txt");
    DictionaryParser.setCurrentDictionary("Built-In Standard Dictionary.txt");
    System.out.println(scan("JOK*R"));
    System.out.println(scan("ZZZ"));
  }
}
