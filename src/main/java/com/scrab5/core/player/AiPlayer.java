package com.scrab5.core.player;

import java.util.ArrayList;

public class AiPlayer extends Player {
  
  private ArrayList<String> words;
  private ArrayList<Integer> points;
  private ArrayList<Integer> x;
  private ArrayList<Integer> y;
  
  /**
   * @param name
   * @author hraza
   */
  public AiPlayer(String name) {
    super(name);
  }
  
  /**
   * In this method all other methods will be called
   * @param
   * @author hraza
   */
  public void playerMove() {
    
  }
  
  /**
   * In this method all other methods will be called
   * @param
   * @author hraza
   */
  public void findTileWithLetter() {
    
  }
  
  /**
   * This Method is surching the current Gameboard for valid free Spots
   * @param
   * @author hraza
   */
  public void getSpotsfree() {
    
  }
  
  /**
   * This Method will create all combinations of rack-tiles and  the tile on the Gameboard and will add the valid ones to the wordslist
   * @param fixLetter
   * @param fixPosition
   * @param rack
   * @param maxLength
   * @author hraza
   */
  public void wordGenerator(String fixLetter, int fixPosition, String rack, int maxLength) {
    
  }
  
  /**
   * This Method will calculate the the Points of a word
   * @param word
   * @author hraza
   */
  public void calculatePoints(String word) {
    
  }

}
