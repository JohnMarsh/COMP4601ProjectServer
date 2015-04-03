package com.comp4601project.persistence;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

import com.comp4601project.fetcher.DataFetcher;
import com.comp4601project.indexing.Indexer;
import com.comp4601project.model.Bill;
import com.comp4601project.model.Bills;
import com.comp4601project.model.MemberExpenditureReports;
import com.comp4601project.model.MemberExpenditureReports.Report;
import com.comp4601project.model.MemberOfParliamentList;
import com.comp4601project.model.MemberOfParliamentList.MemberOfParliament;
import com.comp4601project.model.ModelObjectFactory;
import com.comp4601project.model.Votes;
import com.comp4601project.model.Votes.Vote;
import com.comp4601project.searching.Searcher;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.util.JSON;

public class DataAccess implements IDataAccess {

	private static final String DB_NAME = "parliament";
	private static final String MP_COLLECTION = "mp";
	private static final String BILL_COLLECTION = "bill";
	private static final String VOTE_COLLECTION = "vote";
	private static final String EXP_COLLECTION = "exp";

	private MongoClient mongoClient;
	private DB db;

	private static DataAccess instance = null;

	public static synchronized DataAccess getInstance() {
		if (instance == null) {
			instance = new DataAccess();
		}
		return instance;
	}

