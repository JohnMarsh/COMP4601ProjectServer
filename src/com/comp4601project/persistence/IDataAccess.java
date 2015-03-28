package com.comp4601project.persistence;

import java.util.List;

import com.comp4601project.model.Bill;
import com.comp4601project.model.Bills;
import com.comp4601project.model.MemberExpenditureReports;
import com.comp4601project.model.MemberOfParliamentList;
import com.comp4601project.model.MemberOfParliamentList.MemberOfParliament;
import com.comp4601project.model.Votes;
import com.comp4601project.model.Votes.Vote;
import com.mongodb.DBObject;

public interface IDataAccess {
	
	public void saveBillList(Bills bills);
	public void saveBill(Bill bill);
	public DBObject getBillWithId(Integer id);
	
	public void saveMPList(MemberOfParliamentList mpList);
	public void saveMP(MemberOfParliamentList.MemberOfParliament mpList);
	
	
	public String getMPListJSON();
	public List<DBObject> getMPList();
	public String getMPJSON(String fname, String lname);
	
	public DBObject getMP(String fname, String lname);
	
	public void saveVoteList(Votes votes);
	public void saveVote(Vote vote);
	public DBObject getVote(Integer number, Integer parliament, Integer session);
	
	public String getVoteListJSON(String parliament, String session);
	public String getVoteJSON(String number, String parliament, String session) ;
	
	public void saveExpReport(MemberExpenditureReports report);
	
	public String getExpReportJSONForMP(String fname, String lname);
	

}
