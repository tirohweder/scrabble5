package com.scrab5.util.textParser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ScannerTest {

  /**
   * Tests if the word ZZZ is in the document and XXXX not.
   *
   * @author lengist
   */
  @Test
  void testScan() {
    DictionaryParser.setCurrentDictionary("words.txt");

    Assertions.assertEquals(true, DictionaryScanner.scan("ZZZ"));
    assertEquals(false, DictionaryScanner.scan("XXXX"));
  }

}
