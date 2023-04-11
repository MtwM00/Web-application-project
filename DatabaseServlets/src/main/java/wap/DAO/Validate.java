package wap.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Validate {

	//Checks if email or nickname already exist in user
	//If there are no matching rows (meaning there aren't any duplicates) chechInput should return false
	
	
	public static boolean checkInput(String email,String nickname) {
					
		
		boolean status = false;
		try {
			
			Connection con = DBconnection.getConn();
			PreparedStatement ps = con.prepareStatement("select * from user where u_mail=? or u_nickname=?");
			ps.setString(1, email);
			ps.setString(2, nickname);
			ResultSet rs = ps.executeQuery();
			status = rs.next();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return status;
	}
	
	
	
	
}
