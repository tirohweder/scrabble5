package com.scrab5.network;

/**
 * Class ClientThread and ServerThread extends. Both implement the same structure with the attribute
 * running.
 *
 * @author nitterhe
 */
public abstract class Threads extends Thread {

  protected boolean running;

  /**
   * Mainly for JUnit Tests. Stops the thread.
   *
   * @author nitterhe
   */
  public void stopThread() {
    this.running = false;
  }
}
