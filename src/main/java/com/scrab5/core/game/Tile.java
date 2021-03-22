package com.scrab5.core.game;

public class Tile {
  private char letter; // a-z

  private boolean joker;

  // Constructor

  public Tile() {}

  public Tile(char letter, boolean joker) {
    this.letter = letter;

    this.joker = joker;
  }

  // Methods

  public char getLetter() {
    return letter;
  }

  public void setLetter(char letter) {
    this.letter = letter;
  }

  public boolean isJoker() {
    return joker;
  }

  public void setJoker(boolean joker) {
    this.joker = joker;
  }
}
