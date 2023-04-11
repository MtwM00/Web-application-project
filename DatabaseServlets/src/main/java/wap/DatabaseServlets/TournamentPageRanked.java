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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import wap.DAO.DBconnection;
import wap.DAO.PairingDAO;
import wap.DAO.TournamentDAO;

/**
 * Servlet implementation class TournamentPageRanked
 */
@WebServlet("/TournamentPageRanked")
public class TournamentPageRanked extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TournamentPageRanked() {
        super();
        // TODO Auto-generated constructor stub
    }

    //Show ranked tournament page
    
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		Connection conn = DBconnection.getConn();
		PreparedStatement ps = null;
		ResultSet rs = null;
		response.setContentType("text/html");
		
		request.getRequestDispatcher("bgfoto.html").include(request, response);
		
		int cookie = 0;
		String cookieString = "";
		//cookies with tournament id
		
		Cookie cookies[] = request.getCookies();
		
		if(request.getParameter("idTournament")!=null) {
			cookieString = request.getParameter("idTournament");
			cookie = Integer.parseInt(cookieString);
			
		}else {
			cookieString = cookies[0].getValue();
			cookie = Integer.parseInt(cookieString);
			cookies[0].setMaxAge(0);
			response.addCookie(cookies[0]);
		}
		
		
		out.println("<br>");
		out.println("<br>");
		//out.println(cookie);
		out.println("<br>");
		out.println("<br>");
		
		      
	        
		
		try {

			ps = conn.prepareStatement("SELECT u_nickname, u_name, u_surname, u_elo\r\n"
					+ "FROM user\r\n"
					+ "JOIN player ON idUser = User_idUser\r\n"
					+ "JOIN scoreboard ON idPlayer = Player_idPlayer\r\n"
					+ "where Tournament_idTournament = ?");
           ps.setInt(1, cookie);
           
           ps.executeQuery();
           rs = ps.getResultSet();
           String resultString = "";
           resultString = getHtmlTableFromResultSet(rs);
           
            //sending response to the client:
            response.getWriter().append(resultString);
            out.println("<br>");
            
            if(TournamentDAO.checkIfClosed(cookie)) {
            	out.println("<a href='../DatabaseServlets/ShowPairing'>Check pairing</a>");
            	Cookie ck = new Cookie("tournamentId", cookieString);
            	response.addCookie(ck);
            }
            
            out.println("<br>");
            out.println("<br>");
            out.println("<br>");
            
            
            if(PairingDAO.checkIfMatchAlreadyUpdated(cookie)) {
            out.println("<a href='../DatabaseServlets/ShowSummaryRanked'>Check summary for game</a>");
            Cookie ck = new Cookie("tournamentId", cookieString);
        	response.addCookie(ck);
            }
            
            out.println("<br>");
            out.println("<a href='../DatabaseServlets/ShowTournamentWithJoin'>Go back to tournaments page</a>");
			
		} catch (Exception ex) {
			System.out.println("Exception: " + ex.getMessage());
			response.getWriter().append("Internal error");
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				}

				rs = null;
			}

			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException sqlEx) {
				}

				ps = null;
			}
			}
	}

	
	
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	private String getHtmlTableFromResultSet(ResultSet rs) throws SQLException {
		ResultSetMetaData meta = rs.getMetaData();
		int colCount = meta.getColumnCount();
		String htmlTable = "<table border=\"1\">";
		int l = 1;

		// header row:
		htmlTable += "<tr>";
		htmlTable += "<th>Position</th>";
		for (int col = 1; col <= colCount; col++) {
			htmlTable += "<th>";
			htmlTable += meta.getColumnLabel(col);
			htmlTable += "</th>";
		}
		htmlTable += "</tr>";

		// data rows:

		while (rs.next()) {

			htmlTable += "<tr>";
			htmlTable += "<td>" + l + "</td>";
			for (int col = 1; col <= colCount; col++) {
				Object value = rs.getObject(col);
				htmlTable += "<td>";
				if (value != null) {
					htmlTable += value.toString();
				}
				htmlTable += "</td>";
			}
			l++;

			htmlTable += "</tr>";
		}

		htmlTable += "</table>";

		return htmlTable;
	}
	
}
