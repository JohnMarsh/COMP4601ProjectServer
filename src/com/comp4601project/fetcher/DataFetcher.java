package com.comp4601project.fetcher;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import com.comp4601project.model.Bill;
import com.comp4601project.model.Bills;
import com.comp4601project.model.MemberExpenditureReports;
import com.comp4601project.model.MemberOfParliamentList;
import com.comp4601project.model.ModelObjectFactory;
import com.comp4601project.model.Votes;
import com.comp4601project.persistence.DataAccess;

public class DataFetcher {

	public static void main(String args[]) throws IOException, JAXBException,
			XMLStreamException {
		fetchMPs();
		fetchBills(41,2);
		fetchExpenseReports();
	}

	static String convertStreamToString(java.io.InputStream is) {
		java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";
	}
	

	public static void fetchMPs() throws IOException, JAXBException,
			XMLStreamException {
		String uri = "http://www.parl.gc.ca/Parliamentarians/en/members/export?output=XML";

		URL url = new URL(uri);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Accept", "application/xml");

		InputStream is = connection.getInputStream();
		String xml = convertStreamToString(is);
		MemberOfParliamentList list = ModelObjectFactory
				.createMPListFromXML(xml.replaceAll("[^\\x20-\\x7e]", ""));
		DataAccess.getInstance().saveMPList(list);
	}

	public static void fetchBillWithId(String id) throws IOException,
			JAXBException, XMLStreamException {
		String uri = "http://www.parl.gc.ca/LEGISInfo/BillDetails.aspx?Language=E&Mode=1&billId="
				+ id + "&download=xml";
		URL url = new URL(uri);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Accept", "application/xml");

		InputStream is = connection.getInputStream();
		String xml = convertStreamToString(is);
		Bill bill = ModelObjectFactory.createBillFromXML((xml.replaceAll(
				"[^\\x20-\\x7e]", "")));
		DataAccess.getInstance().saveBill(bill);

	}

	public static void fetchBills(Integer parliament, Integer session)
			throws IOException, JAXBException, XMLStreamException {
		String uri = "http://www.parl.gc.ca/LEGISInfo/Home.aspx?Language=E&Mode=1&ParliamentSession="
				+ parliament + "-" + session + "&download=xml";
		URL url = new URL(uri);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Accept", "application/xml");

		InputStream is = connection.getInputStream();
		String xml = convertStreamToString(is);
		Bills bills = ModelObjectFactory.createBillsFromXML((xml.replaceAll(
				"[^\\x20-\\x7e]", "")));

		DataAccess.getInstance().saveBillList(bills);

	}

	public static void fetchVoteLists(String parliament, String session)
			throws IOException, JAXBException, XMLStreamException {
		String uri = "http://www.parl.gc.ca/housechamberbusiness/Chambervotelist.aspx?Language=E&Mode=1&Parl="
				+ parliament
				+ "&Ses="
				+ session
				+ "&FltrParl="
				+ parliament
				+ "&FltrSes="
				+ session
				+ "&VoteType=0&AgreedTo=True&Negatived=True&Tie=True&Page=1&xml=True&SchemaVersion=1.0";
		URL url = new URL(uri);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Accept", "application/xml");

		InputStream is = connection.getInputStream();
		String xml = convertStreamToString(is);
		Votes votes = ModelObjectFactory.createVoteListFromXML((xml.replaceAll(
				"[^\\x20-\\x7e]", "")));
		DataAccess.getInstance().saveVoteList(votes);
	}

	private static void fetchExpenseReports() throws IOException,
			JAXBException, XMLStreamException {
		String uri = "http://www.parl.gc.ca/PublicDisclosure/MemberExpenditures.aspx?Id=MER2015Q3&Language=E&FormatType=XML";
		URL url = new URL(uri);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");

		InputStream is = connection.getInputStream();
		String xml = convertStreamToString(is);
		MemberExpenditureReports reports = ModelObjectFactory
				.createReportFromXML(xml);
		DataAccess.getInstance().saveExpReportList(reports);
	}

	public static String getLatestBillText(Bill bill) throws IOException,
			SAXException, TikaException {
		int parliament = bill.getParliamentSession().getParliamentNumber();
		int session = bill.getParliamentSession().getSessionNumber();
		String billNumber = bill.getBillNumber().getPrefix() + "-"
				+ bill.getBillNumber().getNumber();
		int latest = bill.getPublications().getPublication().size();
		String billNumberAndPubNumber = billNumber + "_" + latest;
		String uri = "http://www.parl.gc.ca/content/hoc/Bills/" + parliament
				+ "" + session + "/Government/" + billNumber + "/"
				+ billNumberAndPubNumber + "/" + billNumberAndPubNumber
				+ ".PDF";
		URL url = new URL(uri);

		InputStream input = TikaInputStream.get(url);
		org.xml.sax.ContentHandler handler = new BodyContentHandler();
		Metadata metadata = new Metadata();
		ParseContext context = new ParseContext();
		Parser parser = new AutoDetectParser();
		parser.parse(input, handler, metadata, context);

		return handler.toString();
	}

}
