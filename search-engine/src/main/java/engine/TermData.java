package engine;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TermData {

	/**
	 * Map of all files where word from Map in class SearchEngine occurs. Key is
	 * File where it occur and Value is counter of it's occurrences. It is used to
	 * compute TF.
	 */
	private Map<File, Integer> filesAndCounters;

	/**
	 * Static Map of all indexed files in SearchEngine with word counters in them
	 * Key is a indexed File and Value is count of words in that file. It is used to
	 * compute IDF
	 */
	private static Map<File, Integer> theCountOfAllWordsInFiles = new HashMap<>();

	/**
	 * Simple constructor for TermData where new HashMap created and file is added
	 * into it. Also counter is changed to the 1 because constructor is used in
	 * first occurrence of the word in text.
	 * 
	 * @param file which is going to be added into filesAndCounters
	 */
	public TermData(File file) {
		filesAndCounters = new HashMap<>();
		filesAndCounters.put(file, 1);
	}

	/**
	 * Method for generating a HashMap<File, Double> where Key is the File where our
	 * word in SearchEngine occurs and Value is the TF-IDF. There is no need for
	 * word as parameter because the word is set in the SearchEngine Map
	 * 
	 * @return HashMap<File,Double> of all files for word in SearchEngine Map
	 */
	public HashMap<File, Double> getFilesAndTFIDF() {

		HashMap<File, Double> theFileAndTFIDF = new HashMap<>();
		filesAndCounters.forEach((k, v) -> theFileAndTFIDF.put(k, computeTFIDF(k)));

		return theFileAndTFIDF;
	}

	/**
	 * Computes the tf and idf for given File for word that is proceeded by
	 * SearchEngine class. For TF computing we use formula `(n/m)` where `n` is
	 * count of word occurrence in File and `m` is count of all words in File. For
	 * IDF computing we use formula `log(j/k + 1)` where `j` is count of all indexed
	 * Files and `k` is count of all files where our word occurs. `1` is added to
	 * the divider to avoid situations like `log(1/1) = 0`
	 * 
	 * @param file for which TF-IDF shall be computed
	 * @return TF-IDF of the file for word
	 */
	public Double computeTFIDF(File file) {
		Double tf = 0.0;
		Double idf = 0.0;

		tf = (double) filesAndCounters.get(file) / (double) theCountOfAllWordsInFiles.get(file);

		idf = Math.log((double) theCountOfAllWordsInFiles.size() / (double) filesAndCounters.size() + 1);

		return tf * idf;
	}

	/**
	 * Method for adding a counter to the Map of files where our word occurs.
	 * 
	 * @param file to which counter should be added one.
	 */
	public void addCounter(File file) {
		if (!filesAndCounters.containsKey(file)) {
			addFile(file);
		} else {
			filesAndCounters.replace(file, filesAndCounters.get(file) + 1);
		}
	}

	/**
	 * Static method for adding a new file into the Map of all files and count of
	 * words that it has.
	 * 
	 * @param file
	 * @param count
	 */
	public static void addFileAndWordsCounter(File file, Integer count) {
		theCountOfAllWordsInFiles.put(file, count);
	}

	/**
	 * Private method used by addCounter method in case if there was no Files like
	 * that before. It simply adds a new File into the filesAndCounters Map
	 * 
	 * @param file which is going to be added into the map
	 */
	private void addFile(File file) {
		filesAndCounters.put(file, 1);
	}

	/**
	 * Simple toString method, shows a list of {filepath=counterValue}
	 */
	public String toString() {
		return Arrays.asList(filesAndCounters).toString();
	}

	public Map<File, Integer> getFilesAndCounters() {
		return filesAndCounters;
	}

	public static Map<File, Integer> getTheCountOfAllWordsInFiles() {
		return theCountOfAllWordsInFiles;
	}
}
