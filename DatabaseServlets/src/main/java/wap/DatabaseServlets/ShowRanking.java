package wap.DatabaseServlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
 * Servlet implementation class ShowRanking
 */
@WebServlet("/ShowRanking")
public class ShowRanking extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ShowRanking() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		        
        request.getRequestDispatcher("bgfoto.html").include(request, response);
		
		HttpSession session = request.getSession(false);

		if (session == null || session.getAttribute("nickname") == null) {

			out.println("Please Login!");
			request.getRequestDispatcher("Login.jsp").include(request, response);

		} else {

			Connection conn = DBconnection.getConn();
			Statement stmt = null;
			ResultSet rs = null;

			try {

				stmt = conn.createStatement();

				//request.getRequestDispatcher("indexAfterLogin.html").include(request, response);
				
				String resultString = "";
				if (stmt.execute("SELECT u_name'Name',u_surname'Surname',u_nickname'Nickname',u_elo'ELO',u_country'Country' FROM user order by u_elo DESC LIMIT 20")) {
					rs = stmt.getResultSet();
					resultString = getHtmlTableFromResultSet(rs);
				} else
					resultString = "Wrong query type";

				// sending response to the client:
				response.getWriter().append(resultString);

				
				
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

				if (stmt != null) {
					try {
						stmt.close();
					} catch (SQLException sqlEx) {
					}

					stmt = null;
				}
			}

		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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
