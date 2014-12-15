/*
 * Author: Rahul Goswami
 */
package com.dm.sentiment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dm.sentiment.config.Config;
import com.dm.sentiment.util.DMUtil;

public class SentimentClassifier {
	private Map<String,Double> swnDict;
	//private Map<String,Double> adjectiveMap;
	
	public SentimentClassifier() throws IOException{
		
		try{
			SentiWordNetDemoCode swn=new SentiWordNetDemoCode(Config.SENTI_WORDNET_DICTIONARY_PATH);
			swnDict=swn.getDictionary();
		//	adjectiveMap=DMUtil.getAdjectiveMap(swnDict);
		}
		catch(IOException ex){
			ex.printStackTrace();
			System.out.println("=========SentiWordNet Dictionary creation failed!!!=========");
			throw ex;
		}
		
	}
	
	public Map<String,List<String>> getClassificationForDataSet(String folderPath) throws IOException{
		String filePath;
		String classification;
		Map<String,List<String>> classificationMap=new HashMap<String,List<String>>();
		List<String> posClassFileList=new ArrayList<String>();
		List<String> negClassFileList=new ArrayList<String>();
		
		classificationMap.put(Config.POS_CLASSNAME,posClassFileList);
		classificationMap.put(Config.NEG_CLASSNAME,negClassFileList);
		
		File folder = new File(folderPath);
		File[] listOfFiles = folder.listFiles();
		
		for(int i=0; i<listOfFiles.length; i++){
			if (listOfFiles[i].isFile()) {
		        filePath=folderPath+"\\"+listOfFiles[i].getName();
		        classification=getClassification(filePath);
		        classificationMap.get(classification).add(listOfFiles[i].getName());
		      }
		}
		return classificationMap;
	}	
		/*    for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile()) {
		        System.out.println("File " + listOfFiles[i].getName());
		      } else if (listOfFiles[i].isDirectory()) {
		        System.out.println("Directory " + listOfFiles[i].getName());
		      }
		    }
		*/
		//getClassification();
	
/*	public static void main(String[] args){
		getClassificationForDataSet(Config.folderPath);
	}
	*/

	public String getClassification(String filePath) throws IOException {
		// TODO Auto-generated method stub
		List<String> nGramsList=new ArrayList<String>();
		List<String> tokenList=DMUtil.tokenize(filePath, Config.DELIMITERS);
		tokenList=DMUtil.sanitizeList(tokenList);
		if(Config.NGRAM_MODE.equals("tri")){
			List<String> bigrams=addBigrams(tokenList);
			List<String> trigrams=addTrigrams(tokenList);

			for(String token: bigrams){
				tokenList.add(token);
			}

			for(String token: trigrams){
				tokenList.add(token);
			}
		}
		//	DMUtil.writeData("temp.txt", tokenList.toString());
		Double score = getSentimentScore(tokenList);
		/*
		 * Since we feel SentiWordNet is slightly biased towards positive words
		 * based on the number of false positives, a score of 0 is classified as negative
		 * (as a balancing factor)
		 */
		if(score<=0.51){
			return Config.NEG_CLASSNAME;
		}
		else
			return Config.POS_CLASSNAME;
	}

	private List<String> addTrigrams(List<String> tokenList) {
		// TODO Auto-generated method stub
		int len=tokenList.size();
		List<String> triGramsList=new ArrayList<String>();
		for(int i=0;i<len-2;i++){
			String trigram=""+tokenList.get(i)+"_"+tokenList.get(i+1)+"_"+tokenList.get(i+2);
			triGramsList.add(trigram);
		}
		return triGramsList;
	}

	private List<String> addBigrams(List<String> tokenList) {
		// TODO Auto-generated method stub
		int len=tokenList.size();
		List<String> biGramsList=new ArrayList<String>();
		for(int i=0;i<len-1;i++){
			String bigram=""+tokenList.get(i)+"_"+tokenList.get(i+1);
			biGramsList.add(bigram);
		}
		return biGramsList;
	}

	private Double getSentimentScore(List<String> tokenList) {
		// TODO Auto-generated method stub
		Double score=0D;

		for(String token:tokenList){
			/*	if(adjectiveMap.containsKey(token)){
				score+=adjectiveMap.get(token);
			}
			 */
			double tokenScore=0D;
			int POSCount=0;
			if(swnDict.get(token+"#a")!=null){
				tokenScore+=swnDict.get(token+"#a");
				POSCount++;
			}
			
			if(Config.POS_MODE.equals("all")){
				if(swnDict.get(token+"#n")!=null){
					tokenScore+=swnDict.get(token+"#n");
					POSCount++;
				}
				if(swnDict.get(token+"#r")!=null){
					tokenScore+=swnDict.get(token+"#r");
					POSCount++;
				}
				if(swnDict.get(token+"#v")!=null){
					tokenScore+=swnDict.get(token+"#v");
					POSCount++;
				}
			}
			
			//Take the average of a word's sentiment score across all POS 
			if(POSCount!=0)
				score+=tokenScore/POSCount;

		}
		return score;
	}
	
	
 
}
