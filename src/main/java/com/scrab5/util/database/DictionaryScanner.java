package com.scrab5.util.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DictionaryScanner {

  /**
   * Scans the created file of the dictionary for the word searchedWord and returns if its
   * contained.
   * 
   * @author lengist
   * @param searchedWord the String of the laid Word to check
   * @return boolean of the file contains a searched word
   */
  public static boolean scan(String searchedWord) {
    File file = new File(System.getProperty("user.dir") + System.getProperty("file.separator")
        + DictionaryParser.getFileName());
    boolean found = false;

    try {
      Scanner scanner = new Scanner(file);
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
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

  public static void main(String[] args) {
    DictionaryParser.setFileName("eng.txt");
    DictionaryParser.createSearchableFile("words.txt");
    System.out.println(scan("ZZZ"));
  }
}
