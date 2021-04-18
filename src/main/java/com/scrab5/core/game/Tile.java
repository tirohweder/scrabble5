package com.scrab5.core.game;

public class Tile {

  private String letter;
  private int value;

  /**
   * @return
   * @author trohwede
   */
  public int getColumn() {
    return column;
  }

  /**
   * @param column
   * @author trohwede
   */
  public void setColumn(int column) {
    this.column = column;
  }

  /**
   * @return
   * @author trohwede
   */
  public int getRow() {
    return row;
  }

  /**
   * @param row
   * @author trohwede
   */
  public void setRow(int row) {
    this.row = row;
  }

  private int column;
  private int row;


  /**
   * @param letter
   * @param value
   * @author trohwede
   */
  public Tile(String letter, int value) {
    this.letter = letter;
    this.value = value;
  }

  /**
   * @return
   * @author trohwede
   */
  public String getLetter() {
    return letter;
  }

  /**
   * @param letter
   * @author trohwede
   */
  public void setLetter(String letter) {
    this.letter = letter;
  }

  /**
   * @return
   * @author trohwede
   */
  public int getValue() {
    return value;
  }

  /**
   * @param value
   * @author trohwede
   */
  public void setValue(int value) {
    this.value = value;
  }
}
