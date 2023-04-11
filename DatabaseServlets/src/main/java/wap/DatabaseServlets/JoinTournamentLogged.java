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
import javax.servlet.http.HttpSession;

import wap.DAO.PlayerDAO;
import wap.DAO.ScoreboardDAO;
import wap.DAO.TournamentDAO;
import wap.DAO.UserDAO;

/**
 * Servlet implementation class JoinTournamentLogged
 */
@WebServlet("/JoinTournamentLogged")
public class JoinTournamentLogged extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public JoinTournamentLogged() {
        super();
        // TODO Auto-generated constructor stub
    }

	

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(false);
		
		if (session == null || session.getAttribute("nickname") == null) {

			out.println("Please Login!");
			request.getRequestDispatcher("Login.jsp").include(request, response);

		}else {
			
			Cookie cookies[] = request.getCookies();
			if(cookies[0]!=null) {
				cookies[0]=null;
			}
			
			
			
			String sesja = (String) session.getAttribute("nickname");
			
			//get idTournament from URL (string)
			
			response.setContentType("text/html");
			String tournamentId = request.getParameter("idTournament");
			
			//Add idTournament to cookie
			
			Cookie cookie = new Cookie("tournamentId", tournamentId);
			response.addCookie(cookie);
			
			
			//id of tournament player wants to join
			int idTournament = Integer.parseInt(tournamentId);
			
			//create player with that userId
			int userId = UserDAO.getUserId(sesja);
			if(!TournamentDAO.checkIfClosed(idTournament)) {
				if(!PlayerDAO.checkIfUserExists(userId)) {
				
				PlayerDAO.addUserToPlayer(userId);
				
				}
				//add player id to scoreboard
				int playerId = ScoreboardDAO.getPlayerIdofUser(sesja);
				out.print("Player ID: "+playerId);
				
				//check if player is already in tournament + need to add validation if code is correct
				if(!ScoreboardDAO.checkDuplicates(playerId, idTournament) && !TournamentDAO.checkIfClosed(idTournament)) {
					
					
					//if not add him
					ScoreboardDAO.addPlayerToScoreboard(playerId, idTournament);
					RequestDispatcher rd = request.getRequestDispatcher("/TournamentPageRanked");
					rd.include(request, response);
					
				}else {
					//show scoreboard of this tournament
					
					RequestDispatcher rd = request.getRequestDispatcher("/TournamentPageRanked");
					rd.include(request, response);
					
				}
			}else {
				out.println("<h5 style=\"color:white\">Enrollment for this tournament has finished!</h5>");
				request.getRequestDispatcher("indexAfterLogin.html").include(request, response);
			}
		}
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		doPost(request, response);
		
	}

}
