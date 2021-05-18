package com.scrab5.core.game;

import java.io.Serializable;

public class Tile implements Serializable {

  private static final long serialVersionUID = 1L;


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
   * @param letter String of the Tile
   * @param value  Amount of points for the Tile
   * @author trohwede
   */
  public Tile(String letter, int value) {
    this.letter = letter;
    this.value = value;
  }

  /**
   * Getter for String of Tile
   *
   * @return letter String of the Tile
   * @author trohwede
   */
  public String getLetter() {
    return letter;
  }

  /**
   * Sets the Letter of a Tile
   *
   * @param letter String of the TIle
   * @author trohwede
   */
  public void setLetter(String letter) {
    this.letter = letter;
  }

  /**
   * Getter for Value of Tile
   *
   * @return value Amount of points for the letter
   * @author trohwede
   */
  public int getValue() {
    return value;
  }

  /**
   * Setter for Value of Tile
   *
   * @param value Amount of points for the letter
   * @author trohwede
   */
  public void setValue(int value) {
    this.value = value;
  }
}
