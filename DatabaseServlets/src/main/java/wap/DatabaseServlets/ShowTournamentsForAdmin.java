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
import wap.DAO.JudgeDAO;
import wap.DAO.TournamentDAO;
import wap.DAO.UserDAO;

/**
 * Servlet implementation class ShowTournamentsForAdmin
 */
@WebServlet("/ShowTournamentsForAdmin")
public class ShowTournamentsForAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(false);
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = DBconnection.getConn();
		request.getRequestDispatcher("bgfoto.html").include(request, response);
		String sesja = (String) session.getAttribute("nickname");
		
		int userId = UserDAO.getUserId(sesja);
		
		try {
			ps = conn.prepareStatement("select idTournament'Id', t_name 'Name of Tournament', t_type 'Type', t_date 'Date', isClosed\r\n"
					+ "FROM tournament\r\n"
					+ "Join judge on Judge_idJudge = idJudge\r\n"
					+ "join user on idUser = judge.User_idUser\r\n"
					+ "where idUser=? and t_if_ranked='yes'\r\n"
					+ "Union\r\n"
					+ "select idTournament'Id', t_name 'Name of Tournament', t_type 'Type', t_date 'Date', isClosed\r\n"
					+ "from tournament\r\n"
					+ "Join tournamentorganizer on TournamentOrganizer_idTournamentOrganizer = idTournamentOrganizer\r\n"
					+ "join user on idUser = tournamentorganizer.User_idUser\r\n"
					+ "where idUser=? and t_if_ranked='yes';");
			ps.setInt(1, userId);
			ps.setInt(2, userId);
		       
			ps.executeQuery();
			rs = ps.getResultSet();
			String resultString = "";
			resultString = getHtmlTableFromResultSetRanked(rs);
			
			
			//sending response to the client:
			
			out.println("<h5>Manage tournaments</h5><br>");
			out.println("<h5>Ranked Tournaments:</h5><br>");
	        response.getWriter().append(resultString);
	        out.println("<br><br><br>");
	        
	        resultString="";
            ps = conn.prepareStatement("select idTournament'Id', t_name 'Name of Tournament', t_type 'Type', t_date 'Date', isClosed\r\n"
					+ "FROM tournament\r\n"
					+ "Join judge on Judge_idJudge = idJudge\r\n"
					+ "join user on idUser = judge.User_idUser\r\n"
					+ "where idUser=? and t_if_ranked='no'\r\n"
					+ "Union\r\n"
					+ "select idTournament'Id', t_name 'Name of Tournament', t_type 'Type', t_date 'Date', isClosed\r\n"
					+ "from tournament\r\n"
					+ "Join tournamentorganizer on TournamentOrganizer_idTournamentOrganizer = idTournamentOrganizer\r\n"
					+ "join user on idUser = tournamentorganizer.User_idUser\r\n"
					+ "where idUser=? and t_if_ranked='no';");
            ps.setInt(1, userId);
			ps.setInt(2, userId);
			ps.executeQuery();
            rs = ps.getResultSet();
            resultString = getHtmlTableFromResultSetUnranked(rs);
            
            //showing unranked tournaments
            out.print("<h5>Unranked Tournaments</h5><br>");
            response.getWriter().append(resultString);
            
            
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	
	private String getHtmlTableFromResultSetRanked(ResultSet rs) throws SQLException {
		ResultSetMetaData meta = rs.getMetaData();
		int colCount = meta.getColumnCount();
		String htmlTable = "<table border=\"1\">";
		

		// header row:
		htmlTable += "<tr>";
		for (int col = 1; col <= colCount; col++) {
			htmlTable += "<th>";
			htmlTable += meta.getColumnLabel(col);
			htmlTable += "</th>";
		}
		htmlTable += "<th>Close tournament</th>";
		htmlTable += "<th>Show pairing </th>";
		htmlTable += "</tr>";

		// data rows:

		String id = "";
		
		while (rs.next()) {

			htmlTable += "<tr>";
			
			for (int col = 1; col <= colCount; col++) {
				Object value = rs.getObject(col);
				htmlTable += "<td>";
				if (value != null) {
					if(col!=2) {
						htmlTable += value.toString();
						if(col==1) {
							id=value.toString();
						
					}
						
					}else {
						htmlTable += "<a href='../DatabaseServlets/TournamentPageRanked?idTournament="+id+"'>"+value.toString()+"</a>";
						
						}
				}
				htmlTable += "</td>";
			}
			
			htmlTable += "<td><a href='../DatabaseServlets/CloseTournament?idTournament="+ id +"'>Close</a></td>";
			htmlTable += "<td><a href='../DatabaseServlets/FormResultsOfTournament?idTournament="+ id +"'>Set results</a></td>";
			htmlTable += "</tr>";
		}

		htmlTable += "</table>";

		return htmlTable;
	}
	
	private String getHtmlTableFromResultSetUnranked(ResultSet rs) throws SQLException {
		ResultSetMetaData meta = rs.getMetaData();
		int colCount = meta.getColumnCount();
		String htmlTable = "<table border=\"1\">";
		

		// header row:
		htmlTable += "<tr>";
		for (int col = 1; col <= colCount; col++) {
			htmlTable += "<th>";
			htmlTable += meta.getColumnLabel(col);
			htmlTable += "</th>";
		}
		htmlTable += "<th>Close tournament</th>";
		htmlTable += "<th>Show pairing </th>";
		htmlTable += "</tr>";

		// data rows:

		String id = "";
		
		while (rs.next()) {

			htmlTable += "<tr>";
			
			for (int col = 1; col <= colCount; col++) {
				Object value = rs.getObject(col);
				htmlTable += "<td>";
				if (value != null) {
					if(col!=2) {
						htmlTable += value.toString();
						if(col==1) {
							id=value.toString();
						
					}
						
					}else {
						htmlTable += "<a href='../DatabaseServlets/TournamentPageUnranked?idTournament="+id+"'>"+value.toString()+"</a>";
						
						}
				}
				htmlTable += "</td>";
			}
			
			htmlTable += "<td><a href='../DatabaseServlets/CloseTournament?idTournament="+ id +"'>Close</a></td>";
			htmlTable += "<td><a href='../DatabaseServlets/FormResultsOfTournament?idTournament="+ id +"'>Set results</a></td>";
			htmlTable += "</tr>";
		}

		htmlTable += "</table>";

		return htmlTable;
	}
	
}
