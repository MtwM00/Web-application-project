package wap.DatabaseServlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import wap.DAO.DBconnection;
import wap.DAO.Validate;


@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public RegisterServlet() {
        super();
        
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		Connection conn = DBconnection.getConn();
		
		
		String email = request.getParameter("u_mail");
		String name = request.getParameter("u_name");
		String surname = request.getParameter("u_surname");
		String nickname = request.getParameter("u_nickname");
		String country = request.getParameter("u_country");	
		Date birthDate = Date.valueOf(request.getParameter("u_birth_date"));
		int elo =  Integer.parseInt(request.getParameter("u_elo"));
		String password = request.getParameter("u_password");
		String confirmPassword = request.getParameter("u_confirm_password");
				
		
		//if validate = false there are no duplicates and servlet can insert data into database
		if(!Validate.checkInput(email, nickname)) {
		
		
		try {
			
			PreparedStatement ps = conn.prepareStatement("insert into user values (null,?,?,?,?,?,?,?,?)");
			
			ps.setString(1, email);
			ps.setString(2, name);
			ps.setString(3, surname);
			ps.setString(4, nickname);
			ps.setString(5, country);
			ps.setDate(6, birthDate);
			ps.setInt(7, elo);
			ps.setString(8, password);
			
			int i = ps.executeUpdate();
			out.println("test");
			if(i>0) {
				out.println("Successfully registered!");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}else {
			out.println("Email or nickname already ");
			
			RequestDispatcher rd = request.getRequestDispatcher("Register.html");
			rd.include(request, response);
		}
		
		
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		doPost(request, response);
		
	}
	
}
