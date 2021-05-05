package com.scrab5.util.textParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

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
    File fileOne = new File(System.getProperty("user.dir") + System.getProperty("file.separator")
        + "src/main/resources/com/scrab5/util/textParser/" + DictionaryParser.getNewFileName());
    boolean found = false;

    try {
      Scanner scanner = new Scanner(fileOne);
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
    DictionaryParser.setCurrentDictionary("words.txt");
    DictionaryParser.parseFile("words.txt");
    System.out.println(scan("ZZZ"));
  }
}
