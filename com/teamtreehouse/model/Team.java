package com.teamtreehouse.model;

public class Team {
  private String team;
  private String coach;
  
  public Team(String team, String coach) {
    this.team = team;
    this.coach = coach;
 
  }
  public String getTeam() {
    return this.team;
  }
  public String getCoach() {
    return this.coach;
  }
}