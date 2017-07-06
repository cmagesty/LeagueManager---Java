package com.teamtreehouse.model;
import java.util.List;

public class Team{
	private String teamName;
	private String coach;
	private List<Player> players;
	
	public Team(String teamName,String coach){
			this.TeamName = teamName;
			this.coach = coach;	
	}
	
	public int getPlayerCount(){
		return players.size();
	}
	
	public String getTeamName(){
		return this.teamName;
	}
	
	public String getCoachName(){
		return this.coach;
	}
}