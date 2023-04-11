package wap.DatabaseServlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import wap.DAO.TournamentDAO;

/**
 * Servlet implementation class CloseTournament
 */
@WebServlet("/CloseTournament")
public class CloseTournament extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		
		int tournamentId = Integer.parseInt(request.getParameter("idTournament"));
		
		if(TournamentDAO.checkIfClosed(tournamentId)) {
			out.println("Tournament is already closed!");
			request.getRequestDispatcher("indexAfterLogin.html").include(request, response);
		}else {
			TournamentDAO.closeTournament(tournamentId);
			out.println("Tournament closed successfully!");
			request.getRequestDispatcher("indexAfterLogin.html").include(request, response);
		}
	}

}
