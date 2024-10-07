package com.csc;
import java.util.ArrayList;
import java.io.*;

public class CheeseAnalyzer 
{
  // Criteria
  public static int countPasteurized;
  public static int countRaw;
  public static int organicAndMoist;
  // Most cheesed animal
  public static int goat;
  public static int cow;
  public static int ewe;
  public static int buffalo;

  public static void main(String[] args)
  {

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
    // Pasteurized vs raw
    if( Character.toLowerCase(data.get(10).charAt(0)) == 'r' ) { countRaw++; }
    if( Character.toLowerCase(data.get(10).charAt(0)) == 'p' ) { countPasteurized++; }

    // Organic + moisture > 41.0%
    if( (data.get(7) == "1") && (Double.parseDouble(data.get(4))) > 41.0) { organicAndMoist++; }

    // Type of animal milk
    String animal = data.get(9).toLowerCase();

    switch (animal) {
      case "goat":
          goat++;
          break;
      case "cow":
          cow++;
          break;
      case "ewe":
          ewe++;
          break;
      case "buffalo":
          buffalo++;
          break;
      default:
          break;
    }
  }
}
