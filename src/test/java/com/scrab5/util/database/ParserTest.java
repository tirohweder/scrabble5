package com.scrab5.util.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.File;
import org.junit.jupiter.api.Test;

class ParserTest {

  /**
   * Tests the creation of the new filtered file
   * 
   * @author lengist
   */
  @Test
  void testCreateSearchableFile() {
    DictionaryParser.setFileName("testParse.txt");
    DictionaryParser.createSearchableFile("words.txt");
    File file = new File(
        System.getProperty("user.dir") + System.getProperty("file.separator") + "test.txt");
    assertEquals(file.exists(), true);
  }

}
