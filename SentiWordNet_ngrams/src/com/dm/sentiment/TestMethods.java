package com.dm.sentiment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class TestMethods {
	public static void main(String[] args){
/*		List<String> tokenList=tokenize("cv000_29590.txt", "\\s+|,\\s*|\\.\\s*");
		for(String token:tokenList)
			System.out.println(token);
	*/
		List<String> lst=new ArrayList<String>();
		lst.add("hi");
		addBye(lst);
		addWhy(lst);
		for(String st : lst)
			System.out.println(st);
	}
	
	private static void addWhy(List<String> lst) {
		// TODO Auto-generated method stub
		lst.add("why");
	}

	private static void addBye(List<String> lst) {
		// TODO Auto-generated method stub
		lst.add("bye");
	}

	public static List<String> tokenize(String filePath,String regEx){
		 List<String> tokenList=new ArrayList<String>();
			try{
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
			return tokenList;
	
	}
}
