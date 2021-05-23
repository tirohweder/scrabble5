package com.scrab5.util.textParser;

import com.scrab5.util.database.CreateDatabase;
import com.scrab5.util.database.Database;
import com.scrab5.util.database.FillDatabase;
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
   * contained.
   *
   * @author lengist
   * @param searchedWord the String of the laid Word to check
   * @return boolean of the file contains a searched word
   */

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
    ArrayList<String> list = new ArrayList<String>();
    File file = new File(System.getProperty("user.dir") + System.getProperty("file.separator")
        + "src/main/resources/com/scrab5/util/textParser/" 
        + "Built-In Standard DictionaryParsed.txt");
    //needs to be changed back to DictionaryParser.getNewFileName(); 
    try {
      Scanner scanner = new Scanner(file);
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        if (line.contains(letter) && line.length() <= length) {
          list.add(line);
        }
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    String[] suitableWords = new String[list.size()];
    suitableWords = list.toArray(suitableWords);
    
    return list;
  }
  
  /**
   * Returns a new array that contains the words from the parameter array which include letter.
   * 
   * @author lengist
   * @param words a ArrayList containing all words to check
   * @param letter a String of the letter that should be in the array that gets returned
   * @return a ArrayList containing all words containing the letter
   */
  public static ArrayList<String> getWordsIncludingFrom(ArrayList<String> words, String letter) {
    ArrayList<String> checked = new ArrayList<String>();
    for (String line : words) {
      if (line.contains(letter)) {
        checked.add(line);
      }
    }
    return checked;
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
    // System.out.println(scan("JOK*R"));
    System.out.println(scan("ZZZ"));
    System.out.println();
    
    System.out.println("before");
    System.out.println();
    //String[] test = getWordsIncluding("A", 3);
    /*for(int i = 0; i < test.length; i++) {
      System.out.println(test[i]);
    }
    System.out.println("after");*/
  }
}
