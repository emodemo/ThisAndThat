package org.dummy.lucene;

import org.dummy.lucene.index.Query;
import org.dummy.lucene.index.Reader;
import org.dummy.lucene.index.Writer;

import java.util.List;

public class Application {

    public static void main(String[] args) {

        var dataFolder = "dummy-lucene/src/main/resources/data";
        var indexFolder = "dummy-lucene/src/main/resources/index";

        Writer indexWriter = new Writer();
        indexWriter.writeIndex(dataFolder, indexFolder);
        Reader reader = new Reader();
        reader.readIndex("time", indexFolder);

        // queries - see readme
        Query query = new Query();
        var result = query.canSplit(
                List.of("state", "bank", "of", "Sofia", "amazon", "prime", "video"),
                "statebankofSofia");
        System.out.println(result);

    }

}