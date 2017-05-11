import java.io.*;
import java.util.*;
/**
 * Naive Bayes Classifier training and classifying algorithm
 * @author shekharmaharaj
 *
 */

public class Classifier {

	//total counted number of training files
	int totalNumTrainingFiles = 11314; 
	
	//list of valid words
	ArrayList<String> vocabulary;
	//list of invalid words
	ArrayList<String> ignore;
	//list of topic training data
	ArrayList<Topic> topicTrainingData;
	
	public Classifier(ArrayList<String> vocabulary, ArrayList<String> ignore) {
		this.vocabulary = vocabulary;
		this.ignore = ignore;
		topicTrainingData = new ArrayList<Topic>();
	}
	
	//training function
	public void train(String topicName, ArrayList<File> trainingFiles) {
		
		//remove .DS_STORE file
		trainingFiles.remove(0);
		
		//list of all distinct word positions in trainingFiles
		ArrayList<String> instances = new ArrayList<String>();
			
		//read all files in topic folder
		for(File f: trainingFiles) {
			
			//read document
			BufferedReader b = null;
			
			try {
				String line;
				b = new BufferedReader(new FileReader(f));
				while((line = b.readLine())!= null) {
					
					//split line into individual words
					String[] words = line.split(" ");
					
					//check and edit each word if needed
					for(String w:words) {
						
						//check each character
						char[] letters = w.toCharArray();
						String word = "";
						
						for(char c: letters) {
							if(Character.isLetter(c)) {
								word = word +c;
							}
						}
						
						//check if word is a real word and not in the ignore list
						if(vocabulary.contains(word) && !ignore.contains(word)) {
							instances.add(word);
						}
					}
				}
				
			} catch(IOException e) {
				System.out.println(e);
			}	
		}
		
		
		//calculate topic probability
		double topicProbability = trainingFiles.size()/totalNumTrainingFiles;
		
		//store all training data for future reference
		topicTrainingData.add(new Topic(topicName, topicProbability, instances));	
	}
	
	
	//Classification function
	public String classify(File article) {
		
		//list of all distinct word positions in article
		ArrayList<String> instances = new ArrayList<String>();
		
		//read document
		BufferedReader b = null;
		
		try {
			String line;
			b = new BufferedReader(new FileReader(article));
			while((line = b.readLine())!= null) {
				
				//split line into individual words
				String[] words = line.split(" ");
				
				//check and edit each word if needed
				for(String w:words) {
					
					//check each character
					char[] letters = w.toCharArray();
					String word = "";
					
					for(char c: letters) {
						if(Character.isLetter(c)) {
							word = word +c;
						}
					}
					
					//check if word is a real word and not in the ignore list
					if(vocabulary.contains(word) && !ignore.contains(word)) {
						instances.add(word);
					}
				}
			}
			
			b.close();
			
		} catch(IOException e) {
			System.out.println(e);
		}	
		
		
		//classification algorithm
		double max = -1;
		String topicName = "";
		
		//go through all possible topics
		for(Topic t: topicTrainingData) {
			//initialise with the topic probability
			double prob = t.getTopicProbability();
			//calculate probability for all distinct word positions
			for(String w: instances) {
				if(t.wordFrequencyIndex.containsKey(w)){
					prob = prob*calcProbability(t.getName(),w);
				}
			}
			
			//argmax
			if(prob>max) {
				topicName = t.getName();
			}
		}
		
		return topicName;
	}
	
	
	//calculate probability based on training data
	private double calcProbability(String topic, String word) {
		Topic curTopic = null;
		
		for(Topic t: topicTrainingData) {
			if(t.getName().equals(topic)) {
				curTopic = t;
			}
		}
		
		//m-estimate of probability
		double probability = (curTopic.getFrequency(word)+1)/ (curTopic.getVocabSize() + vocabulary.size());
		
		return probability;
		
	}
}
