package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class netbanking
 */
@WebServlet("/netbanking")
public class netbanking extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public netbanking() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String date=new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(Calendar.getInstance().getTime());
		/*
		 * Date date=new Date(0); java.sql.Date sdate=new java.sql.Date(date.getTime());
		 */
		 response.setContentType("text/html");
		 String bank=request.getParameter("bank");
	     String username=request.getParameter("username");
	     String password=request.getParameter("password");
	     HttpSession session=request.getSession();
	        String a11= (String) session.getAttribute("id");
	        String k="NetBanking";
	     
	     PrintWriter pw=response.getWriter();
			int i=0;
			 int mi = 65626;
				int ma=86561;
				Random rn = new Random();
				int dna=rn.nextInt(100000);
			  try {

		            Class.forName("com.mysql.jdbc.Driver");  
		            Connection con=DriverManager.getConnection(  
		            		"jdbc:mysql://localhost:3306/project","root","Pavan@2010");
		            PreparedStatement stmt2=con.prepareStatement("select * from cart where userid="+a11+"");
		            ResultSet rs1=stmt2.executeQuery();
		            
		            PreparedStatement stmt=con.prepareStatement("Insert into netbanking(bank,username,password,Date,paymentmode) values(?,?,?,?,?);");
		stmt.setString(1, bank);
		stmt.setString(2, username);
		stmt.setString(3, password);
		stmt.setString(4, date);
		stmt.setString(5, k);
		i=stmt.executeUpdate();


		if(i>0) {
		      pw.println("<html><body><b>Payment Done Successfully</b></body></html>");
		      RequestDispatcher rd=request.getRequestDispatcher("orderplaced.jsp");
		      rd.include(request, response);
		      
		     
			  }
		PreparedStatement stmt3=con.prepareStatement("insert into orders(orderid,productid,productname,price,quantity,userid,date,paymentmode) values(?,?,?,?,?,?,?,?);");
		while(rs1.next()) {
			stmt3.setInt(1, dna);
			stmt3.setInt(2,rs1.getInt(1));
			stmt3.setString(3, rs1.getString(3));
			stmt3.setInt(4, rs1.getInt(4));
			stmt3.setInt(5, rs1.getInt(5));
			stmt3.setString(6, rs1.getString(7));
		    stmt3.setString(7,date);
		    stmt3.setString(8, "NetBanking");
			stmt3.executeUpdate();
			
		}

		PreparedStatement stmt4=con.prepareStatement("truncate cart");
	    stmt4.executeUpdate();

	stmt.close();
		con.close();	
	

}catch(Exception e) {
		          pw.println("<html><body><b>Payment Failed</b></body></html>");
		          RequestDispatcher rd=request.getRequestDispatcher("orderfailed.jsp");
		          rd.include(request, response);
		 

		        System.out.println(e);
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
