package engine;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import engine.SearchEngine;

public class SearchEngineTest {

	SearchEngine se;
	HashMap<File, Double> map;

	@Before
	public void initialize() {
		se = new SearchEngine();
		File folder = new File("src//main//resources//indexedFiles//");

		List<File> listOfFiles = Arrays.asList(folder.listFiles());

		se.mapListOfFiles(listOfFiles);

	}

	@Test
	public void mapListOfFilesTest() {

		// 14 unique words
		Assert.assertEquals("Not all files were mapped", se.getMappedTerms().size(), 14);
	}

	@Test(expected = IllegalArgumentException.class)
	public void mapFileTest_BLANK_FILE_NAME() {
		File f = new File(" ");

		se.mapFile(f);

	}

	@Test(expected = IllegalArgumentException.class)
	public void mapFileTest_NULL_FILE() {
		File f = null;

		se.mapFile(f);

	}

	@Test(expected = IllegalArgumentException.class)
	public void searchTest_NULL_QUERRY() {
		String query = null;

		se.search(query);

	}

	@Test(expected = IllegalArgumentException.class)
	public void searchTest_EMPTY_QUERRY() {
		String query = " ";

		se.search(query);
	}

	@Test
	public void searchTest_NOT_INDEXED_QUERRY() {
		String query = "v";

		// method search return null value if there were no results
		// so asking for the size shall cause Exception
		assertEquals("Some problem occured in search",se.search(query).size(),0);
	}

	@Test
	public void searchTest() {
		String query = "dog";
		assertEquals("Not all files have been found", se.search(query).size(), 3);

	}

}
