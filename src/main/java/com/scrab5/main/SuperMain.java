package com.scrab5.main;


import com.scrab5.ui.App;

/**
 * We used this SuperMain to have a Main class that doesnt extend Application, this is needed for
 * the Maven Shade Plugin.
 * 
 * @author trohwede
 */
public class SuperMain {
  public static void main(String[] args) {
    App.main(args);
  }
}

