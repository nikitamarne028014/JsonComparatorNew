package com.psl.jsonComparator;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.Map;

import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class JsonComparator {

	public static String JSON_1_FILE_PATH = "D:\\JsonComparator\\JsonComparator\\JsonFiles\\leftJson.json";
	public static String JSON_2_FILE_PATH = "D:\\JsonComparator\\JsonComparator\\JsonFiles\\rightJson.json";
	public static String RESULT_OUTPUT_FILE_PATH = "D:\\JsonComparator\\JsonComparator\\JsonFiles\\Json_Comparision_Output.txt";
	
	public static void main(String[] args) {
		
		JsonElement json1 = parseFirstJson();
		JsonElement json2 = parseSecondJson();
		
		Gson gson = new Gson();
		Type type = new TypeToken<Map<String, Object>>(){}.getType();
		
		Map<String,Object> leftJsonMap = gson.fromJson(json1, type);
		Map<String,Object> rightJsonMap = gson.fromJson(json2, type);
		
		MapDifference<String,Object> difference = Maps.difference(leftJsonMap,rightJsonMap);
		PrintDifferenceOnConsole(difference);
		PrintDifferenceInFile(difference);
	}
	
	public static void PrintDifferenceOnConsole(MapDifference<String,Object> difference)
	{
		System.out.println("\n======================== //******* MisMatched Objects Are *********** \\ ==========================\n");
		System.out.println("\n******************* Entries only in Left Json******************");
		difference.entriesOnlyOnLeft()
        .forEach((key, value) -> System.out.println(key + ": " + value));
		
		System.out.println("\n******************* Entries only in Right Json******************");
		difference.entriesOnlyOnRight()
        .forEach((key, value) -> System.out.println(key + ": " + value));
		
		System.out.println("\n******************* Entries Missing ******************");
		difference.entriesDiffering()
        .forEach((key, value) -> System.out.println(key + ": " + value));
		
		System.out.println("\n******************* Entries InCommon ******************");
		difference.entriesInCommon()
        .forEach((key, value) -> System.out.println(key + ": " + value));
		
	}
	
	public static void PrintDifferenceInFile(MapDifference<String,Object> difference)
	{
		File resultFile = new File(RESULT_OUTPUT_FILE_PATH);
		try {
			PrintWriter writer = new PrintWriter(resultFile);
			
			writer.println("\n======================== //******* MisMatched Objects Are *********** \\ ==========================\n");
			writer.println();
			writer.println("\n******************* Entries only in Left Json******************");
			difference.entriesOnlyOnLeft()
	        .forEach((key, value) -> writer.println(key + ": " + value));
			
			writer.println("\n******************* Entries only in Right Json******************");
			difference.entriesOnlyOnRight()
	        .forEach((key, value) -> writer.println(key + ": " + value));
			
			writer.println("\n******************* Entries Missing ******************");
			difference.entriesDiffering()
	        .forEach((key, value) -> writer.println(key + ": " + value));
			
			writer.println("\n******************* Entries InCommon ******************");
			difference.entriesInCommon()
	        .forEach((key, value) -> writer.println(key + ": " + value));
			
			writer.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static JsonElement parseFirstJson()
	{
		File json1 = new File(JSON_1_FILE_PATH);
		JsonParser parser = new JsonParser();
		JsonElement element = null;
		
		try {
			element = parser.parse(new FileReader(json1));
		} catch (JsonIOException e) {
			e.printStackTrace();
		} catch (JsonSyntaxException e) {
			System.out.println("Syntax Error in Json Provided");
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found");
			e.printStackTrace();
		}
		return element;
	}

	public static JsonElement parseSecondJson()
	{
		File json2 = new File(JSON_2_FILE_PATH);
		JsonParser parser = new JsonParser();
		JsonElement element = null;
		
		try {
			element = parser.parse(new FileReader(json2));
		} catch (JsonIOException e) {
			e.printStackTrace();
		} catch (JsonSyntaxException e) {
			System.out.println("Syntax Error in Json Provided");
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found");
			e.printStackTrace();
		}
		return element;
	}
}
