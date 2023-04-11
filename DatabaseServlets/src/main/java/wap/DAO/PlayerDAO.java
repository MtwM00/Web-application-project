package wap.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.cj.x.protobuf.MysqlxPrepare.Prepare;

public class PlayerDAO {

	//create player using guest id
	
	public static int addGuestToPlayer(int guestId) {
		int status = 0;
		
		Connection con = DBconnection.getConn();
		
		try {
			PreparedStatement ps = con.prepareStatement("Insert into player values (null,null,?)");
			ps.setInt(1, guestId);
			status = ps.executeUpdate();
			con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return status;
	}
	
	public static int addUserToPlayer(int userId) {
		int status = 0;
		Connection con = DBconnection.getConn();
		try {
			PreparedStatement ps = con.prepareStatement("Insert into player values (null,?,null)");
			ps.setInt(1, userId);
			status = ps.executeUpdate();
			con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return status;
	}
	
	
	public static boolean checkIfUserExists(int userId) {
		boolean status = false;
		Connection con = DBconnection.getConn();
		
		try {
			PreparedStatement ps = con.prepareStatement("Select * from player where User_idUser=?");
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			status = rs.next();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return status;
	}
	
	public static boolean checkIfGuestExists(int guestId) {
		boolean status = false;
		Connection con = DBconnection.getConn();
		
		try {
			PreparedStatement ps = con.prepareStatement("Select * from player where Guest_idGuest=?");
			ps.setInt(1, guestId);
			ResultSet rs = ps.executeQuery();
			status = rs.next();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return status;
	}
	
	public static int getPlayerIdFromGuestId(int guestId) {
			
			Connection con = DBconnection.getConn();
			int a=0;
			
			
			try {
				PreparedStatement ps = con.prepareStatement("select * from player where Guest_idGuest = ?");
				ps.setInt(1, guestId);
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
	
	public static String getPlayerNameAndSurname(int playerId) {
		String nameAndSurname = "";
		String name = "";
        String surname = "";
		Connection con = DBconnection.getConn();
		
		if(playerId!=0) {
			try {
				PreparedStatement ps = con.prepareStatement("SELECT u_name'Name', u_surname'Surname'\r\n"
						+ "FROM player\r\n"
						+ "JOIN user ON idUser = User_idUser\r\n"
						+ "JOIN scoreboard ON idPlayer = Player_idPlayer\r\n"
						+ "WHERE Player_idPlayer = ?\r\n"
						+ "UNION \r\n"
						+ "SELECT g_name, g_surname\r\n"
						+ "FROM player\r\n"
						+ "JOIN guest ON Guest_idGuest = idGuest\r\n"
						+ "JOIN scoreboard ON idPlayer = Player_idPlayer\r\n"
						+ "WHERE Player_idPlayer = ?");
				ps.setInt(1, playerId);
				ps.setInt(2, playerId);
				ps.executeQuery();
		        ResultSet rs = ps.getResultSet();
		        
				
		        
		        while(rs.next()) {
		        	name = rs.getObject(1).toString();
		        	surname = rs.getObject(2).toString();
		        }
		        
		        nameAndSurname = name + " "+ surname +" ";
		        
		        con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else nameAndSurname="Bye";
		
		return nameAndSurname;
	}
	
}
