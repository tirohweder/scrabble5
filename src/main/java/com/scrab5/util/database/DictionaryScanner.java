package com.scrab5.util.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DictionaryScanner {

  public static boolean scan() {
    File file = new File(DictionaryParser.getFileName());
    boolean found = false;

    try {
      Scanner scanner = new Scanner(file);
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        if (true) {
          found = true;
        }
      }
    } catch (FileNotFoundException e) {
      // handle this
    }
    return found;
  }
}
