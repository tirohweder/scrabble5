package com.scrab5.util.database;

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

  /**
   * Loads a file from the path and passes the lines of the document on to filterWords.
   * 
   * @author lengist
   * @param file with the name of the file for the dictionary
   */
  private static void loadFile(String file) {

    try {
      FileInputStream fileInput = new FileInputStream(
          System.getProperty("user.dir") + System.getProperty("file.separator") + file);
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
    File file = new File(System.getProperty("user.dir") + System.getProperty("file.separator")
        + "separatewords.txt");
    try {
      file.createNewFile();
      BufferedWriter bufWriter = new BufferedWriter(new FileWriter(file));
      bufWriter.write(word);
      bufWriter.newLine();
      bufWriter.close();
    } catch (IOException e1) {
      e1.printStackTrace();
    }
  }

  public static void main(String[] args) {
    loadFile("words.txt");
  }

}
