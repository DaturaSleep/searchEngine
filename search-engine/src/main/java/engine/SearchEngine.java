package engine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

public class SearchEngine {

	/**
	 * Simple Log4j logger for logging operations in this class. You can find it
	 * properties inside resources folder in log4j.properties file
	 */
	private static final Logger log = Logger.getLogger(SearchEngine.class);

	/**
	 * Hash map of all indexed words where Key is indexed word and Value is word
	 * meta-data (like count of occurrences in specific file, static Map of all
	 * indexed files with count of words in them and etc).
	 */
	private HashMap<String, TermData> mappedTerms = new HashMap<>();

	/**
	 * Chars that match this String is going to be replaced by blank space This is
	 * made for situations like, for example: "dog." and "dog" will be parsed like
	 * two different words but they both shall be parsed like "dog"
	 */
	private final String CHARS_TO_REPLACE = "[-+.^:,\"*()?!;]";

	/**
	 * This method is doing the same as mapFile method but only for the List of
	 * Files.
	 * 
	 * @param listOfFiles
	 */
	public void mapListOfFiles(List<File> listOfFiles) {
		if (listOfFiles == null || listOfFiles.size() == 0) {
			throw new IllegalArgumentException("You cant map a null List or list without Files");
		}
		listOfFiles.forEach(k -> mapFile(k));
	}

	/**
	 * This method maps File into mappedTerms, BufferedReader used there for better
	 * performance
	 * 
	 * @param file
	 * @throws IOException
	 */
	public void mapFile(File file) {
		if (file == null) {
			log.warn(String.format("Nullable file passed"));
			throw new IllegalArgumentException(String.format("Nullable file passed:"));
		}
		if (file.isDirectory() || !file.canRead() || !file.exists()) {
			log.warn(String.format("Illegal file passed: %s", file.getAbsolutePath()));
			throw new IllegalArgumentException(String.format("Illegal file passed: %s", file.getAbsolutePath()));
		}

		try (BufferedReader reader = new BufferedReader(new FileReader(file));) {

			String line;
			String[] words;
			Integer allWordsInFileCount = 0;

			while ((line = reader.readLine()) != null) {

				words = line.replaceAll(CHARS_TO_REPLACE, "").toLowerCase().split(" ");
				allWordsInFileCount = allWordsInFileCount + words.length;

				for (String word : words) {
					if (mappedTerms.containsKey(word)) {
						mappedTerms.get(word).addCounter(file);
					} else {
						mappedTerms.put(word, new TermData(file));
					}
				}

			}

			TermData.addFileAndWordsCounter(file, allWordsInFileCount);
			log.info(String.format("File %s has been mapped", file.getAbsolutePath()));

		} catch (IOException e) {
			log.warn("IO Exception: ", e);
		}
	}

	/**
	 * This method is searching for given word in map of all indexed words
	 * mappedTerms. it return a sorted HashMap<File, Double> where Key is File where
	 * this word has been found and and Value is TF-IDF of searched word in this
	 * File or if word has not been found it might return null value.
	 * 
	 * @param String word
	 * 
	 * @return HashMap<File,Double> searchResult
	 * @return null
	 */
	public List<File> search(String word) {
		/*
		 * This method can be easily modified to return a HashMap<File,Double> Where Key
		 * is a File and Value is a TFIDF number. By the task we have to return a list
		 * of Files.
		 */
		if (word == null || word.isEmpty() || word.trim().isEmpty()) {
			log.warn("Null or empty word searched");
			throw new IllegalArgumentException("Word can't be null or empty");
		}

		final long searchStart = System.currentTimeMillis();
		word = word.toLowerCase();
		word = word.replaceAll(CHARS_TO_REPLACE, "");

		if (!(mappedTerms.get(word) == null)) {

			HashMap<File, Double> searchResult = mappedTerms.get(word).getFilesAndTFIDF();
			final long searchFinish = System.currentTimeMillis();

			log.info(String.format("The search completed in %d miliseconds", searchFinish - searchStart));
			searchResult = sortByValue(searchResult, false);

			List<File> listOfFoundFiles = new ArrayList<>(searchResult.keySet());

			return listOfFoundFiles;

		} else {

			/*
			 * If there is no results in search it will return a new ArrayList<>()
			 */
			final long searchFinish = System.currentTimeMillis();
			log.info(String.format("The search completed in %d miliseconds", searchFinish - searchStart));
			return new ArrayList<>();
		}
	}

	/**
	 * This method used only for HashMap sorting by values
	 * 
	 * @param unsortMap unsorted map
	 * @param order     true for ascended order false for descended order
	 * @return sorted HashMap of File and TF-IDF value
	 */
	private HashMap<File, Double> sortByValue(HashMap<File, Double> unsortMap, final boolean order) {
		List<Entry<File, Double>> list = new LinkedList<>(unsortMap.entrySet());

		list.sort((o1, o2) -> order
				? o1.getValue().compareTo(o2.getValue()) == 0 ? o1.getKey().compareTo(o2.getKey())
						: o1.getValue().compareTo(o2.getValue())
				: o2.getValue().compareTo(o1.getValue()) == 0 ? o2.getKey().compareTo(o1.getKey())
						: o2.getValue().compareTo(o1.getValue()));
		return list.stream().collect(Collectors.toMap(Entry::getKey, Entry::getValue, (a, b) -> b, LinkedHashMap::new));

	}

	public HashMap<String, TermData> getMappedTerms() {
		return mappedTerms;
	}

}
