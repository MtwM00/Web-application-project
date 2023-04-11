package wap.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

	//Retrieves id of logged in user
	
		public static int getUserId(String sesja) {
			
			Connection con = DBconnection.getConn();
			int a =0;
			
			try {
				PreparedStatement ps = con.prepareStatement("Select idUser From user where u_nickname =?");
				ps.setString(1, sesja);
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