	public DataAccess() {
		try {
			mongoClient = new MongoClient("localhost");
			db = mongoClient.getDB(DB_NAME);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void saveMPList(MemberOfParliamentList mpList) {
		for (MemberOfParliament mp : mpList.getMemberOfParliament()) {
			saveMP(mp);
		}
		try {
			Indexer.getInstance().indexMPList(mpList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void saveMP(MemberOfParliament mp) {
		try {
			DBObject existingMp = getMP(mp.getPersonOfficialFirstName(),
					mp.getPersonOfficialLastName());

			DBObject updatedMp = (DBObject) JSON.parse(ModelObjectFactory
					.toJSON(mp));

			if (existingMp == null) {

				db.getCollection(MP_COLLECTION).insert(updatedMp);
			} else {
				db.getCollection(MP_COLLECTION).update(existingMp, updatedMp);
			}

		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String getMPListJSON() {
		List<DBObject> mpList = getMPList();
		return JSON.serialize(mpList);
	}

	@Override
	public String getMPJSON(String fname, String lname) {
		DBObject mp = getMP(fname, lname);
		return JSON.serialize(mp);
	}

	@Override
	public void saveVoteList(Votes votes) {
		for (Vote vote : votes.getVote()) {
			saveVote(vote);
		}
	}

	@Override
	public void saveVote(Vote vote) {
		try {
			DBObject existingVote = getVote(vote.getNumber(),
					vote.getParliament(), vote.getSession());

			System.out.println(ModelObjectFactory.toJSON(vote));

			DBObject updateVote = (DBObject) JSON.parse(ModelObjectFactory
					.toJSON(vote));

			if (existingVote == null) {

				db.getCollection(VOTE_COLLECTION).insert(updateVote);
			} else {
				db.getCollection(VOTE_COLLECTION).update(existingVote,
						updateVote);
			}

		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public DBObject getVote(Integer number, Integer parliament, Integer session) {
		BasicDBObject query = new BasicDBObject("number", number);
		query.put("parliament", parliament);
		query.put("session", session);

		DBCursor cursor = db.getCollection(VOTE_COLLECTION).find(query);

		if (cursor.size() == 0) {
			return null;
		} else {
			return cursor.next();
		}
	}

	@Override
	public String getVoteListJSON(String parliament, String session) {
		List<DBObject> votes = new ArrayList<DBObject>();
		BasicDBObject query = new BasicDBObject();
		query.put("parliament", Integer.parseInt(parliament));
		query.put("session", Integer.parseInt(session));

		DBCursor cursor = db.getCollection(VOTE_COLLECTION).find(query);

		if (cursor.size() == 0) {
			try {
				DataFetcher.fetchVoteLists(parliament, session);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (XMLStreamException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			db.getCollection(VOTE_COLLECTION).find(query);
		}

		while (cursor.hasNext()) {
			votes.add(cursor.next());
		}
		return JSON.serialize(votes);
	}

	@Override
	public String getVoteJSON(String number, String parliament, String session) {
		DBObject vote = getVote(Integer.parseInt(number),
				Integer.parseInt(parliament), Integer.parseInt(session));
		return JSON.serialize(vote);
	}

	@Override
	public void saveExpReportList(MemberExpenditureReports reports) {
		for (Report report : reports.getReport()) {
			saveExpReport(report);
		}
	}

	@Override
	public void saveExpReport(Report report) {
		try {
			DBObject existingReport = getExpReportForMP(report.getMember()
					.getFirstName(), report.getMember().getLastName());

			DBObject updatedReport = (DBObject) JSON.parse(ModelObjectFactory
					.toJSON(report));

			if (existingReport == null) {

				db.getCollection(EXP_COLLECTION).insert(updatedReport);
			} else {
				db.getCollection(EXP_COLLECTION).update(existingReport,
						updatedReport);
			}

		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String getExpReportJSONForMP(String fname, String lname) {
		DBObject exp = getExpReportForMP(fname, lname);
		return JSON.serialize(exp);
	}

	@Override
	public DBObject getExpReportForMP(String fname, String lname) {
		BasicDBObject mp =  new BasicDBObject("firstName", fname); 
		mp.put("lastName", lname);
		BasicDBObject report =  new BasicDBObject("member", mp); 

		DBCursor cursor = db.getCollection(EXP_COLLECTION).find(report);

		if (cursor.size() == 0) {
			return null;
		} else {
			return cursor.next();
		}
	}

	@Override
	public DBObject getMP(String fname, String lname) {
		BasicDBObject query = new BasicDBObject("personOfficialFirstName",
				fname);
		query.put("personOfficialLastName", lname);

		DBCursor cursor = db.getCollection(MP_COLLECTION).find(query);

		if (cursor.size() == 0) {
			return null;
		} else {
			return cursor.next();
		}
	}

	@Override
	public void saveBill(Bill bill) {
		try {
			DBObject existingBill = getBillWithId(bill.getId());

			DBObject updatedBill = (DBObject) JSON.parse(ModelObjectFactory
					.toJSON(bill));

			if (existingBill == null) {

				db.getCollection(BILL_COLLECTION).insert(updatedBill);
			} else {
				db.getCollection(BILL_COLLECTION).update(existingBill,
						updatedBill);
			}

		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public DBObject getBillWithId(Integer id) {
		BasicDBObject query = new BasicDBObject("id", id);

		DBCursor cursor = db.getCollection(BILL_COLLECTION).find(query);

		if (cursor.size() == 0) {
			return null;
		} else {
			return cursor.next();
		}
	}

	@Override
	public void saveBillList(Bills bills) {
		for (Bill bill : bills.getBill()) {
			saveBill(bill);
		}

		DBCursor cursorDoc = db.getCollection(BILL_COLLECTION).find();
		while (cursorDoc.hasNext()) {
			System.out.println(cursorDoc.next());
		}

		try {
			Indexer.getInstance().indexBillList(bills);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(cursorDoc.size());
	}

	@Override
	public List<DBObject> getMPList() {
		List<DBObject> mps = new ArrayList<DBObject>();
		DBCursor cursorDoc = db.getCollection(MP_COLLECTION).find();
		while (cursorDoc.hasNext()) {
			mps.add(cursorDoc.next());
		}
		return mps;
	}

	@Override
	public String getBillJSON(Integer id) {
		DBObject bill = getBillWithId(id);
		return JSON.serialize(bill);
	}

	@Override
	public String getBillListJSON(Integer parliament, Integer session) {
		List<DBObject> bills = new ArrayList<DBObject>();
		DBObject query = (DBObject) JSON.parse("{'parliamentSession' : {"
				+ "		'parliamentNumber' : " + parliament + ","
				+ "		'sessionNumber' : " + session + "}}");

		DBCursor cursor = db.getCollection(BILL_COLLECTION).find(query);

		if (cursor.size() == 0) {
			try {
				DataFetcher.fetchBills(parliament, session);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (XMLStreamException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			db.getCollection(BILL_COLLECTION).find(query);
		}

		while (cursor.hasNext()) {
			bills.add(cursor.next());
		}
		return JSON.serialize(bills);
	}

	public String getBillListJSON(Integer parliament) {
		// TODO Auto-generated method stub
		return "Not implemented yet";
	}

	@Override
	public String searchMPandGetJSON(String query) {
		List<String> mpNames = Searcher.queryMp(query);
		List<DBObject> mpList = new ArrayList<DBObject>();
		for (String name : mpNames) {
			String[] names = name.split("-");
			mpList.add(getMP(names[0], names[1]));
		}
		return JSON.serialize(mpList);
	}

	@Override
	public String searchBillandGetJSON(String query) {
		List<Integer> billIds = Searcher.queryBill(query);
		List<DBObject> billList = new ArrayList<DBObject>();
		for (Integer id : billIds) {
			billList.add(getBillWithId(id));
		}
		return JSON.serialize(billList);
	}

	public static void main(String args[]) {
		System.out
				.println(DataAccess.getInstance().searchMPandGetJSON("david"));
	}

	public String getRecentBills(Integer limit) {
		List<DBObject> bills = new ArrayList<DBObject>();

		DBCursor cursor = db.getCollection(BILL_COLLECTION).find();

		cursor.sort(new BasicDBObject("lastUpdated", -1));
		
		cursor.limit(limit);

		while (cursor.hasNext()) {
			bills.add(cursor.next());
		}
		return JSON.serialize(bills);
	}
}
