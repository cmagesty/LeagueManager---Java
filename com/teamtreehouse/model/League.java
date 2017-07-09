package com.teamtreehouse.model;
import java.util.*;
import java.io.*;

public class League {
  private BufferedReader read;
	private Map<String,String> mMenu;
	private Map<String,String> mTeamMenu;
	private Map<String,List<Player>> teams;
	private List<Team> teamList;
	private List<Player> availablePlayers;
	private int availablePlayersCount;
  
  public League(Player[] players) {
    read = new BufferedReader(new InputStreamReader(System.in));
    
    mMenu = new HashMap<String,String>();
		mMenu.put("1","Create team");
		mMenu.put("2","Choose a team");
		mMenu.put("3","League report");
		mMenu.put("0","Quit Program");
		
		mTeamMenu = new HashMap<String,String>();
		mTeamMenu.put("1","Add player");
		mTeamMenu.put("2","Remove player");
		mTeamMenu.put("3","Print by height");
		mTeamMenu.put("4","Print team roster");
		mTeamMenu.put("0","Return to the main menu");
		
		teamList = new  ArrayList<Team>();
		teams = new TreeMap<String,List<Player>>();
		availablePlayers = new ArrayList<>(Arrays.asList(players));
		availablePlayersCount = availablePlayers.size();
  }
  
  private String promptAction(Map<String,String> menu) throws IOException{

		for(Map.Entry<String,String> options : menu.entrySet()){
			System.out.printf("%s. 	%s %n",options.getKey(),options.getValue());
		}
    
		System.out.println("--------------------");
		System.out.print("What would you like to do: ");
		String choice = read.readLine();
		return choice.trim();
		
	}
	
	public void run(){
		String choice = "";
		do{
      
			try{
				System.out.println("--------MENU--------");
				choice = promptAction(mMenu);
        System.out.println("--------------------");
        
				switch(choice){
					case "1":
						if(!isAvailablePlayers() || teamList.size() >= availablePlayersCount){
							System.out.println("The number of teams is greater than the number of available players!");
							
						}else {
							Team team = newTeam();
							addTeam(team);
							System.out.printf("Team %s was added %n", team.getTeamName());
						}
						break;
          
					case "2":
						String teamChoice = promptTeam();
						if (!teamChoice.equals("")){
								runPlayers(teamChoice);
						}
						break;
          
					case "3":
						if (!teams.isEmpty()){
									leagueBalanceReport();
						}else{
							System.out.printf("League is Empty %n");
						} 
						break;
          
					case "0":
            System.exit(0);
						break;
          
					default:
						System.out.printf("Invalid choice %n");
						
				}
			}catch(IOException ioe){
				System.out.println("invalid input");
				ioe.printStackTrace();
			}
		}while (!choice.equals("0"));
	}
	
	private void addTeam(Team team){
		this.teamList.add(team);
		String teamName = team.getTeamName();
		List<Player> players = new ArrayList<Player>();
		teams.put(teamName, players);
	}

	private Team newTeam() throws IOException{
			System.out.println("Enter team name: ");
			String teamName = read.readLine();
			System.out.print("Enter coach name: ");
	    String coachName = read.readLine();
		
	return new Team(teamName,coachName);
	}
	
	private String promptTeam() throws IOException{
		if (this.teamList.size() == 0 ){
			System.out.println("There arent any teams");
			return "";
		}
		
		System.out.println("Teams:");
		
		List<String> teamNames=new ArrayList<>();
		for(Team team: teamList){
			teamNames.add(team.getTeamName());
		}
		Collections.sort(teamNames);
		int index=promptForChoice(teamNames);
		return teamNames.get(index);
	}
	
	private void runPlayers(String teamName){
		
		String choice = "";
		int playersNumber;
		
		do{
			try{
					System.out.println();
					System.out.printf("Team:%-20s %n ",teamName);
					System.out.println("--------Team MENU--------");
					choice = promptAction(mTeamMenu);
          System.out.println("-------------------------");
					List<Player> plyersByTeam = getPlayersForTeam(teamName);
					if (plyersByTeam != null){
						playersNumber = plyersByTeam.size();
					}else {
						System.out.println("There arent plyaers on this team");
						playersNumber = 0;
					}
				
				switch(choice){
					case "1":
						if(isAvailablePlayers()){
						 if(playersNumber < 11){
							System.out.printf("Adding player %n ");
							System.out.println("Available players:");
							int playerIndex = promptForIndex(printPlayers(sortPlayersByName(availablePlayers)));
							addPlayerToTeam(teamName,availablePlayers.get(playerIndex));
							availablePlayers.remove(playerIndex);	
						 }else {
							System.out.printf("This team already has 11 players %n");
						 }
						}else {
							System.out.printf("There are no more available players %n");
						}
						break;

					case "2":
						if(playersNumber > 0){
							System.out.printf("Removing player %n ");					
							int playerIndexR = promptForIndex(printPlayers(plyersByTeam));
							Player player = plyersByTeam.get(playerIndexR);		
							removePlayerFromTeam(teamName,player);
						}else {
							System.out.printf("There are no players on this team  %n");
						}
					break;

					case "3": 
						if (playersNumber == 0){
							   System.out.println("There arent players on this team");
							}else {
							  System.out.printf("Team %s players by height:  %n",teamName);
								System.out.println();
								groupByHeight(ByHeight(teamName));
							}
							break;
          
					case "4":
							if (playersNumber == 0){
							   System.out.println("No players on this team yet");
							}else {		  
								System.out.printf("Team %s roster:  %n",teamName);
							  int i=printPlayers(plyersByTeam);
							}
							break;
					case "0":
              run();
							break;
					default:
						System.out.printf("Invalid choice%n");
				}
			}catch(IOException ioe){
				System.out.println("Invalid input!");
				ioe.printStackTrace();
			}
		}while (!choice.equals("0"));
	}

