/*
 * Author: Rahul Goswami
 */
package com.dm.sentiment.config;

public class Config {
	public static final String SENTI_WORDNET_DICTIONARY_PATH = "SentiWordNetDictionary.txt";
	//public static final String DELIMITERS="\\s+|,\\s*|\\.\\s*|/\\s*";
	//public static final String DELIMITERS="\\s+|\\s*,\\s*|\\s*\\.\\s*|\\s*/\\s*";
	public static final String DELIMITERS="\\W";
	public static final String POS_FOLDER_PATH="data\\pos\\";
	public static final String NEG_FOLDER_PATH="data\\neg\\";
	public static final String POS_CLASSNAME = "pos";
	public static final String NEG_CLASSNAME = "neg";
	//public static final String ONE_THREE_GRAM_TOKENFILE_PATH="data\\NGramFile.txt";
	public static final String NGRAM_MODE = "tri";
	public static final String POS_MODE = "all";
}
