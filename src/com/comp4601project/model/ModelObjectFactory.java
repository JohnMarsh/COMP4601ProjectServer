package com.comp4601project.model;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.util.StreamReaderDelegate;

public class ModelObjectFactory {

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

}
