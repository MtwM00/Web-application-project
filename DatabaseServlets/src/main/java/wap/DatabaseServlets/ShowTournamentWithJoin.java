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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import wap.DAO.DBconnection;

/**
 * Servlet implementation class ShowTournamentWithJoin
 */
@WebServlet("/ShowTournamentWithJoin")
public class ShowTournamentWithJoin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowTournamentWithJoin() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        
		//tutaj jest zalogowany juz wiec pobiera dane z usera
		
			response.setContentType("text/html");
	        Connection conn = DBconnection.getConn();
	        Statement stmt = null;
	        ResultSet rs = null;
	        PrintWriter out = response.getWriter();
	        
	                
	        request.getRequestDispatcher("bgfoto.html").include(request, response);
	        
	        try {
	            Cookie cookies[] = request.getCookies();
	            cookies[0].setMaxAge(0);
	            stmt = conn.createStatement();
	           
	            String resultString="";
	            if (stmt.execute("select idTournament'Id', t_name 'Name of Tournament', t_type 'Type', t_date 'Date', t_if_ranked 'Ranked' from tournament where t_if_ranked='yes'")) {
	                	            		            	
	            	rs = stmt.getResultSet();
	                resultString = getHtmlTableFromResultSetRanked(rs);
	                         
	                
	            }
	            else {
	                resultString = "Wrong query type";
	            }
	            
	            
	            
	            //showing ranked tournaments	                                
                out.print("<h5>Ranked Tournaments</h5><br>");
                response.getWriter().append(resultString);
	           
	            out.print("<br>");
                out.print("<br>");
                out.print("<br>");
                
                
                resultString="";
                stmt.execute("select idTournament'Id', t_name 'Name of Tournament', t_type 'Type', t_date 'Date', t_if_ranked 'Ranked' from tournament where t_if_ranked='no'");
                rs = stmt.getResultSet();
                resultString = getHtmlTableFromResultSetUnranked(rs);
                
                //showing unranked tournaments
                out.print("<h5>Unranked Tournaments</h5><br>");
                response.getWriter().append(resultString);
	            
                out.print("<br>");
                out.print("<br>");
                out.print("<a href='indexAfterLogin.html'>Go back to main page</a>");
	            
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
						htmlTable += "<a href='../DatabaseServlets/TournamentPageRanked?idTournament="+id+"'>"+value.toString()+"</a>";
						
						}
				}
				htmlTable += "</td>";
			}
			
			htmlTable += "<td><a href='../DatabaseServlets/JoinTournamentLogged?idTournament="+ id +"'>Join</a></td>";
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
			
			htmlTable += "<td><a href='../DatabaseServlets/JoinTournamentLoggedUnranked?idTournament="+ id +"'>Join</a></td>";
			htmlTable += "</tr>";
		}

		htmlTable += "</table>";

		return htmlTable;
	}
}
