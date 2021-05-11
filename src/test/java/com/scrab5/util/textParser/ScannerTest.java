package com.scrab5.util.textParser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import com.scrab5.util.database.CreateDatabase;

class ScannerTest {

  /**
   * Tests if the word ZZZ is in the document and XXXX not.
   *
   * @author lengist
   */
  @Test
  void testScan() {
    CreateDatabase cdb = new CreateDatabase();
    DictionaryParser.setCurrentDictionary("words.txt");

    // assertEquals(true, DictionaryScanner.scan("ZZZ"));
    assertEquals(false, DictionaryScanner.scan("XXXX"));
    cdb.disconnect();
  }

}
