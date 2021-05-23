package com.scrab5.util.textParser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class DictionaryParser {

  static BufferedWriter bufWriter = null;
  // private static String currentDictionary;
  private static String currentDictionary = "Built-In Standard Dictionary.txt";
  private static String newFileName = null;

  /**
   * Sets the name of the current dictionary based on the new dictionary that needs to be inserted.
   *
   * @param dictionary String representing the dictionary file name.
   * @author lengist
   */
  public static void setCurrentDictionary(String dictionary) {
    currentDictionary = dictionary;
  }

  /**
   * Returns the name of the current inserted and used dictionary file.
   *
   * @return String representation of the current inserted dictionary file name
   * @author lengist
   */
  public static String getFileName() {
    return currentDictionary;
  }

  /**
   * Returns the name of the new file to scan.
   *
   * @return String representation of the new file name
   * @author lengist
   */
  public static String getNewFileName() {
    return newFileName;
  }

  /**
   * Parses given file and creates a parsed file while using all methods of this class. Made for
   * initial call.
   *
   * @param originalFile String name of the file from the inserted dictionary
   * @author lengist
   */
  public static void parseFile(String originalFile) {
    StringBuilder sb = new StringBuilder(originalFile);
    sb.setLength(sb.length() - 4);
    newFileName = sb.toString() + "Parsed.txt";
    System.out.println(newFileName);
    createSearchableFile(originalFile);
  }

  /**
   * Creates a new file with all the words that can be scanned now.
   *
   * @param dictionaryFile The file the user inserts as new dictionary
   * @author lengist
   */
  public static void createSearchableFile(String dictionaryFile) {

    File file = new File(System.getProperty("user.dir") + System.getProperty("file.separator")
        + "src/main/resources/com/scrab5/util/textParser/" + newFileName);

    /*
     * File file = new File( System.getProperty("user.dir") + System.getProperty("file.separator") +
     * newFileName);
     */

    try {
      if (!file.exists()) {
        file.createNewFile();
      } else {
        System.out.println("File already exists!");
      }
      bufWriter = new BufferedWriter(new FileWriter(file));
    } catch (IOException e) {
      e.printStackTrace();
    }
    loadFile(dictionaryFile);
    try {
      bufWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Loads a file from the path and passes the lines of the document on to filterWords.
   *
   * @param file with the name of the file for the dictionary
   * @author lengist
   */
  private static void loadFile(String file) {
    try {
      /*
       * File fileOne = new File(System.getProperty("user.dir") +
       * System.getProperty("file.separator") + "src/main/resources/com/scrab5/util/textParser/" +
       * "words.txt");
       */
      File fileOne =
          new File(System.getProperty("user.dir") + System.getProperty("file.separator") + file);
      FileInputStream fileInput = new FileInputStream(fileOne);

      BufferedReader buf =
          new BufferedReader(new InputStreamReader(fileInput, StandardCharsets.UTF_8));
      String line;
      while ((line = buf.readLine()) != null) {
        filterWords(line);

      }
      buf.close();
    } catch (FileNotFoundException e1) {
      e1.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // Check: file.createNewFile()

  /**
   * Filters the words with help of a regular expression and passes them to createDoc.
   *
   * @param line A String representation of a line from the document
   * @author lengist
   */
  private static void filterWords(String line) {
    /* regex: only words with at least two letters */
    String regex = "[A-Za-z]{2,}";
    String[] word = line.split("\\W+");
    String[] toCorrect = {"Ä", "ä", "Ö", "ö", "Ü", "ü", "ß"};
    String[] correct = {"Ae", "ae", "Oe", "oe", "Ue", "ue", "ss"};
    for (int i = 0; i < word.length; i++) {
      for (int j = 0; j < toCorrect.length; j++) {
        word[i] = word[i].replaceAll(toCorrect[j], correct[j]);
      }
      if (word[i].matches(regex)) {
        createDoc(word[i]);
      }
    }
  }

  /**
   * Creates a document with the filtered and separated words.
   *
   * @param word A String representing the proofed word to insert into the new document
   * @author lengist
   */
  public static void createDoc(String word) {
    try {
      bufWriter.write(word);
      bufWriter.newLine();
    } catch (IOException e1) {
      e1.printStackTrace();
    }
  }

  /*public static void main(String[] args) {
    parseFile("Built-In Standard Dictionary.txt");
  }*/

}


