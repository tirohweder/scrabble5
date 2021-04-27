/**
 * Class ClientThread and ServerThread extends. Both implement the same structure with the attribute
 * running.
 * 
 * @author nitterhe
 */
package com.scrab5.network;

public abstract class Threads extends Thread {

  protected boolean running;

  /**
   * Mostly for JUnit Tests. Stops the thread.
   * 
   * @author nitterhe
   */
  public void stopThread() {
    this.running = false;
  }
  /*
   * // stops Thread until this.wake() is executed public synchronized void sleep() { try {
   * this.wait(); } catch (Exception e) { // requires Exception handling } }
   * 
   * // wakes the Thread if it is sleeping public synchronized void wake() { try { this.notify(); }
   * catch (Exception e) { // requires Exception handling } }
   */
}
