package com.scrab5.util.database;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TxtReader {

    private static void loadFile(String fileName) {

        File file = new File(fileName);

        if (!file.canRead() || !file.isFile())
            System.exit(0);

            BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(fileName));
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println("read line: " + line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (IOException e) {
                }
        }
    }

    public static void main(String[] args) {
        String filName = "test.txt";
        loadFile(filName);
    }
}