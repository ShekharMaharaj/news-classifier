# news-classifier
A program that classifies news articles into categories using the Naive Bayes Classifier algorithm.
All code authored by Shekhar Maharaj, and no external machine learning libraries were used for this project.

Below are brief descriptions of each class:

NaiveBayesClassifier - Entire program runs from this class. Instances of objects are created using the input. Runs classification algorithm on input and performs test to determine amount of correctly classified articles.

FileManager - Sets up training and test data into structured input for the Classifier class.

Topic - Defines each topic/category that the Classifier class will use to match articles to topics/categories.

Classifier - Naive Bayes Classifier implemented in this class. Trains using training data and classifies the test data.

