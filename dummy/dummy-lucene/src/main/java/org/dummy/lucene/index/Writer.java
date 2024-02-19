package org.dummy.lucene.index;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * check TokenStream and TokenFilters
 */
public class Writer {

    public void writeIndex(String dataFolder, String indexFolder){
        // analyzer is for stop words, white spaces, stemming, etc...
        try (Directory directory = FSDirectory.open(Paths.get(indexFolder));
            Analyzer analyzer = new StandardAnalyzer()) {

            IndexWriterConfig indexConfig = new IndexWriterConfig(analyzer);
            indexConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND); // reuse index if existing
            IndexWriter indexWriter = new IndexWriter(directory, indexConfig);
            indexPath(indexWriter, Paths.get(dataFolder));
            indexWriter.close(); // TODO: wrap in try with resources

        } catch (IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    public void deleteDocument(String dataFolder, String indexFolder) {
        try (Directory directory = FSDirectory.open(Paths.get(indexFolder));
            Analyzer analyzer = new StandardAnalyzer()) {

            IndexWriterConfig indexConfig = new IndexWriterConfig(analyzer);
            IndexWriter indexWriter = new IndexWriter(directory, indexConfig);
           // indexWriter.deleteDocuments(new Term("path", filePath.toString())); //todo: the walk tree and delete
            indexWriter.close(); // TODO: wrap in try with resources

        } catch (IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    private void indexPath(IndexWriter indexWriter, Path path) throws IOException {
        if(Files.isDirectory(path)){
            Files.walkFileTree(path, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    indexFile(indexWriter, file, attrs.lastModifiedTime().toMillis());
                    return FileVisitResult.CONTINUE;
                }
            });
        } else {
            indexFile(indexWriter, path, Files.getLastModifiedTime(path).toMillis());
        }
    }

    private void indexFile(IndexWriter indexWriter, Path filePath, long lastModified) throws IOException {
        Document document = new Document();
        document.add(new StringField("path", filePath.toString(), Field.Store.YES));
        document.add(new LongPoint("modified", lastModified));
        document.add(new TextField("contents", new String(Files.readAllBytes(filePath)), Field.Store.YES));
       // document.add(new TextField("title", title, Field.Store.YES)); // another field to search in

        // TODO: add indexWriter as a function.
        // indexWriter.addDocument(document); in case the mode is CREATE only.
        indexWriter.updateDocument(new Term("path", filePath.toString()), document); // if the "path" matches => override
    }

}
