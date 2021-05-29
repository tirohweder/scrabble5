package com.scrab5.util.parser;

import com.scrab5.ui.Data;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class to scan a document.
 *
 * @author lengist
 */
public class DictionaryScanner {

  /**
   * Scans the created file of the dictionary for the word searchedWord and returns if its
   * contained. The commented sections were for the use of a joker. Because of the implementation of
   * the function that a user can choose which letter to lay to replace the joker tile, this part is
   * no longer needed.
   *
   * @author lengist
   * @param searchedWord the String of the laid Word to check
   * @return boolean if the file contains a searched word
   */
  public static boolean scan(String searchedWord) {
    File fileOne =
        new File(
            System.getProperty("user.dir")
                + System.getProperty("file.separator")
                + Data.getSelectedDictionary().replace(".", "Parsed."));
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
        if (line.equalsIgnoreCase(searchedWord)) {
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
   * This method is for testing only.
   *
   * @author lengist
   * @param searchedWord the word that needs to be checked if it exists in the dictionary
   * @return found if the word is in the dictionary
   */
  public static boolean scanTest(String searchedWord) {
    File fileOne =
        new File(
            System.getProperty("user.dir")
                + System.getProperty("file.separator")
                + DictionaryParser.getNewFileName());
    boolean found = false;
    try {
      Scanner scanner = new Scanner(fileOne);
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        if (line.equalsIgnoreCase(searchedWord)) {
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
   * Method to return words that contains the letter "letter" and are at most "length" chars long.
   *
   * @author lengist
   * @param letter the letter that needs to be in the wanted word
   * @param length the maximum length the wanted words can be
   * @return a String array containing all the suitable words
   */
  public static ArrayList<String> getWordsIncluding(String letter, int length) {
    ArrayList<String> list = new ArrayList<>();
    File file =
        new File(
            System.getProperty("user.dir")
                + System.getProperty("file.separator")
                + Data.getSelectedDictionary().replace(".", "Parsed."));
    try {
      Scanner scanner = new Scanner(file);
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        if (line.contains(letter) && line.length() <= length) {
          list.add(line);
        }
      }
      scanner.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    String[] suitableWords = new String[list.size()];
    list.toArray(suitableWords);
    return list;
  }
}
