package com.comp4601project.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.comp4601project.persistence.DataAccess;

@Path("/")
public class ProjectApi {

	@GET
	@Produces("application/json")
	@Path("/mp/{name}")
	public String getMp(@PathParam("name") String name) {
		if (name == null || !name.contains("-") || name.isEmpty()) {
			return "Inavlid parameter";
		}
		String[] names = name.split("-");
		return DataAccess.getInstance().getMPJSON(names[0], names[1]);
	}

	@GET
	@Produces("application/json")
	@Path("/mps")
	public String getMpList() {
		return DataAccess.getInstance().getMPListJSON();
	}

	@GET
	@Produces("application/json")
	@Path("/votes/{parliament}/{session}")
	public String getVoteList(@PathParam("parliament") String parliament,
			@PathParam("session") String session) {
		return DataAccess.getInstance().getVoteListJSON(parliament, session);
	}

	@GET
	@Produces("application/json")
	@Path("/vote/{parliament}/{session}/{number}")
	public String getVote(@PathParam("parliament") String parliament,
			@PathParam("session") String session,
			@PathParam("number") String number) {
		return DataAccess.getInstance()
				.getVoteJSON(number, parliament, session);
	}

	@GET
	@Produces("application/json")
	@Path("/bill/{id}")
	public String getBill(@PathParam("id") String id) {
		return DataAccess.getInstance().getBillJSON(Integer.parseInt(id));
	}

	@GET
	@Produces("application/json")
	@Path("/bills/{parliament}/{session}")
	public String getBills(@PathParam("parliament") String parliament,
			@PathParam("session") String session) {
		return DataAccess.getInstance().getBillListJSON(
				Integer.parseInt(parliament), Integer.parseInt(session));
	}

	@GET
	@Produces("application/json")
	@Path("/bills/{parliament}")
	public String getBills(@PathParam("parliament") String parliament) {
		return DataAccess.getInstance().getBillListJSON(
				Integer.parseInt(parliament));
	}

	@GET
	@Produces("application/json")
	@Path("/bills/recent/{limit}")
	public String getTodaysBills(@PathParam("limit") Integer limit) {
		return DataAccess.getInstance().getRecentBills(limit);
	}

	@GET
	@Produces("application/json")
	@Path("/bill/search/{query}")
	public String billSearch(@PathParam("query") String query) {
		return DataAccess.getInstance().searchBillandGetJSON(query);
	}

	@GET
	@Produces("application/json")
	@Path("/mp/search/{query}")
	public String mpSearch(@PathParam("query") String query) {
		return DataAccess.getInstance().searchMPandGetJSON(query);
	}

	@GET
	@Produces("application/json")
	@Path("/expenses/top/")
	public String getTopExpenses() {
		return null;
	}

	@GET
	@Produces("application/json")
	@Path("/expenses/{name}/")
	public String getExpensesForMP(@PathParam("name") String name) {
		if (name == null || !name.contains("-") || name.isEmpty()) {
			return "Inavlid parameter";
		}
		String[] names = name.split("-");
		return DataAccess.getInstance().getExpReportJSONForMP(names[0],
				names[1]);
	}

}
