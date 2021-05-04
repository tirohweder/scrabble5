package com.scrab5.util.textParser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class DictionaryParser {
  static BufferedWriter bufWriter = null;
  static String newFileName = null;

  /**
   * Sets the name of the new file dependent on the new dictionary that needs to be inserted.
   * 
   * @author lengist
   * @param name String representing the new file name. For example name = english, if a english
   *        dictionary is inserted.
   */
  public static void setFileName(String name) {
    newFileName = name;
  }

  /**
   * Returns the name of the new file to scan.
   * 
   * @author lengist
   * @return String representation of the new file name
   */
  public static String getFileName() {
    return newFileName;
  }

  /**
   * Parses given file and creates a parsed file while using all methods of this class. Made for
   * initial call.
   * 
   * @author lengist
   * @param originalFile String name of the file from the inserted dictionary
   */
  public static void parseFiles(String originalFile) {
    StringBuilder sb = new StringBuilder(originalFile);
    sb.setLength(sb.length() - 4);
    newFileName = sb.toString() + "Parsed.txt";
    createSearchableFile(originalFile);
  }

  /**
   * Creates a new file with all the words that can be scanned now.
   * 
   * @author lengist
   * @param DictionaryFile The file the user inserts as new dictionary
   */
  public static void createSearchableFile(String DictionaryFile) {
    File file = new File(System.getProperty("user.dir") + System.getProperty("file.separator")
        + "src/main/resources/com/scrab5/util/textParser/" + newFileName);

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
    loadFile(DictionaryFile);
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
      File fileOne = new File(System.getProperty("user.dir") + System.getProperty("file.separator")
          + "src/main/resources/com/scrab5/util/textParser/" + file);
      FileInputStream fileInput = new FileInputStream(fileOne);

      BufferedReader buf =
          new BufferedReader(new InputStreamReader(fileInput, StandardCharsets.UTF_8));
      String line;
      while ((line = buf.readLine()) != null) {
        filterWords(line);
      }
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
    /* TO DO: edit regex for ae etc. */
    String regex = "[A-Za-z]+";
    String[] word = line.split("\\W+");
    for (int i = 0; i < word.length; i++) {
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

  public static void main(String[] args) {
    parseFiles("words.txt");
    // setFileName("english.txt");
    // createSearchableFile("words.txt");
  }

}


