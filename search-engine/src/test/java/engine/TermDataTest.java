package engine;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class TermDataTest {
	List<File> listOfFiles;
	TermData td;
	@Before
	public void initialize() {
		File folder = new File("src//main//resources//indexedFiles//");

		 listOfFiles = Arrays.asList(folder.listFiles());

	}

	@Test
	public void TermData_CONSTRUCTOR_TEST() {
		File f = listOfFiles.get(0);
		td = new TermData(f);
		
		assertEquals("Constructed not properly", td.getFilesAndCounters().size(), 1);
		
		assertEquals("Counter not added", td.getFilesAndCounters().get(f), new Integer(1));
		
	}
}
