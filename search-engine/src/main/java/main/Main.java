package main;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import engine.SearchEngine;

public class Main {
	public static void main(String[] args) throws IOException {
		SearchEngine se = new SearchEngine();

		File folder = new File("src//main//resources//indexedFiles//");

		List<File> listOfFiles = Arrays.asList(folder.listFiles());

		se.mapListOfFiles(listOfFiles);

		String querryWord = "";
		Scanner sc = new Scanner(System.in);
		List<File> result;

		while (!querryWord.equals("exit")) {

			querryWord = sc.nextLine();
			result = se.search(querryWord);

			if (!(result.size() == 0)) {

				result.forEach((k) -> System.out.println(k));

			} else {
				System.out.println(String.format("There is no word %s indexed", querryWord));
			}
		}
		sc.close();

	}
}
