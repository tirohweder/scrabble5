package com.scrab5.core.game;

import java.io.Serializable;

/**
 * Tile represents a single tile in the game.
 *
 * @author trohwede
 */
public class Tile implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Return where a tile is stored on the rack.
   *
   * @return place where the tile is stored on the rack
   * @author trohwede
   */
  public Integer getRackPlace() {
    return rackPlace;
  }

  /**
   * Sets where a tile is stored on the rack.
   *
   * @param rackPlace place where the tile is stored on the rack
   * @author trohwede
   */
  public void setRackPlace(Integer rackPlace) {
    this.rackPlace = rackPlace;
  }

  /**
   * Getter for the tiles  column coordinates.
   *
   * @return column of the letter
   * @author trohwede
   */
  public int getColumn() {
    return column;
  }

  /**
   * Sets the column of the letter.
   *
   * @param column column coordinates of the letter
   * @author trohwede
   */
  public void setColumn(int column) {
    this.column = column;
  }

  /**
   * Getter for the tiles  column coordinates.
   *
   * @return row of the letter
   * @author trohwede
   */
  public int getRow() {
    return row;
  }

  /**
   * Sets the row of the tile.
   *
   * @param row row coordinates of the letter
   * @author trohwede
   */
  public void setRow(int row) {
    this.row = row;
  }


  /**
   * Returns if the letter is equal to null.
   *
   * @return if the letter is null
   * @author trohwede
   */
  public boolean isNull() {
    return (this.letter == null);
  }

  private String letter;
  private int value;
  private int column;
  private int row;
  private Integer rackPlace;


  /**
   * Constructor for a tile, where you only know tile letter and value.
   *
   * @param letter String of the Tile
   * @param value  Amount of points for the Tile
   * @author trohwede
   */
  public Tile(String letter, int value) {
    this.letter = letter;
    this.value = value;
  }


  /**
   * Constructor for a tile, where you already know the coordinates.
   *
   * @param letter String of the Tile
   * @param value  Amount of points for the Tile
   * @param row    coordinates of Tile
   * @param column coordinates of Tile
   * @author trohwede
   */
  public Tile(String letter, int value, int row, int column) {
    this.letter = letter;
    this.value = value;
    this.row = row;
    this.column = column;
  }


  /**
   * Getter for String of Tile.
   *
   * @return letter String of the Tile
   * @author trohwede
   */
  public String getLetter() {
    return letter;
  }

  /**
   * Sets the Letter of a Tile.
   *
   * @param letter String of the TIle
   * @author trohwede
   */
  public void setLetter(String letter) {
    this.letter = letter;
  }

  /**
   * Getter for Value of Tile.
   *
   * @return value Amount of points for the letter
   * @author trohwede
   */
  public int getValue() {
    return value;
  }

  /**
   * Setter for Value of Tile.
   *
   * @param value Amount of points for the letter
   * @author trohwede
   */
  public void setValue(int value) {
    this.value = value;
  }
}
