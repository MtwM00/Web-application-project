package wap.DatabaseServlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpCookie;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import wap.DAO.TournamentDAO;

/**
 * Servlet implementation class JoinTournamentGuest
 */
@WebServlet("/JoinTournamentGuest")
public class JoinTournamentGuest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public JoinTournamentGuest() {
        super();
        // TODO Auto-generated constructor stub
    }

	
    
    
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
		PrintWriter out  = response.getWriter();
		response.setContentType("text/html");
		String tournamentId = request.getParameter("idTournament");
		
		if(TournamentDAO.checkIfClosed(Integer.parseInt(tournamentId))) {
			out.println("Enrollment for this tournament has finished!");
			request.getRequestDispatcher("ShowUnrankedTournaments").include(request, response);
		}else {
			Cookie cookie = new Cookie("tournamentId", tournamentId);
			response.addCookie(cookie);
			
			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head>");
			out.println("<meta charset=\"UTF-8\">");
			out.println("<title>Join Tournament as guest</title>");
			out.println("<link rel=\"stylesheet\" href=\"CSSy/bgbg.css\">");
			out.println("</head>");
			out.println("<body>");
			
			out.println("<p>Enter your name and surname</p>");
			
			out.print("<form method=\"post\" action=\"AddPlayer\">");
			out.print("Name:<input type=\"text\" name=\"g_name\"><br>");
			out.print("<br>");
			out.print("Surname:<input type=\"text\" name=\"g_surname\"><br>");
			out.print("<input type=\"submit\" value=\"Join\">");
			out.print("</form>");
			
			out.println("</body>");
			out.println("</html>");
		
	}

	}
	
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		
		//insert g_name and g_surname into guest 
		//create player using guest id
		//add player to Scoreboard
		
		
	}

}
