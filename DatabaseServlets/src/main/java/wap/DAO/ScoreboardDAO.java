package wap.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ScoreboardDAO {

	public static int addPlayerToScoreboard(int player_idPlayer,int Tournament_idTournament) {
		int status = 0;
		
		Connection con = DBconnection.getConn();
		
		try {
			PreparedStatement ps = con.prepareStatement("Insert into scoreboard Values(null,0,0,0,?,?)");
			ps.setInt(1, player_idPlayer);
			ps.setInt(2, Tournament_idTournament);
			status = ps.executeUpdate();
			con.close();
			//todo musze sprawdzic czy dla danego tournament id w scoreboard nie ma duplikatu player id przy wprowadzaniu 
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return status;
	}
	
	public static int getPlayerIdofUser(String sesja) {
		
		Connection con = DBconnection.getConn();
		int a=0;
		int userId = UserDAO.getUserId(sesja);
		
		try {
			PreparedStatement ps = con.prepareStatement("select idPlayer from player where User_idUser = ?");
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
			a = (int) rs.getObject(1);
			}
			con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//if a returns 0 there is no player with such userId
		
		return a;
	}
	
	
	public static boolean checkDuplicates(int playerId,int tournamentId) {
		boolean status = false;
		Connection con = DBconnection.getConn();
		
		try {
			PreparedStatement ps = con.prepareStatement("select * from scoreboard where Player_idPlayer = ? and Tournament_idTournament = ?");
			ps.setInt(1, playerId);
			ps.setInt(2, tournamentId);
			ResultSet rs = ps.executeQuery();
			status = rs.next();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//want to achieve false (because if there were duplicates it will return true)
		return status;
	}
	
	public static int getScore(int winner, int tournamentId) {
		int a =0;
		Connection con = DBconnection.getConn();
		
		PreparedStatement ps;
		try {
			ps = con.prepareStatement("select COUNT(winner) from wap_project_ctp.match where winner = ? and Tournament_idTournament=?");
			ps.setInt(1, winner);
			ps.setInt(2, tournamentId);
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
	
	public static int updateScore(int winner, int tournamentId) {
		int status = 0;
		Connection con = DBconnection.getConn();
		
		try {
			PreparedStatement ps = con.prepareStatement("update scoreboard set score = score+1 where Player_idPlayer = ? and tournament_idTournament = ?");
			ps.setInt(1, winner);
			ps.setInt(2, tournamentId);
			status = ps.executeUpdate();
			
			con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return status;
	}
	
}
