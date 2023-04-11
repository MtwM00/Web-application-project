package wap.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PairingDAO {

	
	
	
	
	public static int getNumberOfPlayers(int tournamentId) {
		
		Connection con = DBconnection.getConn();
		int a =0;
		
		try {
			PreparedStatement ps = con.prepareStatement("Select count(*) from scoreboard where Tournament_idTournament=?");
			ps.setInt(1, tournamentId);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
			a = (int) rs.getObject(1);
			}
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return a;
		
	}
	
	
	
	public static void iterateOneRound(ArrayList<Integer> players1, ArrayList<Integer> players2, int numberOfPlayers){



        //Wartosc ostatniego elementu tablicy 1
        int value1 = players1.get(players1.size() - 1);
        //Wartosc drugiego elementu pierwszej tablicy pobrana z 2
        int value2 = players2.get(0);

        //petla przypisujaca kolejnych zawodnikow 1 tabeli
        for (int l = players1.size() - 1; l >= 2; l--) {
            players1.set(l, players1.get(l - 1));
        }
        //przypisanie drugiemu zawodnikowi 1 tabeli wartosci z 2 tabeli
        players1.set(1, value2);

        //przesuniecie o indeks drugiej tabeli
        for (int m = 0; m < players2.size() - 1; m++) {
            players2.set(m, players2.get(m + 1));
        }
        //przypisanie ostatniego zawodnika z pierwszej tabeli do drugiej
        players2.set(players2.size() - 1, value1);

    }
	
	
	public static ArrayList<Integer> insertPlayerIdIntoPlayer(int tournamentId){
		
		Connection con = DBconnection.getConn();
		
		//create arraylist of players
		ArrayList<Integer> players = new ArrayList<Integer>();
		
		String statement = "Select Player_idPlayer from scoreboard where Tournament_idTournament=?";
		
		try {
			PreparedStatement ps = con.prepareStatement(statement);
			ps.setInt(1, tournamentId);
			ps.executeQuery();
			ResultSet rs = ps.getResultSet();
			
			while(rs.next()) {
				
				Object value = rs.getObject(1);
				players.add(0, (int)value);
				
				
			}
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return players;
	}
	
	public static int insertIntoMatch(int playerId1,int playerId2, int winner, int tournamentId) {
		int status = 0;
		Connection con = DBconnection.getConn();
		
		try {
			PreparedStatement ps = con.prepareStatement("Insert into wap_project_ctp.match values (null,?,?,?,null,?)");
			ps.setInt(1, playerId1);
			ps.setInt(2, playerId2);
			ps.setInt(3, winner);
			ps.setInt(4, tournamentId);
			
			status = ps.executeUpdate();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return status;
	}
	
	public static int updateWinnerInMatch(int winner, int playerId1, int playerId2, int tournamentId) {
		int status = 0;
		Connection con = DBconnection.getConn();
		
		try {
			PreparedStatement ps = con.prepareStatement("Update wap_project_ctp.match set winner = ? where playerID1 = ? AND playerID2 = ? AND Tournament_idTournament=?");
			ps.setInt(1, winner);
			ps.setInt(2, playerId1);
			ps.setInt(3, playerId2);
			ps.setInt(4, tournamentId);
			
			status = ps.executeUpdate();
			con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return status;
	}
	
	
	//returns if data has already been inserted into match
	public static boolean checkIfMatchAlreadyUpdated(int tournamentId) {
		boolean status = false;
		Connection con = DBconnection.getConn();
		
		PreparedStatement ps;
		try {
			ps = con.prepareStatement("SELECT * FROM wap_project_ctp.match WHERE Tournament_idTournament = ?");
			ps.setInt(1, tournamentId);
			ResultSet rs = ps.executeQuery();
			status = rs.next();
			con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//if true then already inserted
		return status;
	}
	
}
