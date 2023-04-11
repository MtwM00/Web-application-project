package wap.DatabaseServlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import wap.DAO.DBconnection;
import wap.DAO.TournamentDAO;
import wap.DAO.UserDAO;


@WebServlet("/CreateTournament")
public class CreateTournament extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public CreateTournament() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		HttpSession session = request.getSession(false);
		
		String sesja = (String) session.getAttribute("nickname");
		int user_idUser = UserDAO.getUserId(sesja);
		TournamentDAO.insertTournamentOrganizer(user_idUser);
		int organizerId = TournamentDAO.getTournamentOrganizerId(user_idUser);
		
		String name = request.getParameter("t_name");
		String type = request.getParameter("t_type");
		String ranked = request.getParameter("t_if_ranked");
		Date date = Date.valueOf(request.getParameter("t_date"));
		String code = request.getParameter("t_code");
		int judgeId = Integer.parseInt(request.getParameter("Judge_idJudge"));
		
		int result = TournamentDAO.createTournament(name, type, ranked, date, code, judgeId,organizerId);
		
		if(result !=0) {
			out.println("Dodano turniej!<br>");
			request.getRequestDispatcher("indexAfterLogin.html").include(request, response);
			
		}else {
			out.println("Blad!<br>");
			out.println();
			out.println(sesja);
			request.getRequestDispatcher("TournamentCreation.jsp").include(request, response);
		}
		
		//TODO - zeby wpisywac torunament id potrzebuje z sesji pobrac userID
		
		
	}

}
