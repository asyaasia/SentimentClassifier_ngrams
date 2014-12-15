/*
 * Authors: Rahul Goswami
 * 			Jatin Mistry
 */
package com.dm.sentiment.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DMUtil {
	public static List<String> tokenize(String filePath,String regEx){
		 List<String> tokenList=new ArrayList<String>();
		 String text=getText(filePath);
		 
		 /*		try{
				File rawFile=new File(filePath);
				BufferedReader reader=new BufferedReader(new FileReader(rawFile));
				String line=null;
				while((line=reader.readLine())!=null){
					//Split the line into words with delimiters as periods, commas and spaces
					String[] lineTokens=line.split(regEx);
					for(String token:lineTokens){
						tokenList.add(token);
					}
				}
				reader.close();
			}
			catch(IOException ex){
				ex.printStackTrace();
			}
*/
		 String[] lineTokens = text.split(regEx);
		 for(String token:lineTokens){
				tokenList.add(token);
			}
	//	 System.out.println(tokenList.toString());
			return tokenList;
	
	}
	
	public static Map<String, Double> getAdjectiveMap(Map<String, Double> dicSentiWordnet)
	{
		Map<String, Double> dictAdjectiveWords = null;
		dictAdjectiveWords = new HashMap<String, Double>();
		
		for (String key : dicSentiWordnet.keySet()) 
		{
			if(key.contains("#a"))
			{
				String sAdjKey = key.split("#a")[0].trim();
				
				dictAdjectiveWords.put(sAdjKey, dicSentiWordnet.get(key));
			}
		}
		
		return dictAdjectiveWords;
	}

	public static List<String> sanitizeList(List<String> tokenList) {
		// TODO Auto-generated method stub
		List<String> sanitizedList=new ArrayList<String>();
		for(String token:tokenList){
			if(!token.trim().equals(""))
				sanitizedList.add(token.trim());
		}
		return sanitizedList;
	}
	public static String getText(String fileName) {
		String text="";
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			String line;
			//text = "";
			while ((line = reader.readLine()) != null) {
                text = text + " " + line;
            }
			// System.out.println("===== Loaded text data: " + fileName + " =====");
			reader.close();
		
		}
		catch (IOException ex) {
			ex.printStackTrace();
			System.out.println("Eror reading file: " + fileName);
		}
		return text;
	}
	
	public static void writeData(String fileName, String data){
		try{
			File statistics=new File(fileName);
			BufferedWriter dataWriter=new BufferedWriter(new FileWriter(statistics));
			dataWriter.write(data);
			dataWriter.close();
		}
		catch(IOException ex){
			ex.printStackTrace();
			System.out.println("Error writing to file "+fileName);
		}
	}
}
