package wap.DatabaseServlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import wap.DAO.DBconnection;


@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		String nickname = request.getParameter("u_nickname");
		String password = request.getParameter("u_password");
		
		Connection conn = DBconnection.getConn();
		
		boolean st = false;
		try {
			PreparedStatement ps = conn.prepareStatement("select * from user where u_nickname = ? and u_password = ?");
			ps.setString(1, nickname);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			st = rs.next();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(st == true) {		
			out.println("<h1 style=\"color:white\">Welcome " + nickname+"<br></h1>");
			RequestDispatcher rd = request.getRequestDispatcher("indexAfterLogin.html");
			rd.include(request, response);
			
			HttpSession session = request.getSession();
			session.setAttribute("nickname", nickname);
			
			
		}else {
			
			out.println("Username or password incorrect<br>");
			RequestDispatcher rd = request.getRequestDispatcher("Login.jsp");
			rd.include(request, response);
			
		}
		
		out.close();
		
	}

}
