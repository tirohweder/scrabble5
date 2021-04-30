package com.scrab5.util.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class ScannerTest {

  /**
   * Tests if the word ZZZ is in the document
   *
   * @author lengist
   */
  @Test
  void testScan() {
    DictionaryParser.setFileName("testScan.txt");
    DictionaryParser.createSearchableFile("words.txt");

    assertEquals(true, DictionaryScanner.scan("ZZZ"));
    assertEquals(false, DictionaryScanner.scan("XXXX"));
  }

}
