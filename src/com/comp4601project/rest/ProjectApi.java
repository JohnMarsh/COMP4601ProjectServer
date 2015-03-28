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
	@Path("/mp/{name}")
	public String getMp(@PathParam("name") String name) {
		if (name == null || !name.contains("-") || name.isEmpty()) {
			return "Inavlid parameter";
		}
		String[] names = name.split("-");
		return DataAccess.getInstance().getMPJSON(names[0], names[1]);
	}

	@GET
	@Path("/mpList")
	public String getMpList() {
		return DataAccess.getInstance().getMPListJSON();
	}

	@GET
	@Path("/voteList/{parliament}/{session}")
	public String getVoteList(@PathParam("parliament") String parliament,
			@PathParam("session") String session) {
		return DataAccess.getInstance().getVoteListJSON(parliament, session);
	}

}
