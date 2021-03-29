// @author nitterhe

package com.scrab5.network;

public class Threads extends Thread {

  protected boolean running;

  // stops Thread until this.wake() is executed
  public synchronized void sleep() {
    this.running = false;
    try {
      this.wait();
    } catch (Exception e) {
      // requires Exception handling
    }
  }

  // wakes the Thread if it sleeping
  public synchronized void wake() {
    if (!running) {
      try {
        this.running = true;
        this.notify();
      } catch (Exception e) {
        // requires Exception handling
      }
    }
  }
}