	private void addPlayerToTeam(String teamName,Player player){
		List<Player> players;
		if(teams.containsKey(teamName)){
			players = teams.get(teamName);
    	players.add(player);
		} else {
			players = new ArrayList<Player>();
			players.add(player);
    	teams.put(teamName, players);
		}
	}
	
	private void removePlayerFromTeam(String teamName,Player player){
		List<Player> players = new ArrayList<>(teams.get(teamName));

		if(players.remove(player)){
			List<Player> pl = teams.replace(teamName,players);		
			availablePlayers.add(player); 
		}
	}
	
  private List<Player> sortPlayersbyH(List<Player> players){
		
			players.sort(new Comparator<Player>(){
			@Override 
			public int compare(Player player1,Player player2){
				if(player1.equals(player2)){
					return 0;
				}
				return player1.getHeightInInches()-player2.getHeightInInches();
			}
		}  );
		
		return players;

	}
	
	public List<Player> getPlayersForTeam(String teamName){
		if(teams.containsKey(teamName)){
			List<Player> players= sortPlayersByName(teams.get(teamName));
			return players;
		}else {
			return null;
		}
	}
			
	private List<Player> sortPlayersByName(List<Player> players){
		
		players.sort(new Comparator<Player>(){
		@Override 
		public int compare(Player player1,Player player2){
			if(player1.equals(player2)){
				return 0;
			}
			return player1.getLastName().compareTo(player2.getLastName());
		}
	  }  );
	 return players;
	}
	
	public List<Player> ByHeight(String teamName){
		if(teams.containsKey(teamName)){
			List<Player> players = teams.get(teamName);
			players.sort(new Comparator<Player>(){
			@Override 
			public int compare(Player player1,Player player2){
				if(player1.equals(player2)){
					return 0;
				}
				return player1.getHeightInInches()-player2.getHeightInInches();
			}
		}  );
		
		return players;
		}else {
			return null;
		}
	}
	
	private void leagueBalanceReport() throws IOException{
		System.out.println();
		System.out.println("--------LEAGUE BALANCE REPORT--------");
		System.out.println("-------------------------------------");
		System.out.println();
		
		for(Map.Entry<String,List<Player>> options:teams.entrySet()){
			String teamName=options.getKey();
			List<Player> players=options.getValue();
			if (players.size()>0){ 
						int experience=0;
						for(Player player:players){
							if(player.isPreviousExperience()) {
							experience++;
							}
						}
					System.out.printf("TEAM: %s %n%n",teamName);
					System.out.printf("Experienced players: %d %n",experience);
					System.out.printf("Inexperienced players: %d %n",players.size()-experience);
				
					double eLevel=experience*100/players.size();
					System.out.printf("Experience level: %.2f%% %n%n",eLevel);
					System.out.printf("Players by height: %n%n");
					printByHeight(ByHeight(teamName));
					System.out.println("--------------------");
					System.out.println();
			}else{
						System.out.printf("no players on this team %n");
			}
		}
  }
	
	private boolean isAvailablePlayers(){
				return (availablePlayers.size()>0);
	}	
	
	private int promptForIndex(int numberOfElements) throws IOException{ 			 
	boolean isAcceptible=false;
	int choice=0;
		
		while (!isAcceptible){ 
			try{
				System.out.print("Your choice: ");
				String optionAsString = read.readLine();
				choice=Integer.parseInt(optionAsString.trim());
				isAcceptible=true;
			
			} catch (NumberFormatException ex ){ 
				System.out.println("Please enter a number");
			}
      
			if (isAcceptible&&((choice<1)||(choice>numberOfElements))) {
					isAcceptible=!isAcceptible;
					System.out.println("Please enter a valid number");
			}
		}
		return choice-1;
	}
	
	private int promptForChoice(List<String> options) throws IOException{ 			 
	 int counter=0;
		
	  for(String option:options){
		  ++counter;
		  System.out.printf("%3d. %s %n",counter,option);
		}
		return promptForIndex(counter);		
	}
		
	private int printPlayers(List<Player> players){
	 int counter=0;

	  String[] st={"number","last name","first name","height","experience"};
	  for(Player player:players){
		  String s;
			if (player.isPreviousExperience()){
			   s="experienced";
					}else {
						s = "inexperienced";
					}
					++counter;
		  		System.out.printf("%7d.  %-15s %-15s %5d %20s %n",counter,player.getLastName(),player.getFirstName(),player.getHeightInInches(),s);
					
			}
			return counter;
	}	
	
	private void groupByHeight(List<Player> players){
		int height=0;
					
		for(Player player:players){
			if (height!=player.getHeightInInches()){
				System.out.println();
				height=player.getHeightInInches();
			}			
			System.out.printf("%-15s %-15s %5d %n",player.getLastName(),player.getFirstName(),player.getHeightInInches());
		}
  }
  
	private void printByHeight(List<Player> players){
		int height=0;
		int count=0;

			for(Player player:players){
			
			if(player.getHeightInInches()==height){
					count++;
			}else{
				if(height!=0){
					System.out.printf("%5d\"- %d player(s) %n",height,count);
				}
					height=player.getHeightInInches();	
					count=1;
				}
			}
			System.out.printf("%5d\"- %d player(s) %n",height,count);
		}
}