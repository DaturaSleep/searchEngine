# Search Engine
Simple search engine with TF-IDF usage

## Task
The goal of this assignment is to create a simple search engine in Java. The search engine should be implemented
as an inverted index (http://en.wikipedia.org/wiki/Inverted_index) that runs in memory and can return a result
list that is sorted by TF-IDF (http://en.wikipedia.org/wiki/Tf*idf).

### The search engine should:
* be able to take in a list of documents [method mapListOfFiles](https://github.com/DaturaSleep/searchEngine/blob/master/search-engine/src/main/java/engine/SearchEngine.java)
* support searches for single terms in the document set [method search](https://github.com/DaturaSleep/searchEngine/blob/master/search-engine/src/main/java/engine/SearchEngine.java)
* return a list of matching documents sorted by TF-IDF [method search](https://github.com/DaturaSleep/searchEngine/blob/master/search-engine/src/main/java/engine/SearchEngine.java)


### Navigation 
* [JUnit tests](https://github.com/DaturaSleep/searchEngine/tree/master/search-engine/src/test/java/engine)
* [Indexed Folder](https://github.com/DaturaSleep/searchEngine/tree/master/search-engine/src/main/resources/indexedFiles)
* [SearchEngine class](https://github.com/DaturaSleep/searchEngine/blob/master/search-engine/src/main/java/engine/SearchEngine.java)
* [TermData class](https://github.com/DaturaSleep/searchEngine/blob/master/search-engine/src/main/java/engine/TermData.java)
* [Main class](https://github.com/DaturaSleep/searchEngine/blob/master/search-engine/src/main/java/main/Main.java)
