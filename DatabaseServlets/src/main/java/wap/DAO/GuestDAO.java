package wap.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GuestDAO {

	
	//insert data into guest method
	
	public static int insertIntoGuest(String g_name, String g_surname) {
		int status = 0;
		
		Connection con = DBconnection.getConn();
		
		try {
			PreparedStatement ps = con.prepareStatement("Insert into guest Values(null,?,?)");
			ps.setString(1, g_name);
			ps.setString(2, g_surname);
			status =  ps.executeUpdate();
			con.close();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return status;
	}
	
	//Receive guestId of most recent guest
	
	public static int getMostRecentGuestId() {
		
		Connection con = DBconnection.getConn();
		int a = 0;
		
		try {
			PreparedStatement ps = con.prepareStatement("SELECT MAX(idGuest) FROM guest");
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
	
}
