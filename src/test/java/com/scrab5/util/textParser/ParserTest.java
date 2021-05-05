package com.scrab5.util.textParser;

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
    // load file
    DictionaryParser.parseFile(DictionaryParser.getFileName());
    File file = new File(System.getProperty("user.dir") + System.getProperty("file.separator")
        + "src/main/resources/com/scrab5/util/textParser/" + DictionaryParser.getNewFileName());
    assertEquals(true, file.exists());
  }

}
