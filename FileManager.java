
import java.io.*;
import java.util.*;
/**
 * Read in and manage training and test data files
 * @author shekharmaharaj
 *
 */

public class FileManager {
	
	//number of known topics
	int numTopics = 20;
	//list of topics
	ArrayList<File> topicList;
	//a list of all valid words
	ArrayList<String> vocabulary;
	//a list of all invalid words
	ArrayList<String> ignore;

	public FileManager() {
		topicList = new ArrayList<File>();
		vocabulary = new ArrayList<String>();
		ignore = new ArrayList<String>();
	}
	
	//get topics
	public ArrayList<File> getTopicList() {
		return topicList;
	}
	
	//get words on the ignore list
	public ArrayList<String> getIgnoreList() {
		return ignore;
	}
	
	//get words from the vocabulary
	public ArrayList<String> getVocabList() {
		return vocabulary;
	}
	
	
	
	//Get the training data from the training file
	public void setupTrainingData(String topicDirectory) {
		
		File directory = new File(topicDirectory);
		File[] fileList = directory.listFiles();
		
		for(File topic: fileList) {
			if(topic.isDirectory()) {			
				topicList.add(topic);			
			}else {
				if(topic.getName().equals("ignore.txt") || topic.getName().equals("vocabulary.txt")) {
					fillWordList(topic);
				}
			}
		}
		
	}
	
	//get the individual articles for each topic file
	public ArrayList<File> getArticles(File topicFile){
		ArrayList<File> articles = new ArrayList<File>();
		File[] articlesIn = topicFile.listFiles();
		
		for(File article: articlesIn) {
			if(article.isFile()) {
				articles.add(article);
			}
		}
		
		return articles;
	}
	
	
	//create a list of words from text file
	public void fillWordList(File f) {
		ArrayList<String> temp = null;
		if(f.getName().equalsIgnoreCase("ignore.txt")) {
			temp = ignore;
		} else {
			temp = vocabulary;
		}
		
		BufferedReader b = null;
		
		try {
			String line;
			b = new BufferedReader(new FileReader(f));
			
			while((line = b.readLine())!= null) {
				temp.add(line);
			}
			
		} catch(IOException e) {
			System.out.println(e);
		}
	}
	
}

