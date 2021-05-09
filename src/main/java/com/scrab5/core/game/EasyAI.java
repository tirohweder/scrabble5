package com.scrab5.core.game;

public class EasyAI {
  private String rack;
  private int wordLength;
  private String wordBeginning;
  private String wordEnding;
  private int x;
  private int y;
  

  /**
   * This method will find a tile on the gameboard on which the AI can hang a word onto it and will
   * initialize the wordLength and wordBeginning so the we can Hand these parameters over to the
   * wordgenerator
   *
   * @param
   * @param
   * @author hraza
   */

  public void findTile() {
    for (int i = 0; i < 15; i++) {
      for (int j = 0; j < 15; j++) {
        if (!GameSession.getGameBoard().isSpotFree(i, j) && j < 14) {
          int counter = 1;
          while (GameSession.getGameBoard().isSpotFree(i, j + counter)
              && GameSession.getGameBoard().isSpotFree(i - counter, j + counter)
              && GameSession.getGameBoard().isSpotFree(i + counter, j + counter)) {
            counter++;
          }
          if (counter > 1) {
            this.wordLength = counter;
            this.wordBeginning = GameSession.getGameBoard().getPlacedTile(i, j).getLetter();
            this.rack = GameSession.getCurrentPlayer().getRack().toString();
            wordGeneratorBeginning(this.wordLength, this.wordBeginning, this.rack);
          }
        }
      }
    }
  }

  /**
   * This method will find create a valid word from the dictionary with giving over the information
   * of the wordLength and the wordBeginning and the actual rack
   *
   * @param wordLength
   * @param wordBeginning
   * @param rack
   * @author hraza
   */
  public static String wordGeneratorBeginning(int wordLength, String wordBeginning, String rack) {
    return "";
  }

  /**
   * This method will find create a valid word from the dictionary with giving over the information
   * of the wordLength and the wordEnding
   *
   * @param wordLength
   * @param wordEnding
   * @author hraza
   */
  public String wordGeneratorEnding(int wordLength, String wordBeginning) {
    return "";
  }

  public static void main(String[] args) {
    String[][] s = new String[15][15];
    for (int i = 0; i < 15; i++) {
      for (int j = 0; j < 15; j++) {
        s[i][j] = "HI";
        System.out.println(s[i][j] + " " + i + " " + j);
      }
    }
  }
}
