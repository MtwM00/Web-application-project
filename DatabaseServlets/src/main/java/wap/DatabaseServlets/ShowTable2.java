package wap.DatabaseServlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
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

import wap.DAO.DBconnection;

/**
 * Servlet implementation class ShowTable
 */
@WebServlet("/ShowTable2")
public class ShowTable2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String username = "root";
	private String password = "Mati4321";
	private String databaseSchema = "wap_project_ctp";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowTable2() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        Connection conn = DBconnection.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        int tid=1;
        //tabela wyswietla polaczane wyniki tabel judge i user
        
        
        try {
           
            ps = conn.prepareStatement("SELECT u_name'Name', u_surname'Surname'\r\n"
            		+ "FROM player\r\n"
            		+ "JOIN user ON idUser = User_idUser\r\n"
            		+ "JOIN scoreboard ON idPlayer = Player_idPlayer\r\n"
            		+ "WHERE Tournament_idTournament = ?\r\n"
            		+ "UNION \r\n"
            		+ "SELECT g_name, g_surname\r\n"
            		+ "FROM player\r\n"
            		+ "JOIN guest ON Guest_idGuest = idGuest\r\n"
            		+ "JOIN scoreboard ON idPlayer = Player_idPlayer\r\n"
            		+ "WHERE Tournament_idTournament = ?;");
           ps.setInt(1, tid);
           ps.setInt(2, tid);
           ps.executeQuery();
           rs = ps.getResultSet();
           String resultString = "";
           resultString = getHtmlTableFromResultSet(rs);
           
            //sending response to the client:
            response.getWriter().append(resultString);
           
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            response.getWriter().append("Internal error");
        }
        
        
        
        
    }



	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	private String getHtmlTableFromResultSet(ResultSet rs) throws SQLException
    {
        ResultSetMetaData meta = rs.getMetaData();
        int colCount = meta.getColumnCount();
        String htmlTable = "<table border=\"1\">";
        
        //header row:
        htmlTable +="<tr>";
        for (int col=1; col <= colCount; col++)
        {
            htmlTable +="<th>";
            htmlTable +=meta.getColumnLabel(col);
            htmlTable +="</th>";
        }
        htmlTable +="</tr>";
        
        //data rows:
        while(rs.next()) {
            
            htmlTable +="<tr>";
            
            for (int col=1; col <= colCount; col++)
            {
                Object value = rs.getObject(col);
                htmlTable +="<td>";
                if (value != null)
                {
                    htmlTable +=value.toString();
                }
                htmlTable +="</td>";
            }
            htmlTable +="</tr>";
        }
        htmlTable +="</table>";
        return htmlTable;
    }
	
	
}
