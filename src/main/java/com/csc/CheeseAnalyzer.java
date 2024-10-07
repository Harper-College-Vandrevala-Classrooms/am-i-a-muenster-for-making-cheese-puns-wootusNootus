package com.csc;
import java.util.ArrayList;
import java.io.*;

public class CheeseAnalyzer 
{
  public static void main(String[] args){
  }

  public static void readFile(String fileName, ArrayList<String> data){
    try{
      FileReader fr = new FileReader(fileName);
      BufferedReader br = new BufferedReader(fr);
      String line;

      while((line = br.readLine()) != null){
        analyzeLine(formulateLine(line));
      }
      br.close();
    }
    catch(IOException e){
      System.out.println("Error - Cannot read from file" + fileName);
    }
  }

  public static ArrayList<String> formulateLine(String line){
    ArrayList<String> formulated = new ArrayList<>();
    StringBuilder lineBuilder = new StringBuilder(line);
    
    // Removing quotation marked data
    int firstQuoteIndex = lineBuilder.indexOf("\"");
    int secondQuoteIndex = lineBuilder.indexOf("\"", firstQuoteIndex + 1);
        
    if (firstQuoteIndex != -1 && secondQuoteIndex != -1) {
      formulated.add(lineBuilder.substring(firstQuoteIndex, secondQuoteIndex + 1)); // With quotes
  
      // Remove the substring (including the quotes) from the og string
      lineBuilder = new StringBuilder(lineBuilder.substring(0, firstQuoteIndex) + lineBuilder.substring(secondQuoteIndex + 1));
    } 
    else {
      formulated.add("No data found");
    }

    // Filling in no data (,,) values with "NULL"
    for(int i = 0; i < (lineBuilder.length() - 1); i++){
      if (lineBuilder.charAt(i) == ',' && lineBuilder.charAt(i + 1) == ',') {
        lineBuilder.insert((i + 1), "NULL");
      }
    }

    // Splitting by commas
    String[] dataArray = lineBuilder.toString().split(",");
    
    for(String data : dataArray){
      formulated.add(data.trim());
    }

    return formulated;
  }

  public static void analyzeLine(ArrayList<String> data)
  {

  }
}
