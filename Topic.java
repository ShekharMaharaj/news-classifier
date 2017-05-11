import java.util.*;
/**
 * Store relevant training data about a topic. Use these details for classification
 * @author shekharmaharaj
 *
 */
public class Topic {
	
	String topicName;
	double topicProbability;
	
	//all distinct word positions for topic
	ArrayList<String> vocabulary;
	//hash map of words and their frequencies
	public HashMap<String, Integer> wordFrequencyIndex;
	
	
	public Topic(String topicName, double topicProbability, ArrayList<String> vocabulary) {
		wordFrequencyIndex = new HashMap<String, Integer>();	
		this.topicName = topicName;
		this.topicProbability = topicProbability;
		this.vocabulary = vocabulary;
		
		//populate hash map
		fillIndex();
	}
	
	//get topic name
	public String getName() {
		return topicName;
	}
	
	//get topic probability
	public double getTopicProbability() {
		return topicProbability;
	}
	
	//get number of distinct word positions
	public int getVocabSize() {
		return vocabulary.size();
	}
	
	//get frequency of a word
	public int getFrequency(String word) {
		if(wordFrequencyIndex.containsKey(word)) {
			return wordFrequencyIndex.get(word);
		} else {
			return 0;
		}
	}
	
	//populate hash map
	public void fillIndex() {
		for(String word: vocabulary) {
			if(wordFrequencyIndex.containsKey(word)) {
				wordFrequencyIndex.put(word, (wordFrequencyIndex.get(word) + 1));
				//System.out.println("<"+word+", "+wordFrequencyIndex.get(word)+">");
			} else {
				wordFrequencyIndex.put(word, 1);
			}
		}
	}
	
	
}
