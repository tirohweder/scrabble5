package com.scrab5.core.game;

public class Tile {


  public Integer getRackPlace() {
    return rackPlace;
  }

  public void setRackPlace(Integer rackPlace) {
    this.rackPlace = rackPlace;
  }

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


  public boolean isNull() {
    return (this.letter == null);
  }

  private String letter;
  private int value;
  private int column;
  private int row;
  private Integer rackPlace;


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
