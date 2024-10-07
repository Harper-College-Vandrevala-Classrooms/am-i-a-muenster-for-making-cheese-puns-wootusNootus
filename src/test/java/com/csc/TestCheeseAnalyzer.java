package com.csc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TestCheeseAnalyzer 
{
  public static void main(String[] args) {
    String testFilePath = "cheese_data.csv"; 
    
    try (BufferedReader br = new BufferedReader(new FileReader(testFilePath))) {
      String line = br.readLine();

      for(int i = 0; i < 2; i++){
           line = br.readLine(); 
      }

      ArrayList<String> result = CheeseAnalyzer.formulateLine(line); 
      System.out.println(result); 
    } 
    catch (IOException e) {
        e.printStackTrace();
    }
}
}
