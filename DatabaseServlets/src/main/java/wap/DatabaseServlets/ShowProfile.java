package wap.DatabaseServlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import wap.DAO.DBconnection;
import wap.DAO.UserDAO;

/**
 * Servlet implementation class ShowProfile
 */
@WebServlet("/ShowProfile")
public class ShowProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		
		Connection conn = DBconnection.getConn();
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(false);
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		
		String sesja = (String) session.getAttribute("nickname");
		int userId = UserDAO.getUserId(sesja);
		        
        request.getRequestDispatcher("bgfoto.html").include(request, response);
		
		//show basic info
		try {
			ps = conn.prepareStatement("Select u_name'Name', u_surname'Surname',u_nickname'Nickname' ,u_mail'e-mail', u_country'Country',u_birth_date'Birth Date',u_elo'ELO' From user where idUser =?");
			ps.setInt(1, userId);
		       
			ps.executeQuery();
			rs = ps.getResultSet();
			String resultString = "";
			resultString = getHtmlTableFromResultSet(rs);
			
			
			//sending response to the client:
			
			out.println("<h5>Player's info </h5><br>");
	        response.getWriter().append(resultString);
	        out.println("<br><br><br>");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
       
        
		
		
		//Recent tournaments
		
		try {
			ps = conn.prepareStatement("select t_name 'Name of Tournament', t_type 'Type', t_date 'Date', t_if_ranked 'Ranked'\r\n"
					+ "from tournament\r\n"
					+ "JOIN scoreboard on Tournament_idTournament=idTournament\r\n"
					+ "JOIN player on idPlayer = Player_idPlayer\r\n"
					+ "JOIN user on idUser  = User_idUser\r\n"
					+ "where idUser=?");
			ps.setInt(1, userId);
		       
			ps.executeQuery();
			rs = ps.getResultSet();
			String resultString = "";
			resultString = getHtmlTableFromResultSet(rs);
			
			//sending response to the client:
			
			out.println("<h5>Your tournaments:</h5><br>");
	        response.getWriter().append(resultString);
	        out.println("<br><br><br>");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	
	private String getHtmlTableFromResultSet(ResultSet rs) throws SQLException {
		ResultSetMetaData meta = rs.getMetaData();
		int colCount = meta.getColumnCount();
		String htmlTable = "<table border=\"1\" >";
		

		// header row:
		htmlTable += "<tr>";
		
		for (int col = 1; col <= colCount; col++) {
			htmlTable += "<th>";
			htmlTable += meta.getColumnLabel(col);
			htmlTable += "</th>";
		}
		htmlTable += "</tr>";

		// data rows:

		while (rs.next()) {

			htmlTable += "<tr>";
			
			for (int col = 1; col <= colCount; col++) {
				Object value = rs.getObject(col);
				htmlTable += "<td>";
				if (value != null) {
					htmlTable += value.toString();
				}
				htmlTable += "</td>";
			}
			

			htmlTable += "</tr>";
		}

		htmlTable += "</table>";

		return htmlTable;
	}
	
	
}
