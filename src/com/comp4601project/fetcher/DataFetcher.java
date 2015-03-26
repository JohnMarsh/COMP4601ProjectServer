package com.comp4601project.fetcher;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

import com.comp4601project.model.ModelObjectFactory;

public class DataFetcher {

	public static void main(String args[]) throws IOException, JAXBException,
			XMLStreamException {
		fetchMPs();
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
		ModelObjectFactory.createMPListFromXML(xml.replaceAll("[^\\x20-\\x7e]", ""));
	}

}
