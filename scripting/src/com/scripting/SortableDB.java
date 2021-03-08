package com.scripting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.jbibtex.BibTeXDatabase;
import org.jbibtex.BibTeXEntry;
import org.jbibtex.BibTeXObject;
import org.jbibtex.BibTeXString;
import org.jbibtex.Key;
import org.jbibtex.Value;

public class SortableDB extends BibTeXDatabase {

	private static final String AUTHOR = "author";
	private static final String TITLE = "title";
	private BibTeXDatabase db;
	
	public SortableDB(BibTeXDatabase db) {
		super();
		this.db = db;
	}

	@Override
	public Map<Key, BibTeXEntry> getEntries() {
		return db.getEntries();
	}
	
	@Override
	public void removeObject(BibTeXObject object) {
		db.removeObject(object);
	}
	
	@Override
	public Map<Key, BibTeXString> getStrings() {
		return db.getStrings();
	}
	
	@Override
	public BibTeXEntry resolveEntry(Key key) {
		return db.resolveEntry(key);
	}
	
	@Override
	public BibTeXString resolveString(Key key) {
		return db.resolveString(key);
	}
	
	@Override
	public void addObject(BibTeXObject object) {
		db.addObject(object);
	}

	@Override
	public List<BibTeXObject> getObjects() {
		List<BibTeXObject> list = new ArrayList<>(db.getObjects());
		Collections.sort(list, new DBComparator());
		return Collections.unmodifiableList(list);
	}

	static class DBComparator implements Comparator<BibTeXObject> {

		@Override
		public int compare(BibTeXObject o1, BibTeXObject o2) {
			if (o1 instanceof BibTeXEntry && o2 instanceof BibTeXEntry) {
				BibTeXEntry e1 = (BibTeXEntry) o1;
				BibTeXEntry e2 = (BibTeXEntry) o2;
				int type = e1.getType().getValue().compareTo(e2.getType().getValue());
				if (type != 0) return type;
				int author = getField(e1, AUTHOR).compareTo(getField(e2, AUTHOR));
				if (author != 0) return author;
				return getField(e1, TITLE).compareTo(getField(e2, TITLE));
			}
			return 0;
		}

		private String getField(BibTeXEntry e, String field) {
			Value value = e.getField(new Key(field));
			return value == null ? "" : value.toUserString();
		}

	}

}
