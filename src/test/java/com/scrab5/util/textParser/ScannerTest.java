package com.scrab5.util.textParser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.scrab5.util.database.DictionaryParser;
import com.scrab5.util.database.DictionaryScanner;
import org.junit.jupiter.api.Assertions;
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

    Assertions.assertEquals(true, DictionaryScanner.scan("ZZZ"));
    assertEquals(false, DictionaryScanner.scan("XXXX"));
  }

}
