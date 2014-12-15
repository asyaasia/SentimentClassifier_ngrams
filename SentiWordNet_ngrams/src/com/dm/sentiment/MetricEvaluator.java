/*
 * Author: Rahul Goswami
 */
package com.dm.sentiment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.dm.sentiment.config.Config;

public class MetricEvaluator {
	public void generateStatistics() throws IOException{
		SentimentClassifier sc=new SentimentClassifier();
		
		Map<String,List<String>> positiveReviewStats=sc.getClassificationForDataSet(Config.POS_FOLDER_PATH);
		Map<String,List<String>> negativeReviewStats=sc.getClassificationForDataSet(Config.NEG_FOLDER_PATH);
		long numTruePositives=positiveReviewStats.get(Config.POS_CLASSNAME).size();
		long numTrueNegatives=negativeReviewStats.get(Config.NEG_CLASSNAME).size();
		long numFalsePositives=negativeReviewStats.get(Config.POS_CLASSNAME).size();
		long numFalseNegatives=positiveReviewStats.get(Config.NEG_CLASSNAME).size();
		
		
		double f1=calculateF1(numTruePositives,numTrueNegatives,numFalsePositives,numFalseNegatives);
		double accuracy=calculateAccuracy(numTruePositives,numTrueNegatives,numFalsePositives,numFalseNegatives);
		try{
		File statistics=new File("Statistics.txt");
		BufferedWriter statsWriter=new BufferedWriter(new FileWriter(statistics));
		statsWriter.write("Number of True Positives: ");
		statsWriter.write(((Long)numTruePositives).toString()+"\n");
		
		statsWriter.write("Number of True Negatives: ");
		statsWriter.write(((Long)numTrueNegatives).toString()+"\n");
		
		statsWriter.write("Number of False Positives: ");
		statsWriter.write(((Long)numFalsePositives).toString()+"\n");
		
		statsWriter.write("Number of False Negatives: ");
		statsWriter.write(((Long)numFalseNegatives).toString()+"\n");
		
		statsWriter.write("ACCURACY: ");
		statsWriter.write(((Double)(accuracy*100)).toString()+"%\n");

		statsWriter.write("F1-MEASURE: ");
		statsWriter.write(((Double)(f1*100)).toString()+"%\n");

		statsWriter.close();
		}
		catch(IOException ex){
			ex.printStackTrace();
			System.out.println("=======Error writing statitics to file===========");
		}
	}

	private double calculateAccuracy(long TP,long TN, long FP,long FN ) {
		// TODO Auto-generated method stub
		return ((double)(TP+TN)/(TP+TN+FP+FN));
	}

	private double calculateF1(long TP,long TN, long FP,long FN) {
		// TODO Auto-generated method stub
		double precision=calculatePrecision(TP,FP);
		double recall=calculateRecall(TP,FN);
		return (2*precision*recall)/(precision+recall);
	}

	private double calculatePrecision(long TP,long FP) {
		// TODO Auto-generated method stub
		return (double)TP/(TP+FP);
	}
	
	private double calculateRecall(long TP,long FN) {
		// TODO Auto-generated method stub
		return (double)TP/(TP+FN);
	}

	public static void main(String[] args){
		MetricEvaluator me=new MetricEvaluator();
		try{
		me.generateStatistics();
		}
		catch(IOException ex){
			ex.printStackTrace();
			System.out.println("====Error generating statistics=====");
		}
	}
}
