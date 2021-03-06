package com.comp4601project.factory;

import java.io.File;
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

import com.comp4601project.model.Bill;
import com.comp4601project.model.MemberExpenditureReports;
import com.comp4601project.model.MemberOfParliamentList;
import com.comp4601project.model.MemberVotes;
import com.comp4601project.model.Votes;

public class FactoryTest {

	public static void main(String[] args) throws JAXBException,
			XMLStreamException {

		System.out.println(new File(".").getAbsolutePath());

		JAXBContext jaxbContext = org.eclipse.persistence.jaxb.JAXBContextFactory
				.createContext(new Class[] { Bill.class}, null);
		System.out.println(jaxbContext.getClass());
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

		StringReader reader = new StringReader(TEST_XML);

		XMLInputFactory xif = XMLInputFactory.newInstance();
		XMLStreamReader xsr = xif.createXMLStreamReader(reader);
		xsr = new MyStreamReaderDelegate(xsr);

		Bill bill = (Bill) unmarshaller.unmarshal(xsr);

		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(MarshallerProperties.MEDIA_TYPE,
				"application/json");
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.marshal(bill, System.out);

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

	public static final String TEST_XML = "<?xml version=\"1.0\" encoding=\"utf-8\"?><!--The data contained in this document is provided in a standard XML format. Once imported into a spreadsheet or database tool of your choice, the data can be filtered and sorted to create customized reports.\n"
			+ "\n"
			+ "Please refer to the instructions within the tool of your choice to find out how to import external data.--><Bill id=\"6263562\" lastUpdated=\"2015-03-24T18:07:11\"><BillIntroducedDate>2013-10-25T12:06:05</BillIntroducedDate><ParliamentSession parliamentNumber=\"41\" sessionNumber=\"2\" /><BillNumber prefix=\"C\" number=\"7\" /><BillTitle><Title language=\"en\">An Act to amend the Museums Act in order to establish the Canadian Museum of History and to make consequential amendments to other Acts</Title><Title language=\"fr\">Loi modifiant la Loi sur les musées afin de constituer le Musée canadien de l'histoire et apportant des modifications corrélatives à d'autres lois</Title></BillTitle><ShortTitle><Title language=\"en\">Canadian Museum of History Act</Title><Title language=\"fr\">Loi sur le Musée canadien de l'histoire</Title></ShortTitle><BillType><Title language=\"en\">House Government Bill</Title><Title language=\"fr\">Projet de loi émanant du gouvernement (Cdc)</Title></BillType><SponsorAffiliation id=\"194612\"><Title language=\"en\">Minister of Canadian Heritage and Official Languages</Title><Title language=\"fr\">ministre du Patrimoine canadien et des Langues officielles</Title><Person id=\"59115\" Gender=\"F\"><FullName>Shelly Glover</FullName><FirstName>Shelly</FirstName><MiddleName /><LastName>Glover</LastName></Person><PoliticalParty><Title language=\"en\">Conservative</Title><Title language=\"fr\">Conservateur</Title><abbreviation><Title language=\"en\">CPC</Title><Title language=\"fr\">PCC</Title></abbreviation></PoliticalParty></SponsorAffiliation><ComingIntoForce><Information><Title language=\"en\">&lt;div&gt;This bill comes into force when it receives Royal Assent.&lt;/div&gt;</Title><Title language=\"fr\">&lt;div&gt;La loi entre en vigueur le jour de sa sanction royale.&lt;/div&gt;</Title></Information></ComingIntoForce><LegisInfoNotes><Title language=\"en\">&lt;div&gt;On October 25, 2013, pursuant to Order made by the House of Commons on October 21, 2013, the Bill was deemed approved at all stages completed in the previous session (previously Bill C-49 in the 1st Session of the 41st Parliament).&lt;/div&gt;</Title><Title language=\"fr\">&lt;div&gt;Le 25 octobre 2013, conformément à l'ordre adopté par la Chambre des communes le 21 octobre 2013, le projet de loi est réputé approuvé à toutes les étapes franchies lors de la session précédente (ancien projet de loi C-49 de la 1re session de la 41e législature).&lt;/div&gt;</Title></LegisInfoNotes><PrimeMinister id=\"78738\"><Title language=\"en\">Prime Minister</Title><Title language=\"fr\">premier ministre</Title><Person id=\"1733\" Gender=\"M\"><FullName>Stephen Harper</FullName><FirstName>Stephen</FirstName><MiddleName /><LastName>Harper</LastName></Person><PoliticalParty><Title language=\"en\">Conservative</Title><Title language=\"fr\">Conservateur</Title><abbreviation><Title language=\"en\">CPC</Title><Title language=\"fr\">PCC</Title></abbreviation></PoliticalParty></PrimeMinister><Statute><Year>2013</Year><Chapter>38</Chapter></Statute><Publications><Publication id=\"6268614\"><Title language=\"en\">First Reading</Title><Title language=\"fr\">Première lecture</Title><PublicationFiles><PublicationFile language=\"en\" relativePath=\"/HousePublications/Publication.aspx?Mode=1&amp;DocId=6268614&amp;Language=E\" /><PublicationFile language=\"fr\" relativePath=\"/HousePublications/Publication.aspx?Mode=1&amp;DocId=6268614&amp;Language=F\" /></PublicationFiles></Publication><Publication id=\"6290012\"><Title language=\"en\">As passed by the House of Commons</Title><Title language=\"fr\">Tel qu’adopté par la Chambre des communes</Title><PublicationFiles><PublicationFile language=\"en\" relativePath=\"/HousePublications/Publication.aspx?Mode=1&amp;DocId=6290012&amp;Language=E\" /><PublicationFile language=\"fr\" relativePath=\"/HousePublications/Publication.aspx?Mode=1&amp;DocId=6290012&amp;Language=F\" /></PublicationFiles></Publication><Publication id=\"6388213\"><Title language=\"en\">Royal Assent</Title><Title language=\"fr\">Sanction royale</Title><PublicationFiles><PublicationFile language=\"en\" relativePath=\"/HousePublications/Publication.aspx?Mode=1&amp;DocId=6388213&amp;Language=E\" /><PublicationFile language=\"fr\" relativePath=\"/HousePublications/Publication.aspx?Mode=1&amp;DocId=6388213&amp;Language=F\" /></PublicationFiles></Publication></Publications><Events laagCurrentStage=\"RoyalAssentGiven\"><LastMajorStageEvent><Event id=\"8195427\" chamber=\"SEN\" date=\"2013-12-12\" meetingNumber=\"27\"><Status><Title language=\"en\">Royal Assent</Title><Title language=\"fr\">Sanction royale</Title></Status><Description /></Event><Progress>CRA</Progress></LastMajorStageEvent><LegislativeEvents><Event id=\"8108302\" chamber=\"HOC\" date=\"2013-10-25\" meetingNumber=\"8\"><Status><Title language=\"en\">Introduction and First Reading</Title><Title language=\"fr\">Dépôt et première lecture</Title></Status><Description /></Event><Event id=\"8108487\" chamber=\"HOC\" date=\"2013-10-25\" meetingNumber=\"8\"><Committee id=\"20172\" senateCommitteeId=\"0\" accronym=\"CHPC\"><Title language=\"en\">Standing Committee on Canadian Heritage</Title><Title language=\"fr\">Comité permanent du patrimoine canadien</Title><CommitteeMeetings /></Committee><Status><Title language=\"en\">Second Reading and Referral to Committee</Title><Title language=\"fr\">Deuxième lecture et renvoi à un comité</Title></Status><Description /></Event><Event id=\"8109643\" chamber=\"HOC\" date=\"2013-10-25\" meetingNumber=\"8\"><Status><Title language=\"en\">Committee Report Presented</Title><Title language=\"fr\">Présentation du rapport du comité</Title></Status><Description /></Event><Event id=\"8108489\" chamber=\"HOC\" date=\"2013-10-25\" meetingNumber=\"8\"><Status><Title language=\"en\">Concurrence at Report Stage</Title><Title language=\"fr\">Adoption à l’étape du rapport</Title></Status><Description /></Event><Event id=\"8115380\" chamber=\"HOC\" date=\"2013-10-30\" meetingNumber=\"11\"><Status><Title language=\"en\">Debate at 3rd Reading</Title><Title language=\"fr\">Débat à l’étape de la troisième lecture</Title></Status><Description /></Event><Event id=\"8125724\" chamber=\"HOC\" date=\"2013-11-06\" meetingNumber=\"15\"><Status><Title language=\"en\">Debate at 3rd Reading</Title><Title language=\"fr\">Débat à l’étape de la troisième lecture</Title></Status><Description /></Event><Event id=\"8125725\" chamber=\"HOC\" date=\"2013-11-06\" meetingNumber=\"15\"><Status><Title language=\"en\">Third Reading</Title><Title language=\"fr\">Troisième lecture</Title></Status><Description /></Event><Event id=\"8130280\" chamber=\"SEN\" date=\"2013-11-07\" meetingNumber=\"14\"><Status><Title language=\"en\">First Reading</Title><Title language=\"fr\">Première lecture</Title></Status><Description /></Event><Event id=\"8160978\" chamber=\"SEN\" date=\"2013-11-27\" meetingNumber=\"19\"><Status><Title language=\"en\">Debate at Second Reading</Title><Title language=\"fr\">Débat à l’étape de la deuxième lecture</Title></Status><Description /></Event><Event id=\"8180621\" chamber=\"SEN\" date=\"2013-12-04\" meetingNumber=\"22\"><Status><Title language=\"en\">Debate at Second Reading</Title><Title language=\"fr\">Débat à l’étape de la deuxième lecture</Title></Status><Description /></Event><Event id=\"8180847\" chamber=\"SEN\" date=\"2013-12-04\" meetingNumber=\"22\"><Status><Title language=\"en\">Second Reading</Title><Title language=\"fr\">Deuxième lecture</Title></Status><Description /></Event><Event id=\"8180855\" chamber=\"SEN\" date=\"2013-12-04\" meetingNumber=\"22\"><Committee id=\"16104\" senateCommitteeId=\"1047\" accronym=\"SOCI\"><Title language=\"en\">Standing Senate Committee on Social Affairs, Science and Technology</Title><Title language=\"fr\">Comité sénatorial permanent des affaires sociales, des sciences et de la technologie</Title><CommitteeMeetings><CommitteeMeeting number=\"6\" meetingDateTime=\"2013-12-04T16:13:00\" meetingDate=\"2013-12-04\" meetingTime=\"16:13\" studyActivityId=\"0\" /><CommitteeMeeting number=\"7\" meetingDateTime=\"2013-12-05T10:31:00\" meetingDate=\"2013-12-05\" meetingTime=\"10:31\" studyActivityId=\"0\" /></CommitteeMeetings></Committee><Status><Title language=\"en\">Referral to Committee</Title><Title language=\"fr\">Renvoi à un comité</Title></Status><Description /></Event><Event id=\"8184738\" chamber=\"SEN\" date=\"2013-12-05\" meetingNumber=\"23\"><Status><Title language=\"en\">Committee Report Presented</Title><Title language=\"fr\">Présentation du rapport du comité</Title></Status><Description /></Event><Event id=\"8194015\" chamber=\"SEN\" date=\"2013-12-10\" meetingNumber=\"25\"><Status><Title language=\"en\">Debate at 3rd Reading</Title><Title language=\"fr\">Débat à l’étape de la troisième lecture</Title></Status><Description /></Event><Event id=\"8195216\" chamber=\"SEN\" date=\"2013-12-11\" meetingNumber=\"26\"><Status><Title language=\"en\">Debate at 3rd Reading</Title><Title language=\"fr\">Débat à l’étape de la troisième lecture</Title></Status><Description /></Event><Event id=\"8195217\" chamber=\"SEN\" date=\"2013-12-11\" meetingNumber=\"26\"><Status><Title language=\"en\">Third Reading</Title><Title language=\"fr\">Troisième lecture</Title></Status><Description /></Event><Event id=\"8195427\" chamber=\"SEN\" date=\"2013-12-12\" meetingNumber=\"27\"><Status><Title language=\"en\">Royal Assent</Title><Title language=\"fr\">Sanction royale</Title></Status><Description /></Event></LegislativeEvents></Events></Bill>";

}
