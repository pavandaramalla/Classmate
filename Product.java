package servlet;

import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Addtocart")
public class Product extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		  response.setContentType("text/html");	
		  int Productid=Integer.parseInt(request.getParameter("id"));
		  String Name=request.getParameter("name");
		 int Quantity=Integer.parseInt(request.getParameter("quantity"));		
		 int Price=Integer.parseInt(request.getParameter("price"));

	HttpSession session=request.getSession();

String sql1="update cart set Total=price*quantity";

String a= (String) session.getAttribute("id");


	  try {
	   String jdbcURL="jdbc:mysql://localhost:3306/project";
	  String jdbcUsername="root"; 
	   String jdbcPassword="Pavan@2010";
	  Connection con=DriverManager.getConnection(jdbcURL,jdbcUsername,jdbcPassword);
	 
	  PreparedStatement ps=con.prepareStatement("insert into cart(plantid,plantname,price,quantity,Userid) values(?,?,?,?,?); "); 

	  ps.setInt(1,Productid);
	  ps.setString(2,Name);
	  ps.setInt(3,Price);
	  ps.setInt(4,Quantity);
	  ps.setString(5, a);
	  PreparedStatement ps1=con.prepareStatement(sql1);
ps.executeUpdate();



	  PreparedStatement pss=con.prepareStatement("select count(*) from cart where Userid="+a+"");				  
	    ResultSet rs2 = pss.executeQuery();			  
	     if(rs2.next()) {
	  int  count = rs2.getInt(1);
	    session.setAttribute("count",count);
	     }
	  
	  
	ps1.executeUpdate();	 
	 
	RequestDispatcher rs=request.getRequestDispatcher("summer.jsp");
	  rs.forward(request,response);
	  }catch(Exception e) {
		  RequestDispatcher rs=request.getRequestDispatcher("home.jsp");
		  rs.forward(request,response);
	  }	
	} 	  	 
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
