import java.io.IOException;
import java.io.PrintWriter;
import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.*;
import java.sql.*;

public class signin_forgot extends HttpServlet
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    {
        try
        {
            String emailid = request.getParameter("emailid"), question = null, username = null, r_name, r_email, r_password, r_question;
            Cookie email_cookie = new Cookie("emailid", emailid);
            email_cookie.setMaxAge(60*60*24);
            response.addCookie(email_cookie);
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:tsuki", "system", "youThot_777");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from users_ecotourism");
            while(rs.next())
            {
                r_name = rs.getString(1);
                r_email = rs.getString(2);
                r_password = rs.getString(3);
                r_question = rs.getString(4);
                if(r_email.equals(emailid))
                {
                    username = r_name;
                    question = r_question;
                }
            }
            stmt.close();
            con.close();
            if(question == null)
            {
                out.println("<html style=\"font-family: Kalam;\"><head></title><link rel=\"stylesheet\" href=\"button.css\"><link rel=\"stylesheet\" href=\"navbar.css\"><link href='https://fonts.googleapis.com/css?family=Kalam' rel='stylesheet'></head><nav id=\"navbar\"><div><a href=\"home.html\"><img id=\"logo\" src=\"images/logo.jpg\"></a></div><ul><li><a href=\"signin.html\"><i class=\"fa fa-fw fa-user\"></i>SignIn</a></li></ul></nav>");
                out.println("<div align=\"center\" style=\"margin-top: 250px;\">");
                out.println("<h2> Invalid Email ID. </h2>");
                out.println("<h3> Click the Button below to try again. </h3>");
                out.println("<a href=\"./signin.html\"> <input class=\"button\" type=\"button\" value=\"Go\"> </a>");
                out.println("</div>");
                out.println("</html>");
            }
            else
            {
                out.println("<html style=\"font-family: Kalam;\"><head></title><link rel=\"stylesheet\" href=\"button.css\"><link rel=\"stylesheet\" href=\"navbar.css\"><link href='https://fonts.googleapis.com/css?family=Kalam' rel='stylesheet'></head><nav id=\"navbar\"><div><a href=\"home.html\"><img id=\"logo\" src=\"images/logo.jpg\"></a></div><ul><li><a href=\"signin.html\"><i class=\"fa fa-fw fa-user\"></i>SignIn</a></li></ul></nav>");
                out.println("<div align=\"center\" style=\"margin-top: 250px;\">");
                out.println("<h2> Hello " + username + ". </h2>");
                out.println("<h3> Your security question is: \"" + question + "?\" </h3>");
                out.println("<h3> Please enter your answer for the security question to proceed further. </h3>");
                out.println("<form action=\"./signin_forgot_validate\"> <input type=\"text\" name=\"answer\"> <input class=\"button\" type=\"submit\" value=\"Go\"> </form>");
                out.println("</div>");
                out.println("</html>");
            }
        }
        catch(Exception e){try{PrintWriter out = response.getWriter();out.println(e);}catch(Exception e1){}}
    }
}
