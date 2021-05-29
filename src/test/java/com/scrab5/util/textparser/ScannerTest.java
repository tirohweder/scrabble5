package com.scrab5.util.textparser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.scrab5.util.database.CreateDatabase;
import com.scrab5.util.parser.DictionaryParser;
import com.scrab5.util.parser.DictionaryScanner;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * This class tests the scanning process of a file to search a word. 
 * The method getWordsIncluding is tested relating the AiPlayer.
 * Note: In the methods with access to the database to connection gets established 
 * and disconnect individually for each method. 
 * Because of that every test method needs to do so too.
 * 
 * @author lengist
 */
@Disabled
class ScannerTest {

  /**
   * Tests if the word ZZZ is in the document and XXXX not.
   *
   * @author lengist
   */
  @Test
  void testScan() {
    CreateDatabase cdb = new CreateDatabase();
    DictionaryParser.setCurrentDictionary("Built-In Standard Dictionary.txt");
    DictionaryParser.parseFile(DictionaryParser.getFileName());

    assertEquals(true, DictionaryScanner.scan("ZZZ"));
    assertEquals(false, DictionaryScanner.scan("XXXX"));
    assertEquals(true, DictionaryScanner.scan("before"));
    cdb.deleteDatabaseFile();
  }

}
