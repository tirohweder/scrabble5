package com.scrab5.util.textparser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.scrab5.util.database.CreateDatabase;
import com.scrab5.util.parser.DictionaryParser;
import com.scrab5.util.parser.DictionaryScanner;
import java.io.File;
import org.junit.jupiter.api.Test;

/**
 * This class tests the scanning process of a file to search a word. The method getWordsIncluding is
 * tested relating the AiPlayer. Note: In the methods with access to the database to connection gets
 * established and disconnect individually for each method. Because of that every test method needs
 * to do so too.
 * 
 * @author lengist
 */
class ScannerTest {

  /**
   * Tests if the word ZZZ is in the document and XXXX not.
   *
   * @author lengist
   */
  @Test
  void testScan() {
    DictionaryParser.setCurrentDictionary("Built-In Standard Dictionary.txt");
    DictionaryParser.parseFile(DictionaryParser.getFileName());

    assertEquals(true, DictionaryScanner.scanTest("ZZZ"));
    assertEquals(false, DictionaryScanner.scanTest("XXXX"));
    assertEquals(true, DictionaryScanner.scanTest("before"));
    File file = new File(System.getProperty("user.dir") + System.getProperty("file.separator")
        + DictionaryParser.getNewFileName());
    file.delete();
  }

}
