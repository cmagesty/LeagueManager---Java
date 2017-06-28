import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Players;
import com.teamtreehouse.model.Team;

import java.io.*;
import java.util.*;

public class LeagueManager {

  public static void main(String[] args) throws IOException {
    Player[] players = Players.load();
    System.out.printf("There are currently %d registered players.%n", players.length);
    String choice = "";
    Map<String, String> mMenu = new HashMap<String, String>();
    BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
    mMenu.put("create team", "create a new team");
    mMenu.put("add players", "add players to a team");
    mMenu.put("remove players", "remove players from a team");
    mMenu.put("quit", "quit program");
    
    System.out.printf("Menu: %n");
    for (Map.Entry<String, String> option : mMenu.entrySet()){
      System.out.printf("%s - %s %n", option.getKey(), option.getValue());
    }
    
    do {
      System.out.printf("Choice: ");
        choice = read.readLine();
        switch (choice) {
          case "create team":
          //implement
          System.out.printf("Enter a team name: ");
          String teamName = read.readLine();
          System.out.printf("Enter a coach name: ");
          String coach = read.readLine();
          Team team = new Team(teamName, coach);
          System.out.printf("Team Created \n");
            break;
          case "add players":
          //implement
            break;
          case "remove players":
          //implement
            break;
          default :
           System.out.printf("unknown command");
           break;
        }
      } while(!choice.equals("quit"));
  }
}

