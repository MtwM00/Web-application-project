package wap.DatabaseServlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import wap.DAO.DBconnection;

/**
 * Servlet implementation class ShowUnrankedTournaments
 */
@WebServlet("/ShowUnrankedTournaments")
public class ShowUnrankedTournaments extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowUnrankedTournaments() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = DBconnection.getConn();
        Statement stmt = null;
        ResultSet rs = null;
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);
        response.setContentType("text/html");
        request.getRequestDispatcher("bgfoto.html").include(request, response);
        if (session == null || session.getAttribute("nickname") == null) {

					
        
	        try {
	            
	            stmt = conn.createStatement();
	           
	            String resultString="";
	            if (stmt.execute("select idTournament'Id', t_name 'Name of Tournament', t_type 'Type', t_date 'Date', t_if_ranked 'Ranked' from tournament where t_if_ranked='no'")) {
	                rs = stmt.getResultSet();
	                resultString = getHtmlTableFromResultSet(rs);
	            }
	            else
	                resultString = "Wrong query type";
	           
	            
	            
	            
	            //sending response to the client:
	            out.print("<h5>Unranked Tournaments</h5><br>");
	            response.getWriter().append(resultString);
	           
	            
	    		out.println("If you want to see all tournaments, please log in!");
	    		request.getRequestDispatcher("Login.jsp").include(request, response);
	
	    		
	            
	        } catch (Exception ex) {
	            System.out.println("Exception: " + ex.getMessage());
	            response.getWriter().append("Internal error");
	        }
	        finally {
	            if (rs != null) {
	                try {
	                    rs.close();
	                } catch (SQLException sqlEx) { }
	
	                rs = null;
	            }
	
	            if (stmt != null) {
	                try {
	                    stmt.close();
	                } catch (SQLException sqlEx) { }
	
	                stmt = null;
	            }
	        }
        }
        else {
        	request.getRequestDispatcher("/ShowTournamentWithJoin").include(request, response);
        	
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
		

		// header row:
		htmlTable += "<tr>";
		for (int col = 1; col <= colCount; col++) {
			htmlTable += "<th>";
			htmlTable += meta.getColumnLabel(col);
			htmlTable += "</th>";
		}
		htmlTable += "<th>Join</th>";
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
			
			htmlTable += "<td><a href='../DatabaseServlets/JoinTournamentGuest?idTournament="+ id +"'>Join</a></td>";
			htmlTable += "</tr>";
		}

		htmlTable += "</table>";

		return htmlTable;
	}

}
