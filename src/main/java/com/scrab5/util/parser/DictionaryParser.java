package com.scrab5.util.parser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * Parses a dictionary and saves it as a new file.
 * 
 * @author lengist
 */
public class DictionaryParser {

  static BufferedWriter bufWriter = null;
  // private static String currentDictionary;
  private static String currentDictionary = "Built-In Standard Dictionary.txt";
  private static String newFileName = null;

  /**
   * Sets the name of the current dictionary based on the new dictionary that needs to be inserted.
   *
   * @author lengist
   * @param dictionary String representing the dictionary file name.
   */
  public static void setCurrentDictionary(String dictionary) {
    currentDictionary = dictionary;
  }

  /**
   * Returns the name of the current inserted and used dictionary file.
   * 
   * @author lengist
   * @return String representation of the current inserted dictionary file name
   */
  public static String getFileName() {
    return currentDictionary;
  }

  /**
   * Returns the name of the new file to scan.
   * 
   * @author lengist
   * @return String representation of the new file name
   */
  public static String getNewFileName() {
    return newFileName;
  }

  /**
   * Parses given file and creates a parsed file while using all methods of this class. Made for
   * initial call.
   *
   * @author lengist
   * @param originalFile String name of the file from the inserted dictionary
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
   * @author lengist
   * @param dictionaryFile The file the user inserts as new dictionary
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
   * @author lengist
   * @param file with the name of the file for the dictionary
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
   * @author lengist
   * @param line A String representation of a line from the document
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
   * @author lengist
   * @param word A String representing the proofed word to insert into the new document
   */
  public static void createDoc(String word) {
    try {
      bufWriter.write(word);
      bufWriter.newLine();
    } catch (IOException e1) {
      e1.printStackTrace();
    }
  }

  /**
   * Returns the file of the current Dictionary.
   * 
   * @author lengist
   * @param dictionaryName the name of the current set dictionary
   * @return the dictionary file to send to all clients
   */
  public static File getDictionaryFile(String dictionaryName) {
    File file = new File(System.getProperty("user.dir") + System.getProperty("file.separator")
        + "src/main/resources/com/scrab5/util/textParser/" + dictionaryName);
    return file;
  }

  /**
   * Saves a new file from another client.
   * 
   * @author lengist
   * @param file the file from the other user
   * @param newFileName the name of the file sent
   */
  public static void insertFile(File file, String newFileName) {
    System.out.println(newFileName);
    File newFile = new File(System.getProperty("user.dir") + System.getProperty("file.separator")
        + "src/main/resources/com/scrab5/util/textParser/" + newFileName);
    String line = null;
    FileInputStream fileInput;
    try {
      fileInput = new FileInputStream(newFile);
      BufferedReader buf =
          new BufferedReader(new InputStreamReader(fileInput, StandardCharsets.UTF_8));
      while ((line = buf.readLine()) != null) {
        bufWriter.write(line);
        bufWriter.newLine();
      }
      buf.close();
      parseFile(newFileName);
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
