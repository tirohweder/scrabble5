package com.scrab5.core.game;

import java.io.Serializable;

/**
 * Tile represents a single tile in the game.
 *
 * @author trohwede
 */
public class Tile implements Serializable {

  private static final long serialVersionUID = 1L;
  private String letter;
  private int value;
  private int column;
  private int row;
  private Integer rackPlace;

  /**
   * Constructor for a tile, where you only know tile letter and value.
   *
   * @author trohwede
   * @param letter String of the Tile
   * @param value Amount of points for the Tile
   */
  public Tile(String letter, int value) {
    this.letter = letter;
    this.value = value;
  }

  /**
   * Constructor for a tile, where you already know the coordinates.
   *
   * @author trohwede
   * @param letter String of the Tile
   * @param value Amount of points for the Tile
   * @param row coordinates of Tile
   * @param column coordinates of Tile
   */
  public Tile(String letter, int value, int row, int column) {
    this.letter = letter;
    this.value = value;
    this.row = row;
    this.column = column;
  }

  /**
   * Return where a tile is stored on the rack.
   *
   * @author trohwede
   * @return place where the tile is stored on the rack
   */
  public Integer getRackPlace() {
    return rackPlace;
  }

  /**
   * Sets where a tile is stored on the rack.
   *
   * @author trohwede
   * @param rackPlace place where the tile is stored on the rack
   */
  public void setRackPlace(Integer rackPlace) {
    this.rackPlace = rackPlace;
  }

  /**
   * Getter for the tiles column coordinates.
   *
   * @author trohwede
   * @return column of the letter
   */
  public int getColumn() {
    return column;
  }

  /**
   * Sets the column of the letter.
   *
   * @author trohwede
   * @param column column coordinates of the letter
   */
  public void setColumn(int column) {
    this.column = column;
  }

  /**
   * Getter for the tiles column coordinates.
   *
   * @author trohwede
   * @return row of the letter
   */
  public int getRow() {
    return row;
  }

  /**
   * Sets the row of the tile.
   *
   * @author trohwede
   * @param row row coordinates of the letter
   */
  public void setRow(int row) {
    this.row = row;
  }

  /**
   * Returns if the letter is equal to null.
   *
   * @author trohwede
   * @return if the letter is null
   */
  public boolean isNull() {
    return (this.letter == null);
  }

  /**
   * Getter for String of Tile.
   *
   * @author trohwede
   * @return letter String of the Tile
   */
  public String getLetter() {
    return letter;
  }

  /**
   * Sets the Letter of a Tile.
   *
   * @author trohwede
   * @param letter String of the TIle
   */
  public void setLetter(String letter) {
    this.letter = letter;
  }

  /**
   * Getter for Value of Tile.
   *
   * @author trohwede
   * @return value Amount of points for the letter
   */
  public int getValue() {
    return value;
  }

  /**
   * Setter for Value of Tile.
   *
   * @author trohwede
   * @param value Amount of points for the letter
   */
  public void setValue(int value) {
    this.value = value;
  }
}
