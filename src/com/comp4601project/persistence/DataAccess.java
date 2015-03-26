package com.comp4601project.persistence;

import java.net.UnknownHostException;

import com.comp4601project.model.MemberExpenditureReports;
import com.comp4601project.model.MemberOfParliamentList;
import com.comp4601project.model.Votes;
import com.comp4601project.model.Votes.Vote;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class DataAccess implements IDataAccess {

	private static final String DB_NAME = "parliament";
	private static final String MP_COLLECTION = "mp";
	private static final String BILL_COLLECTION = "bill";
	private static final String VOTE_COLLECTION = "vote";
	private static final String EXP_COLLECTION = "exp";
	
	private MongoClient mongoClient;

	private static DataAccess instance = null;

	public synchronized DataAccess getInstance() {
		if (instance == null) {
			instance = new DataAccess();
		}
		return instance;
	}
	
	public DataAccess() {
		try {
			mongoClient = new MongoClient("localhost");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

	@Override
	public void saveMPList(MemberOfParliamentList mpList) {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveMP(MemberOfParliamentList mpList) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getMPListJSON() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMPJSON() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveVoteList(Votes votes) {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveVote(Vote vote) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getVoteListJSON() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getVoteJSON() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveExpReport(MemberExpenditureReports report) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getExpReportJSONForMP() {
		// TODO Auto-generated method stub
		return null;
	}

}
