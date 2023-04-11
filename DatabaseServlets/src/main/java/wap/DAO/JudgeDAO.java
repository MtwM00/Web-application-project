package wap.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JudgeDAO {

	public static int getJudgeIdFromUserId(int userId) {
		int a = 0;
		Connection con = DBconnection.getConn();
		
		try {
			PreparedStatement ps = con.prepareStatement("Select idJudge from judge JOIN user on User_idUser = idUser where idUser=?");
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
		
		
		
		return a;
	}
	
	
}
