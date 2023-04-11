package wap.DatabaseServlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import wap.DAO.GuestDAO;
import wap.DAO.PlayerDAO;
import wap.DAO.ScoreboardDAO;


@WebServlet("/AddPlayer")
public class AddPlayer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();		
		
		//cookie with tournament id
		
		Cookie cookies[] = request.getCookies();
		int tourId = Integer.parseInt(cookies[0].getValue());
		cookies[0].setMaxAge(0);
		
		String name = request.getParameter("g_name");
		String surname = request.getParameter("g_surname");
		
		out.println("Hello "+ name+ " "+ surname);
		
		//insert g_name and g_surname into guest (if x!= 0 then guest correctly added to DB	)
		
		
		int x = GuestDAO.insertIntoGuest(name, surname);
		
		
		int recentGuestId = GuestDAO.getMostRecentGuestId();
		if(x!=0) {
			
			out.println("User successfully added<br>");
			out.println("Most recent guest id: "+recentGuestId+"<br>");
			out.println("<br>Tournament id: "+tourId);
			
			
			//create player using guest id
			
			PlayerDAO.addGuestToPlayer(recentGuestId);
			int z = PlayerDAO.getPlayerIdFromGuestId(recentGuestId);
			
			//add player to Scoreboard
			if(!ScoreboardDAO.checkDuplicates(z, tourId)) {
				
				
			int c = ScoreboardDAO.addPlayerToScoreboard(z, tourId);
			out.println("<br>Player successfully added to scoreboard of a torunament where id = "+tourId);	
				
			}
			
		}
		
		
		
		//Show scoreboard or page of this tournament
		
		RequestDispatcher rd = request.getRequestDispatcher("/TournamentPageUnranked");
		rd.include(request, response);
		
	}

}
