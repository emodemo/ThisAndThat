package org.dummy.lucene.index;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.BoostQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;

public class Reader {

    /**
     * query examples: <a href="https://lucene.apache.org/core/2_9_4/queryparsersyntax.html">...</a>
     */
    public void readIndex(String queryString, String indexFolder){
        try(Directory directory = FSDirectory.open(Paths.get(indexFolder))){
            IndexReader indexReader = DirectoryReader.open(directory);
            IndexSearcher searcher = new IndexSearcher(indexReader);
            // currently this is the same analyzer as in the writer
            // "contents" where we put the written data
            // see documentation for dsl symbols :) + CommonQueryParserConfiguration
            QueryParser queryParser = new QueryParser("contents", new StandardAnalyzer());
            Query query = queryParser.parse(queryString);
            // PhraseQuery -
            // BooleanQuery - query combination
            // Query q = new BooleanQuery.Builder()
            // .add(originalQuery, Occurs.MUST)
            // .add(BoostQuery(featureQuery, 0.7f), Occurs.SHOULD).build()
            //  matches the same doc as originalQuery and computes score as similarity + 0.7 * feature score
            // https://www.youtube.com/watch?v=5tfi1uCbBxw bad english, good examples
            TopDocs found = searcher.search(query, 10);

            System.out.println("Found %d matches".formatted(found.totalHits.value));

            for(var searchDoc : found.scoreDocs){
                var fields = searcher.storedFields();
                var document = fields.document(searchDoc.doc);

                var formatted = "Path : %s, Contents : %s, Score: %f"
                        .formatted(document.get("path"), document.get("contents"), searchDoc.score);

                System.out.println(formatted);
            }

        } catch (IOException | ParseException ex){
            System.out.println(ex.getMessage());
        }
    }

    // ... and all kinds of queries

    private Query termQuery(){
        Term term = new Term("contents", "time");
        return new TermQuery(term);
    }


    private Query prefixQuery(){
        Term term = new Term("contents", "intro");
        return new PrefixQuery(term);
    }

}
