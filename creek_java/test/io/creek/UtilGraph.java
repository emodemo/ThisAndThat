package io.creek;

import static java.nio.file.Files.readAllLines;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;

public class UtilGraph {

	private final static String RESOURCES = "./testResources";
	private final static String FILE_MISSING = "%s is missing from %s folder";
	
	public static <T> void loadDataInGraph(String fileName, String split, Graph<T> graph, Function<String, T> function){
		try {
			File file = new File(RESOURCES, fileName).getCanonicalFile();
			assertTrue(file.exists(), String.format(FILE_MISSING, fileName, RESOURCES));
			List<String[]> lines = readAllLines(file.toPath()).stream().map(line -> line.split(split)).collect(Collectors.toList());

			for(String[] line : lines) {
				if(line.length == 1) graph.addEdge(function.apply(line[0]), null);
				else if(line.length == 2) graph.addEdge(function.apply(line[0]), function.apply(line[1]));
				else graph.addEdge(function.apply(line[0]), function.apply(line[1]), Double.parseDouble(line[2])); 
			}
		} catch (IOException e) {
			Assertions.fail(e.getMessage());
		}
	}
}
