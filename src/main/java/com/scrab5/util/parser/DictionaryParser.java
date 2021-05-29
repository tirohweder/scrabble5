package com.scrab5.util.parser;

import com.scrab5.network.messages.DictionaryMessage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * Parses a dictionary and saves it as a new file. Creates a file to send via network and saves a
 * file received from another user via the network.
 *
 * @author lengist
 */
public class DictionaryParser {

  static BufferedWriter bufWriter = null;
  private static String currentDictionary;
  private static String newFileName;
  private static String dictionary;

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
   * @return currentDictionary String representation of the current inserted dictionary file name
   */
  public static String getFileName() {
    return currentDictionary;
  }

  /**
   * Returns the name of the new file to scan.
   *
   * @author lengist
   * @return newFileName String representation of the new file name
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
    newFileName = sb + "Parsed.txt";
    createSearchableFile(originalFile);
  }

  /**
   * Creates a new file with all the words that can be scanned now.
   *
   * @author lengist
   * @param dictionaryFile The file the user inserts as new dictionary
   */
  public static boolean createSearchableFile(String dictionaryFile) {
    File file =
        new File(
            System.getProperty("user.dir") + System.getProperty("file.separator") + newFileName);

    try {
      if (!doesAlreadyExist(file)) {
        file.createNewFile();
      } else {
        return false;
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
    return true;
  }

  /**
   * Loads a file from the path and passes the lines of the document on to filterWords.
   *
   * @author lengist
   * @param file a String with the name of the file for the dictionary
   */
  private static void loadFile(String file) {
    try {
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
    } catch (IOException e1) {
      e1.printStackTrace();
    }
  }

  /**
   * Filters the words with help of a regular expression and passes them to createDoc.
   *
   * @author lengist
   * @param line A String representation of a line from the document
   */
  private static void filterWords(String line) {
    /* regex: only words with at least two letters */
    String regex = "[A-Za-z]{2,}";
    String[] word = line.split("\\s");
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
   * Checks if there already is a same file in the folder. This method is needed to prevent from
   * duplicates.
   *
   * @author lengist
   * @param file2 the file that should be created
   * @return a boolean value whether this exact file already exists in the folder or not
   */
  public static boolean doesAlreadyExist(File file2) {
    File help = new File(System.getProperty("user.dir") + System.getProperty("file.separator"));
    File[] savedFiles = help.listFiles();
    String line1;
    String line2;

    assert savedFiles != null;
    for (File file1 : savedFiles) {
      if (file1.getName().equals(file2.getName())) {
        BufferedReader buf1;
        BufferedReader buf2;
        try {
          buf1 = new BufferedReader(new FileReader(file1));
          buf2 = new BufferedReader(new FileReader(file2));
          while (((line1 = buf1.readLine()) != null) && ((line2 = buf2.readLine()) != null)) {
            if (!line1.contentEquals(line2)) {
              buf1.close();
              buf2.close();
              return false;
            }
          }
          buf1.close();
          buf2.close();
          return true;
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return false;
  }

  /**
   * Saves a new dictionary as a file with given name and content. Dictionary is a single string but
   * this works fine for the size of dictionaries.
   *
   * @author nitterhe
   * @param dictionary - the dictionary as a String
   * @param dictionaryName - the name that the dictionary should have
   * @see DictionaryMessage
   */
  public static void addDictionary(String dictionary, String dictionaryName) {
    Thread t =
        new Thread(
            () -> {
              File file =
                  new File(
                      System.getProperty("user.dir")
                          + System.getProperty("file.separator")
                          + dictionaryName.replace(".", "Parsed."));
              try {
                if (file.createNewFile()) {
                  bufWriter = new BufferedWriter(new FileWriter(file));
                  filterWords(dictionary);
                }
              } catch (Exception e) {
                e.printStackTrace();
              }
            });
    t.start();
  }

  /**
   * Transforms a dictionary file to a String.
   *
   * @author nitterhe
   * @param dictionaryName - the name of the dictionary to be transformed
   * @return dictionary - the dictionary as a String
   */
  public static String getDictionary(String dictionaryName) {
    File file =
        new File(
            System.getProperty("user.dir") + System.getProperty("file.separator") + dictionaryName);
    try {
      FileInputStream fileInput = new FileInputStream(file);
      String line;
      BufferedReader buf =
          new BufferedReader(new InputStreamReader(fileInput, StandardCharsets.UTF_8));
      StringBuilder help = new StringBuilder();
      int count = 0;
      while ((line = buf.readLine()) != null) {
        count++;
        help.append("\n").append(line);
        if (count == 100) {
          dictionary = dictionary + help;
          help = new StringBuilder();
          count = 0;
        }
      }
      buf.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return dictionary;
  }
}
