import java.io.*;
import java.util.*;

/**
 * Topic 11: Text Categorization
 * @author shekharmaharaj
 *
 */
public class NaiveBayesClassifier {
	FileManager fileMan;
	Classifier classifier;
	
	public NaiveBayesClassifier() {
		fileMan = new FileManager();
		classifier = new Classifier(fileMan.getVocabList(), fileMan.getIgnoreList());
	}
	
	//get file manager
	public FileManager getFileManager() {
		return fileMan;
	}
	
	//get classifier
	public Classifier getClassifier() {
		return classifier;
	}
	
	//main function
	public static void main(String[] args) {
		
		NaiveBayesClassifier nBC = new NaiveBayesClassifier();
		
		//train classifier
		nBC.getFileManager().setupTrainingData(args[1]);
		for(File t: nBC.getFileManager().getTopicList()) {
			nBC.getClassifier().train(t.getName(), nBC.getFileManager().getArticles(t));
		}
		System.out.println("Training Completed");
		
		//test classifier
		File test = new File(args[2]);
		File[] testFiles = test.listFiles();
		int totNumArticles = 0;
		int numClassCorrect = 0;
		
		for(File f: testFiles) {
			if(!f.isHidden()) {
				ArrayList<File> articles =  nBC.getFileManager().getArticles(f);
				articles.remove(0);
			
				for(File a: articles) {
					//check if returned topic matches topic on file name
					String cl = nBC.getClassifier().classify(a);
					System.out.println(cl+" = "+f.getName());
					if(cl.equals(f.getName())) {
						numClassCorrect++;
					}
					totNumArticles++;
				}
			}
		}
		
		System.out.println(numClassCorrect+" out of "+totNumArticles+" correctly classified");
	}
}
