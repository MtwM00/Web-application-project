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

import wap.DAO.PairingDAO;
import wap.DAO.ScoreboardDAO;


@WebServlet("/InsertIntoMatch")
public class InsertIntoMatch extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html"); 
		PrintWriter out = response.getWriter();

		// need to get tournament id from previous servlet , for not its 1 to test

		// need to get tournament id from somewhere
		Cookie ck[] = request.getCookies();
		request.getRequestDispatcher("bgfoto.html").include(request, response);
		int tournamentId = Integer.parseInt(ck[0].getValue());

		if(!PairingDAO.checkIfMatchAlreadyUpdated(tournamentId)) {
		
			// Creates arraylist of players
			ArrayList<Integer> players = PairingDAO.insertPlayerIdIntoPlayer(tournamentId);
			//out.print(players + "<br>");
	
			if(players.size()%2!=0) {
				players.add(0);
			}
			
			int numberOfPlayers = players.size();
			ArrayList<Integer> players1 = new ArrayList<Integer>();
			ArrayList<Integer> players2 = new ArrayList<Integer>();
	
			// Inserting values into 1st arraylist
			for (int i = 0; i < numberOfPlayers / 2; i++) {
				players1.add(players.get(i));
			}
	
			// Inserting values into 2nd arraylist
			for (int j = numberOfPlayers / 2; j < numberOfPlayers; j++) {
				players2.add(players.get(j));
			}
	
			//out.println(players1 + "<br>");
			//out.println(players2 + "<br>");
			out.println("Winners:<br>");
	
			int numberOfRounds = players.size() - 1;
			int round = 1;
			int matchIndex = 1;
			int winner = 0;
			
			while (round <= numberOfRounds) {
				out.println("<br>");
				out.println("Runda " + round + "<br>");
	
				for (int k = 0; k < numberOfPlayers / 2; k++) {
					
					//String stringWinner = request.getParameter("winner" + matchIndex);
					winner = Integer.parseInt(request.getParameter("winner" + matchIndex));
					out.println(winner);
					//tutaj powinno updatowac Winnera
					//PairingDAO.updateWinnerInMatch(winner, players1.get(k), players2.get(k), tournamentId);
					
					//need to check if playerId isnt 0 , because 0 i bye
					if(players1.get(k)==0) {
						PairingDAO.insertIntoMatch(players2.get(k), players2.get(k), winner, tournamentId);
					}else if(players2.get(k)==0) {
						PairingDAO.insertIntoMatch(players1.get(k), players1.get(k), winner, tournamentId);
					}else {
					PairingDAO.insertIntoMatch(players1.get(k), players2.get(k), winner, tournamentId);
					}
					
					//tutaj metoda dodajaca score
					ScoreboardDAO.updateScore(winner, tournamentId);
					
					matchIndex++; 
				}
				out.println("<br>");
				PairingDAO.iterateOneRound(players1, players2, numberOfPlayers);
	
				round++;
			}
			out.println("Results added to database!<br>");
			out.println("<a href='indexAfterLogin.html'>Go back to main page</a>");
			
			
			
			
		}else {
			out.println("<br>Results has already been inserted into this match!");
			request.getRequestDispatcher("ShowTournamentsForAdmin").include(request, response);
		}

		

	}

}
