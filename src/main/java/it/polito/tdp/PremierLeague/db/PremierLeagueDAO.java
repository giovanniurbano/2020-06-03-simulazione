package it.polito.tdp.PremierLeague.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.PremierLeague.model.Action;
import it.polito.tdp.PremierLeague.model.Adiacenza;
import it.polito.tdp.PremierLeague.model.Player;

public class PremierLeagueDAO {
	
	public List<Adiacenza> getAdiacenze(Double minGoals, Map<Integer, Player> idMap) {
		String sql = "SELECT p1.PlayerID AS id1, p2.PlayerID AS id2, (a1.TimePlayed - a2.TimePlayed) AS peso "
				+ "FROM actions a1, players p1, actions a2, players p2 "
				+ "WHERE a1.PlayerID = p1.PlayerID AND a2.PlayerID = p2.PlayerID "
				+ "AND a1.`Starts` = 1 AND a2.`Starts` = 1 "
				+ "AND a1.MatchID = a2.MatchID  "
				+ "AND p1.PlayerID <> p2.PlayerID "
				+ "AND a1.TeamID <> a2.TeamID "
				+ "AND p1.PlayerID IN (SELECT p.PlayerID "
				+ "							FROM actions a, players p "
				+ "							WHERE a.PlayerID = p.PlayerID "
				+ "							GROUP BY p.PlayerID, p.Name "
				+ "							HAVING AVG(a.Goals) > ?) "
				+ "AND p2.PlayerID IN (SELECT p.PlayerID "
				+ "							FROM actions a, players p "
				+ "							WHERE a.PlayerID = p.PlayerID "
				+ "							GROUP BY p.PlayerID, p.Name "
				+ "							HAVING AVG(a.Goals) > ?)";
		List<Adiacenza> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setDouble(1, minGoals);
			st.setDouble(2, minGoals);
			
			ResultSet res = st.executeQuery();
			while (res.next()) {
				Player p1 = idMap.get(res.getInt("id1"));
				Player p2 = idMap.get(res.getInt("id2"));
				
				if(p1 != null && p2 != null && !p1.equals(p2)) {
					Adiacenza a = new Adiacenza(p1, p2, (double)res.getInt("peso"));
					result.add(a);
				}
				
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Player> getPlayersByAvgGoals(Double minGoals) {
		String sql = "SELECT p.PlayerID, p.Name "
				+ "FROM actions a, players p "
				+ "WHERE a.PlayerID = p.PlayerID "
				+ "GROUP BY p.PlayerID, p.Name "
				+ "HAVING AVG(a.Goals) > ?";
		List<Player> result = new ArrayList<Player>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setDouble(1, minGoals);
			
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Player player = new Player(res.getInt("PlayerID"), res.getString("Name"));
				
				result.add(player);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Player> listAllPlayers(){
		String sql = "SELECT * FROM Players";
		List<Player> result = new ArrayList<Player>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Player player = new Player(res.getInt("PlayerID"), res.getString("Name"));
				
				result.add(player);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Action> listAllActions(){
		String sql = "SELECT * FROM Actions";
		List<Action> result = new ArrayList<Action>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Action action = new Action(res.getInt("PlayerID"),res.getInt("MatchID"),res.getInt("TeamID"),res.getInt("Starts"),res.getInt("Goals"),
						res.getInt("TimePlayed"),res.getInt("RedCards"),res.getInt("YellowCards"),res.getInt("TotalSuccessfulPassesAll"),res.getInt("totalUnsuccessfulPassesAll"),
						res.getInt("Assists"),res.getInt("TotalFoulsConceded"),res.getInt("Offsides"));
				
				result.add(action);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
