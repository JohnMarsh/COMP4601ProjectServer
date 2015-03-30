package com.comp4601project.model;

import java.io.ByteArrayOutputStream;
import java.io.ObjectStreamClass;
import java.io.OutputStream;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.util.StreamReaderDelegate;

import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.eclipse.persistence.jaxb.UnmarshallerProperties;

import com.comp4601project.model.MemberOfParliamentList.MemberOfParliament;
import com.sun.codemodel.writer.OutputStreamCodeWriter;

public class ModelObjectFactory {

	public static String toJSON(Object o) throws JAXBException {
		JAXBContext jaxbContext = org.eclipse.persistence.jaxb.JAXBContextFactory
				.createContext(new Class[] { o.getClass() }, null);

		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(MarshallerProperties.MEDIA_TYPE,
				"application/json");
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, false);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		marshaller.marshal(o, os);
		String json = new String(os.toByteArray());
		return json;
	}

	public static Bill createBillFromXML(String xml) throws JAXBException,
			XMLStreamException {
		JAXBContext jaxbContext = org.eclipse.persistence.jaxb.JAXBContextFactory
				.createContext(new Class[] { Bill.class }, null);
		System.out.println(jaxbContext.getClass());
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

		StringReader reader = new StringReader(xml);

		XMLInputFactory xif = XMLInputFactory.newInstance();
		XMLStreamReader xsr = xif.createXMLStreamReader(reader);
		xsr = new MyStreamReaderDelegate(xsr);

		Bill bill = (Bill) unmarshaller.unmarshal(xsr);
		return bill;
	}

	public static MemberExpenditureReports createReportFromXML(String xml)
			throws JAXBException, XMLStreamException {
		JAXBContext jaxbContext = org.eclipse.persistence.jaxb.JAXBContextFactory
				.createContext(new Class[] { MemberExpenditureReports.class },
						null);
		System.out.println(jaxbContext.getClass());
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

		StringReader reader = new StringReader(xml);

		XMLInputFactory xif = XMLInputFactory.newInstance();
		XMLStreamReader xsr = xif.createXMLStreamReader(reader);
		xsr = new MyStreamReaderDelegate(xsr);

		MemberExpenditureReports report = (MemberExpenditureReports) unmarshaller
				.unmarshal(xsr);
		return report;
	}

	public static MemberOfParliamentList createMPListFromXML(String xml)
			throws JAXBException, XMLStreamException {
		JAXBContext jaxbContext = org.eclipse.persistence.jaxb.JAXBContextFactory
				.createContext(new Class[] { MemberOfParliamentList.class },
						null);
		System.out.println(jaxbContext.getClass());
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

		StringReader reader = new StringReader(xml);

		XMLInputFactory xif = XMLInputFactory.newInstance();
		XMLStreamReader xsr = xif.createXMLStreamReader(reader);
		xsr = new MyStreamReaderDelegate(xsr);

		MemberOfParliamentList list = (MemberOfParliamentList) unmarshaller
				.unmarshal(xsr);
		return list;
	}

	public static Votes createVoteListFromXML(String xml) throws JAXBException,
			XMLStreamException {
		JAXBContext jaxbContext = org.eclipse.persistence.jaxb.JAXBContextFactory
				.createContext(new Class[] { Votes.class }, null);
		System.out.println(jaxbContext.getClass());
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

		StringReader reader = new StringReader(xml);

		XMLInputFactory xif = XMLInputFactory.newInstance();
		XMLStreamReader xsr = xif.createXMLStreamReader(reader);
		xsr = new MyStreamReaderDelegate(xsr);

		Votes votes = (Votes) unmarshaller.unmarshal(xsr);
		return votes;
	}

	public static MemberVotes createMemberVoteListFromXML(String xml)
			throws JAXBException, XMLStreamException {
		JAXBContext jaxbContext = org.eclipse.persistence.jaxb.JAXBContextFactory
				.createContext(new Class[] { MemberVotes.class }, null);
		System.out.println(jaxbContext.getClass());
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

		StringReader reader = new StringReader(xml);

		XMLInputFactory xif = XMLInputFactory.newInstance();
		XMLStreamReader xsr = xif.createXMLStreamReader(reader);
		xsr = new MyStreamReaderDelegate(xsr);

		MemberVotes mvotes = (MemberVotes) unmarshaller.unmarshal(xsr);
		return mvotes;
	}

	public static MemberOfParliament getMPFromJSON(String json)
			throws JAXBException {
		JAXBContext jaxbContext = org.eclipse.persistence.jaxb.JAXBContextFactory
				.createContext(new Class[] { MemberOfParliament.class }, null);
		System.out.println(jaxbContext.getClass());
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		unmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE,
				"application/json");

		StringReader reader = new StringReader(json);

		MemberOfParliament mp = (MemberOfParliament) unmarshaller
				.unmarshal(reader);
		return mp;
	}

	private static class MyStreamReaderDelegate extends StreamReaderDelegate {

		public MyStreamReaderDelegate(XMLStreamReader xsr) {
			super(xsr);
		}

		@Override
		public String getAttributeLocalName(int index) {
			// String name = super.getAttributeLocalName(index);
			// String newName = Character.toLowerCase(name.charAt(0))
			// + name.substring(1);
			// System.out.println(newName);
			// return newName;
			return super.getAttributeLocalName(index);
		}

		@Override
		public String getLocalName() {
			String name = super.getLocalName();
			String newName = Character.toLowerCase(name.charAt(0))
					+ name.substring(1);
			System.out.println(newName);
			return newName;
		}

	}

	public static Bills createBillsFromXML(String xml) throws JAXBException,
			XMLStreamException {
		JAXBContext jaxbContext = org.eclipse.persistence.jaxb.JAXBContextFactory
				.createContext(new Class[] { Bills.class }, null);
		System.out.println(jaxbContext.getClass());
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

		StringReader reader = new StringReader(xml);

		XMLInputFactory xif = XMLInputFactory.newInstance();
		XMLStreamReader xsr = xif.createXMLStreamReader(reader);
		xsr = new MyStreamReaderDelegate(xsr);

		Bills bills = (Bills) unmarshaller.unmarshal(xsr);
		return bills;
	}

}
