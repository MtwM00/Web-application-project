package wap.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.cj.xdevapi.Result;


public class TournamentDAO {

	public static int createTournament(String name,String type,String ranked, Date date, String code,int judgeId,int organizerId) {
		int status = 0;
		
		Connection con = DBconnection.getConn();
		try {
			PreparedStatement ps = con.prepareStatement("Insert into tournament Values (null,?,?,?,?,?,?,?,null)");
			ps.setString(1, name);
			ps.setString(2, type);
			ps.setString(3, ranked);
			ps.setDate(4, date);
			ps.setString(5, code);
			ps.setInt(6, judgeId);
			ps.setInt(7, organizerId);
			status = ps.executeUpdate();
			con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		//jezeli status 0 to nie powiodlo sie, ajezeli !=0 to dobrze
		return status;
	}
	
	
	
	//Inserts userId into tournamentOrganizer table
	
	public static int insertTournamentOrganizer(int user_idUser) {
		int status = 0;
		Connection con = DBconnection.getConn();
		
		try {
			PreparedStatement ps = con.prepareStatement("insert into tournamentorganizer values (null,?) ");
			ps.setInt(1, user_idUser);
			status = ps.executeUpdate();
			con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return status;
	}
	
	
	public static int getTournamentOrganizerId(int user_idUser) {
		
		Connection con = DBconnection.getConn();
		int a =0;
		
		try {
			PreparedStatement ps = con.prepareStatement("select idTournamentOrganizer from tournamentorganizer where User_idUser=?");
			ps.setInt(1, user_idUser);
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

	public static boolean checkCode(int tournamentId,String t_code) {
		boolean status = false;
		Connection con = DBconnection.getConn();
		
		try {
			PreparedStatement ps = con.prepareStatement("select  * from tournament where t_code = ? and idTournament = ?");
			ps.setInt(1, tournamentId);
			ps.setString(2, t_code);
			ResultSet rs = ps.executeQuery();
			status=rs.next();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//if true then t_code matches tournament id
		return status;
	}
	
	public static boolean checkIfClosed(int tournamentId) {
		boolean status = false;
		Connection con = DBconnection.getConn();
		
		try {
			PreparedStatement ps = con.prepareStatement("select  * from tournament where isClosed ='yes' and idTournament=?");
			ps.setInt(1, tournamentId);
			ResultSet rs = ps.executeQuery();
			status = rs.next();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//if true then it is closed
		return status;
	}
	
	public static int closeTournament(int tournamentId) {
		int status = 0;
		Connection con = DBconnection.getConn();
		
		try {
			PreparedStatement ps = con.prepareStatement("update tournament set isClosed = 'yes' where idTournament = ?");
			ps.setInt(1, tournamentId);
			status = ps.executeUpdate();
			
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return status;
	}
	
}
