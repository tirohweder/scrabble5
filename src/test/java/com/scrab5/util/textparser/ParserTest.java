package com.scrab5.util.textparser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.scrab5.util.parser.DictionaryParser;
import java.io.File;
import org.junit.jupiter.api.Test;

/**
 * filter words
 * This class tests the parsing of a dictionary file into a new file to be scanned. The method to filter for words is tested in ScannerTest.java.
 * 
 * @author lengist
 */
class ParserTest {

  /**
   * Tests the creation of the new filtered file. Test for F.5 "users should be able to import
   * different dictionaries".
   *
   * @author lengist
   */
  @Test
  void testCreateSearchableFile() {
    DictionaryParser.setCurrentDictionary("Built-In Standard Dictionary.txt");
    DictionaryParser.parseFile(DictionaryParser.getFileName());
    File file = new File(System.getProperty("user.dir") + System.getProperty("file.separator")
        + "src/main/resources/com/scrab5/util/textParser/" + DictionaryParser.getNewFileName());
    assertEquals(true, file.exists());
  }

}
