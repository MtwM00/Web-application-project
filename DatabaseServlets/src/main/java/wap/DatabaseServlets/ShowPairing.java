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
import wap.DAO.PlayerDAO;

/**
 * Servlet implementation class ShowPairing
 */
@WebServlet("/ShowPairing")
public class ShowPairing extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowPairing() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html"); 
		PrintWriter out = response.getWriter();
		request.getRequestDispatcher("bgfoto.html").include(request, response);
		
		//need to get tournament id from somewhere
		 
		
		Cookie ck[] = request.getCookies();
		int tournamentId = Integer.parseInt(ck[0].getValue());
		
		//Creates arraylist of players
		ArrayList<Integer> players = PairingDAO.insertPlayerIdIntoPlayer(tournamentId);
		//out.print(players+"<br>");
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
        int round = 1;
        
        
        while(round<=numberOfRounds) {
        	out.println("<br>");
        	out.println("Runda "+round +"<br>");
        	        	
        	
        	for (int k = 0; k < numberOfPlayers / 2; k++) {
                out.println(PlayerDAO.getPlayerNameAndSurname(players1.get(k)) + " VS " + PlayerDAO.getPlayerNameAndSurname(players2.get(k))+"<br>");
                
                                
            }
        	out.println("<br>");
        	PairingDAO.iterateOneRound(players1, players2, numberOfPlayers);
        	
        	
        	round++;
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
