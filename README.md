# SentimentClassifier_ngrams
The text is broken into uni, bi and tri grams and these n-grams are checked for in the SentiWordNet dictionary. Configuration settings are provided to either consider an n-gram as either an adjective(if it exists as one, eg:"entertaining" could be a verb or an adjective) or average out its score across all possible parts of speech that it can belong to. These scores for all such n-grams are added to finally evaluate the sentiment score of the entire text. The review is accordingly classified as positive or negative.
