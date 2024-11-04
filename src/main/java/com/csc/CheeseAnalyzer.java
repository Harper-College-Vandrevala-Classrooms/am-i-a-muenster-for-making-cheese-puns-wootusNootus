package com.csc;
import java.util.ArrayList;
import java.io.*;

public class CheeseAnalyzer 
{
  // Criteria
  public static int countPasteurized;
  public static int countRaw;
  public static int organicAndMoist;
  public static double totalMoisture;
  public static int moistureCount;
  // Most cheesed animal
  public static int goat;
  public static int cow;
  public static int ewe;
  public static int buffalo;
  

  public static void main(String[] args)
  {
    System.out.println("Ready to analyze data from Cheese file");

    readFile("cheese_data.csv");
    writeFile("output.txt");
    removeHeadersAndIds("cheese_data.csv", "cheese_without_headers_and_ids.csv");

    System.out.println("Thank for using the Cheese Analyzer Program");
  }

  public static void writeFile(String filename){
    try{
      FileWriter fw = new FileWriter(filename);
      PrintWriter pw = new PrintWriter(fw);
      
      pw.println("Number of cheeses that use pasteurized milk: " + countPasteurized + " cheeses");
      pw.println("Number of cheeses that use raw milk: " + countRaw + " cheeses");
      pw.println("Number of cheeses that are organic and have a moisture percentage greater than 41.0%: " + organicAndMoist + " cheeses");

      double averageMoisture = totalMoisture / moistureCount;
      pw.println("Average moisture percentage of all cheeses: " + (Math.round(averageMoisture * 100.0) / 100.0));

      int winner = cow;
      if(goat > winner) {
        pw.println("Most cheeses come from a goat.");
        winner = goat;
      }
      else if (ewe > winner) {
        pw.println("Most cheeses come from a ewe.");
        winner = ewe;
      }
      else if (buffalo > winner){
        pw.println("Most cheeses come from a buffalo.");
      }
      else {
        pw.println("Most cheeses come from a cow.");
      }

      System.out.println("Succesfully analyzed data to new file " + filename);
      pw.close();
    }
    catch(IOException e){
      System.out.println("Error - Cannot write to file " + filename);
    }

  }

  public static void readFile(String fileName){
    try{
      FileReader fr = new FileReader(fileName);
      BufferedReader br = new BufferedReader(fr);
      String line;

      line = br.readLine(); // Skipping the header during analysis

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
    // Fixing null values for analysis
    if( data.get(7).equals("NULL") ) { data.set(7, "-1"); }
    if( data.get(4).equals("NULL") ) { data.set(4, "0.0"); }

    // Pasteurized vs raw
    if( data.get(10).equalsIgnoreCase("Raw Milk") ) { countRaw++; }
    if( data.get(10).equalsIgnoreCase("Pasteurized") ) { countPasteurized++; }

    // Organic + moisture > 41.0% (.matches function fixes Number Format Exception Error)
    if( (data.get(7).matches("-?\\d+") && Integer.parseInt(data.get(7)) == 1) && ((Double.parseDouble(data.get(4))) > 41.0) ) { organicAndMoist++; }

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

    // Calculate total moisture for averaging
    if (!data.get(4).equals("0.0")) {
      totalMoisture += Double.parseDouble(data.get(4));
      moistureCount++;
    }
  }

  public static void removeHeadersAndIds(String inputFile, String outputFile) {
    try {
      // Setting up read file
      FileReader fr = new FileReader(inputFile);
      BufferedReader br = new BufferedReader(fr);

      // Setting up write file
      FileWriter fw = new FileWriter(outputFile);
      PrintWriter pw = new PrintWriter(fw);

      String line;
      boolean isHeader = true;

      while ((line = br.readLine()) != null) {
        // Skipping the first line
        if (isHeader) {
          isHeader = false; // Anything after first line will not be header
        } 
        else {
          // Removing the first column
          String[] splitLine = line.split(",", 2);
          if (splitLine.length > 1) {
            pw.println(splitLine[1].trim()); 
          }
        }
      }

      System.out.println("Successfully created file without headers and IDs: " + outputFile);
      br.close();
      pw.close();
    } 
    catch (IOException e) {
      System.out.println("Error - Cannot create file " + outputFile);
    }
  }
}
