package com.scrab5.core.game;

import java.util.ArrayList;

public class EasyAI {
  private char[] rack;
  private int wordLength;
  private String wordBeginning;
  private String wordEnding;
  private int x;
  private int y;
  private ArrayList<String> words = new ArrayList<>();


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
              && GameSession.getGameBoard().isSpotFree(i + counter, j + counter) && counter < 9) {
            counter++;
          }
          if (counter > 1) {
            this.wordLength = counter;
            this.wordBeginning = GameSession.getGameBoard().getPlacedTile(i, j).getLetter();
            this.rack = GameSession.getCurrentPlayer().getRack().toString().toCharArray();
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
  public static String wordGeneratorBeginning(int wordLength, String wordBeginning, char[] rack) {
    ArrayList<String> listOfWords = new ArrayList<>();

    return "";
  }

   public void printAllKLength(char[] set, int k) {
    int n = set.length;
    this.words.add(printAllKLengthRec(set, "", n, k));
  }

   public String printAllKLengthRec(char[] set, String prefix, int n, int k) {

    if (k == 0) {
      System.out.println(prefix);
      return prefix;
    }

    for (int i = 0; i < n; ++i) {
      String newPrefix = prefix + set[i];
      printAllKLengthRec(set, newPrefix, n, k - 1);
    }
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

    System.out.println("First Test");
    char[] set1 = {'a', 'b'};
    int k =2 ;
    EasyAI ai= new EasyAI();
    ai.printAllKLength(set1, k);

    System.out.println("\nSecond Test");
    char[] set2 = {'a', 'b', 'c', 'd'};
    k = 1;
    ai.printAllKLength(set2, k);
  }
}
