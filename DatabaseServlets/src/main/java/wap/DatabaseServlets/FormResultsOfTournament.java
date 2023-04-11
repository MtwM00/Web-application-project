package wap.DatabaseServlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import wap.DAO.PairingDAO;
import wap.DAO.PlayerDAO;
import wap.DAO.TournamentDAO;

/**
 * Servlet implementation class ShowPairing
 */
@WebServlet("/FormResultsOfTournament")
public class FormResultsOfTournament extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);		      
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		
		//need to get tournament id from somewhere
		
		String sTournamentId = request.getParameter("idTournament");
		int tournamentId = Integer.parseInt(sTournamentId);
		Cookie ck = new Cookie("tournamentId",sTournamentId);
		request.getRequestDispatcher("bgfoto.html").include(request, response);
		
		if(TournamentDAO.checkIfClosed(tournamentId)) {
		
			//Creates arraylist of players
			ArrayList<Integer> players = PairingDAO.insertPlayerIdIntoPlayer(tournamentId);
			//out.print(players+"<br>");
			out.print("<h5>Result form:</h5><br>");
			if(players.size()%2!=0) {
				players.add(0);
			}
			
			int numberOfPlayers = players.size();
	        ArrayList<Integer> players1 = new ArrayList<Integer>();
	        ArrayList<Integer> players2 = new ArrayList<Integer>();
	
	
	        //Inserting values into 1st arraylist
	        for (int i = 0; i < numberOfPlayers / 2; i++) {
	            players1.add(players.get(i));
	        }
	
	        //Inserting values into 2nd arraylist
	        for (int j = numberOfPlayers / 2; j < numberOfPlayers; j++) {
	            players2.add(players.get(j));
	        }
			
	        //out.println(players1+"<br>");
	        //out.println(players2+"<br>");
	        
	        
	        int numberOfRounds = players.size()-1;
	        int start = 1;
	        int matchIndex = 1;
	        
	        while(start<=numberOfRounds) {
	        	out.println("<br>");
	        	out.println("Runda "+start +"<br>");
	        	
	        	//ADD SERVLET NAME!
	        	out.println("<form method=\"post\" action=\"InsertIntoMatch\">");
	        	for (int k = 0; k < numberOfPlayers / 2; k++) {
	                out.println(PlayerDAO.getPlayerNameAndSurname(players1.get(k)) + " VS " + PlayerDAO.getPlayerNameAndSurname(players2.get(k))+" <select required=\"required\" type=\"text\" name=\"winner"+matchIndex+"\"><br>\r\n"
	                		+ "            <option value=\"\" disabled selected>Winner</option>\r\n"
	                		+ "            <option value=\""+players1.get(k)+"\">"+PlayerDAO.getPlayerNameAndSurname(players1.get(k)) +" </option>\r\n"
	                		+ "            <option value=\""+players2.get(k)+"\">" +PlayerDAO.getPlayerNameAndSurname(players2.get(k)) +"</option>\r\n"
	                		+ "</select><br>");
	                //powinno przy okazji insertowac dane do meczu
	                //PairingDAO.insertIntoMatch(players1.get(k), players2.get(k), tournamentId);
	                 matchIndex++;               
	            }
	        	out.println("<br>");
	        	PairingDAO.iterateOneRound(players1, players2, numberOfPlayers);
	        	
	        	
	        	start++;
	        }
	        response.addCookie(ck);
	        out.println("<input type=\"submit\" value=\"Submit Results\"></form>");
		}else {
			
			ck = new Cookie("tournamentId","");
			ck.setMaxAge(0);
			response.addCookie(ck);
			out.println("Tournament is not closed, cannot generate result form");
			request.getRequestDispatcher("ShowTournamentsForAdmin").include(request, response);
			
		}
	}

}




