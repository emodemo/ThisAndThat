package com.scripting;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbibtex.BibTeXDatabase;
import org.jbibtex.BibTeXEntry;
import org.jbibtex.BibTeXFormatter;
import org.jbibtex.BibTeXParser;
import org.jbibtex.CharacterFilterReader;
import org.jbibtex.DigitStringValue;
import org.jbibtex.Key;
import org.jbibtex.ParseException;
import org.jbibtex.StringValue;
import org.jbibtex.StringValue.Style;
import org.jbibtex.TokenMgrException;

public class BibtexAPI {

	private static final String FILE1 = "notes.bib";
	private static final String FILE2 = "notes2.bib";
	private static final String BOOK = "book";
	// private static final String ARTICLE = "article";
	private static final String TITLE = "title";
	private static final String AUTHOR = "author";
	private static final String YEAR = "year";
	private static final String TAGS = "tags";
	
	public static void main(String[] args) {
		
	}

	private static void beautify(){
		try (CharacterFilterReader reader = new CharacterFilterReader(new FileReader(FILE1, StandardCharsets.UTF_8))) {
			BibTeXParser parser = new BibTeXParser();
			BibTeXDatabase db = parser.parse(reader);
			try (Writer writer = new FileWriter(new File(FILE2), StandardCharsets.UTF_8)) {
				BibTeXFormatter formatter = new BibTeXFormatter();
				formatter.format(new SortableDB(db), writer);
			}
		} catch (TokenMgrException | ParseException | IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void readWrite() {
		List<BibTeXEntry> newData = createDB();

		try (CharacterFilterReader reader = new CharacterFilterReader(new FileReader(FILE1, StandardCharsets.UTF_8))) {
			BibTeXParser parser = new BibTeXParser();
			BibTeXDatabase db = parser.parse(reader);
			try (Writer writer = new FileWriter(new File(FILE2), StandardCharsets.UTF_8)) {
				BibTeXDatabase newDB = new BibTeXDatabase();
				// add only newly present, save existing's info, skip deleted
				for (BibTeXEntry entry : newData) {
					BibTeXEntry xEntry = db.resolveEntry(entry.getKey());
					xEntry = xEntry != null ? xEntry : entry;
					newDB.addObject(xEntry);
				}
				BibTeXFormatter formatter = new BibTeXFormatter();
				formatter.format(new SortableDB(newDB), writer);
			}
		} catch (TokenMgrException | ParseException | IOException e) {
			e.printStackTrace();
		}
	}
	
	private static List<BibTeXEntry> createDB() {

		List<BibTeXEntry> entries = new ArrayList<>();
		File folder1 = new File("F:\\DOCUMENTS\\LIBRARY\\DEVELOPMENT");
		File folder2 = new File("F:\\DOCUMENTS\\LIBRARY\\SCIENCE");
		Map<String, File> map = new HashMap<>();
		map.put("development", folder1);
		map.put("science", folder2);
		for (Map.Entry<String, File> e : map.entrySet()) {
			File[] files = e.getValue().listFiles();
			for (File file : files) {
				if (file.isFile()) {
					int suffixIdx = file.getName().lastIndexOf(".");
					String name = file.getName().substring(0, suffixIdx);
					String[] split = name.split(" - ");
					String author = split[0];
					author = author.replace("_", " and ");
					author = author.replace("&", "and");
					author = author.replace(" et al", "");
					String year = split.length == 2 ? "0000" : split[split.length - 1];
					entries.add(createEntry(author, split[1], year, e.getKey(), entries));
				}
			}
		}
		return entries;
	}

	private static BibTeXEntry createEntry(String author, String title, String year, String defaultTag, List<BibTeXEntry> entries) {
		String key = author.substring(0, author.indexOf(" ") > 0 ? author.indexOf(" ") : author.length()).trim();
		key = key + year.trim();
		key = key + title.substring(0, title.indexOf(" ") > 0 ? title.indexOf(" ") : title.length()).trim();
		// if already existing add counter
		String keykey = key;
		long count = entries.stream().filter(e -> e.getKey().getValue().startsWith(keykey)).count();
		key = count > 0 ? key + ++count : key;
		BibTeXEntry entry = new BibTeXEntry(new Key(BOOK), new Key(key));
		entry.addField(new Key(AUTHOR), new StringValue(author, Style.BRACED));
		entry.addField(new Key(TITLE), new StringValue(title, Style.BRACED));
		entry.addField(new Key(YEAR), new DigitStringValue(year));
		entry.addField(new Key(TAGS), new StringValue(defaultTag, Style.BRACED));
		return entry;
	}
}
