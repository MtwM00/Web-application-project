package wap.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBconnection {
	
	public static Connection getConn() {
	    
		
		Connection con = null;
	    String loadDriver = "com.mysql.cj.jdbc.Driver";
	        
	    String username = "root";
		String password = "rootpass";
		String databaseSchema = "wap_project_ctp";
		
	    try {
	      Class.forName(loadDriver);
	      con = DriverManager.getConnection("jdbc:mysql://localhost/" + databaseSchema + "?serverTimezone=UTC",username,password);
	    } catch (ClassNotFoundException e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
	    } catch (SQLException e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
	    }
	    return con;
	  }
	
	

}
