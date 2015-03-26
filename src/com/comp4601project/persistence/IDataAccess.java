package com.comp4601project.persistence;

import com.comp4601project.model.MemberExpenditureReports;
import com.comp4601project.model.MemberOfParliamentList;
import com.comp4601project.model.Votes;
import com.comp4601project.model.Votes.Vote;

public interface IDataAccess {
	
	public void saveMPList(MemberOfParliamentList mpList);
	public void saveMP(MemberOfParliamentList mpList);
	
	public String getMPListJSON();
	public String getMPJSON();
	
	public void saveVoteList(Votes votes);
	public void saveVote(Vote vote);
	
	public String getVoteListJSON();
	public String getVoteJSON();
	
	public void saveExpReport(MemberExpenditureReports report);
	
	public String getExpReportJSONForMP();
	

}
