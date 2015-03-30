package com.comp4601project.searching;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.comp4601project.indexing.Indexer;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class Searcher {

	private MongoClient client;
	private DB db;
	private DBCollection docCollection;

	public Searcher() {

	}

	public static ArrayList<String> queryMp(String searchString) {
		try {
			IndexReader reader = DirectoryReader.open(FSDirectory
					.open(new File(Indexer.INDEX_MP_DIR)));
			IndexSearcher searcher = new IndexSearcher(reader);
			Analyzer analyzer = new StandardAnalyzer();
			MultiFieldQueryParser parser = new MultiFieldQueryParser(
					new String[] { "FIRST_NAME", "LAST_NAME", "PARTY", "RIDING" },
					analyzer);
			Query q = parser.parse(searchString);
			TopDocs results = searcher.search(q, 1000);
			ScoreDoc[] hits = results.scoreDocs;
			ArrayList<String> mpNames = new ArrayList<String>();

			for (ScoreDoc hit : hits) {
				Document doc = searcher.doc(hit.doc);
				String fname = doc.get("FIRST_NAME");
				String lname = doc.get("LAST_NAME");
				mpNames.add(fname + "-" + lname);
			}
			reader.close();

			return mpNames;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static ArrayList<Integer> queryBill(String searchString) {
		try {
			IndexReader reader = DirectoryReader.open(FSDirectory
					.open(new File(Indexer.INDEX_BILL_DIR)));
			IndexSearcher searcher = new IndexSearcher(reader);
			Analyzer analyzer = new StandardAnalyzer();
			MultiFieldQueryParser parser = new MultiFieldQueryParser(
					new String[] { "BILL_NUMBER", "BILL_NAME_ENGLISH",
							"BILL_NAME_FRENCH", "BILL_TEXT" }, analyzer);
			Query q = parser.parse(searchString);
			TopDocs results = searcher.search(q, 50);
			ScoreDoc[] hits = results.scoreDocs;
			ArrayList<Integer> billIds = new ArrayList<Integer>();

			for (ScoreDoc hit : hits) {
				Document doc = searcher.doc(hit.doc);
				Integer id = Integer.parseInt(doc.get("ID"));
				billIds.add(id);
			}
			reader.close();

			return billIds;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
