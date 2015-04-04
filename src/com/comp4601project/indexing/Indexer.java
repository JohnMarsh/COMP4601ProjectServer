package com.comp4601project.indexing;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.comp4601project.fetcher.DataFetcher;
import com.comp4601project.model.Bill;
import com.comp4601project.model.Bills;
import com.comp4601project.model.MemberOfParliamentList;
import com.comp4601project.model.MemberOfParliamentList.MemberOfParliament;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import org.apache.lucene.document.Field;
import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

public class Indexer {

	private IndexWriter mpWriter;
	private IndexWriter billWriter;

	public static final String INDEX_MP_DIR = "/data/index/mp";
	public static final String INDEX_BILL_DIR = "/data/index/bill";

	private static Indexer instance = null;

	public static synchronized Indexer getInstance() {
		if (instance == null) {
			instance = new Indexer();
		}
		return instance;
	}
	
	public static void main(String[] args){
		Indexer.getInstance().deleteBillIndex();
	}
	
	public void deleteMPIndex() {
		FSDirectory mpdir = null;
		try {
			mpdir = FSDirectory.open(new File(INDEX_MP_DIR));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Analyzer analyzer = new StandardAnalyzer();
		IndexWriterConfig mpwc = new IndexWriterConfig(Version.LUCENE_4_10_3,
				analyzer);
		try {
			mpWriter = new IndexWriter(mpdir, mpwc);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			mpWriter.deleteAll();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			mpWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void deleteBillIndex(){
		FSDirectory billdir = null;
		try {

			billdir = FSDirectory.open(new File(INDEX_BILL_DIR));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Analyzer analyzer = new StandardAnalyzer();
		IndexWriterConfig billwc = new IndexWriterConfig(Version.LUCENE_4_10_3,
				analyzer);
		billWriter = null;
		try {
			billWriter = new IndexWriter(billdir, billwc);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			billWriter.deleteAll();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			billWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void indexBillList(Bills bills) throws IOException {
		FSDirectory billdir = null;
		try {

			billdir = FSDirectory.open(new File(INDEX_BILL_DIR));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Analyzer analyzer = new StandardAnalyzer();
		IndexWriterConfig billwc = new IndexWriterConfig(Version.LUCENE_4_10_3,
				analyzer);
		billWriter = null;
		try {
			billWriter = new IndexWriter(billdir, billwc);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (Bill bill : bills.getBill()) {
			indexABill(bill);
		}

		billWriter.close();

	}

	public void indexMPList(MemberOfParliamentList mplist) throws IOException {
		FSDirectory mpdir = null;
		try {
			mpdir = FSDirectory.open(new File(INDEX_MP_DIR));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Analyzer analyzer = new StandardAnalyzer();
		IndexWriterConfig mpwc = new IndexWriterConfig(Version.LUCENE_4_10_3,
				analyzer);
		try {
			mpWriter = new IndexWriter(mpdir, mpwc);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (MemberOfParliament mp : mplist.getMemberOfParliament()) {
			indexAnMP(mp);
		}

		mpWriter.close();
	}

	private void indexAnMP(MemberOfParliament mp) throws IOException {
		Document doc = new Document();
		doc.add(new TextField("FIRST_NAME", mp.getPersonOfficialFirstName(),
				Field.Store.YES));
		doc.add(new TextField("LAST_NAME", mp.getPersonOfficialLastName(),
				Field.Store.YES));
		doc.add(new TextField("PARTY", mp.getCaucusShortName(), Field.Store.YES));
		doc.add(new TextField("RIDING", mp.getConstituencyName(),
				Field.Store.YES));
		mpWriter.addDocument(doc);
	}

	private void indexABill(Bill bill) throws IOException {
		Document doc = new Document();
		doc.add(new TextField("BILL_NUMBER", bill.getBillNumber().getPrefix()
				+ bill.getBillNumber().getNumber(), Field.Store.YES));
		doc.add(new TextField("BILL_NAME_ENGLISH", bill.getEnglishTitle(),
				Field.Store.YES));
		doc.add(new TextField("BILL_NAME_FRENCH", bill.getFrenchTitle(),
				Field.Store.YES));
		try {
			doc.add(new TextField("BILL_TEXT", DataFetcher
					.getLatestBillText(bill), Field.Store.YES));
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TikaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		doc.add(new IntField("ID", bill.getId(), Field.Store.YES));
		billWriter.addDocument(doc);
	}

}
