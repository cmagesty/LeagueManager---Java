import com.teamtreehouse.model.*;

public class LeagueManager {

  public static void main(String[] args) {
    Player[] players = Players.load();
		System.out.printf(" There are currently %d registered players.%n%n%n", players.length);
		
		League league = new League(players);
		league.run();
  }
}