import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;

public class signin extends HttpServlet
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    {
        try
        {
            String username = null, emailid = request.getParameter("emailid"), password = request.getParameter("password"), r_name, r_email, r_password;
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
                if(r_email.equals(emailid) && r_password.equals(password))
                {
                    username = r_name;
                    break;
                }
            }
            if(username == null)
            {
                out.println("<html style=\"font-family: Kalam;\"><head></title><link rel=\"stylesheet\" href=\"button.css\"><link rel=\"stylesheet\" href=\"navbar.css\"><link href='https://fonts.googleapis.com/css?family=Kalam' rel='stylesheet'></head><nav id=\"navbar\"><div><a href=\"home.html\"><img id=\"logo\" src=\"images/logo.jpg\"></a></div><ul><li><a href=\"signin.html\"><i class=\"fa fa-fw fa-user\"></i>SignIn</a></li></ul></nav>");
                out.println("<div align=\"center\" style=\"margin-top: 250px;\">");
                out.println("<h2> Invalid Email ID or Password. </h2>");
                out.println("<h3> Click the Button below to try again. </h3>");
                out.println("<a href=\"./signin.html\"> <input class=\"button\" type=\"button\" value=\"Go\"> </a>");
                out.println("</div>");
                out.println("</html>");
            }
            else
            {
                out.println("<html style=\"font-family: Kalam;\"><head></title><link rel=\"stylesheet\" href=\"button.css\"><link rel=\"stylesheet\" href=\"navbar.css\"><link href='https://fonts.googleapis.com/css?family=Kalam' rel='stylesheet'></head><nav id=\"navbar\"><div><a href=\"home_inner.html\"><img id=\"logo\" src=\"images/logo.jpg\"></a></div><ul><li><a href=\"home_inner.html\"><i class=\"fa fa-fw fa-home\"></i>Home</a></li><li><a href=\"packages.html\"><i class=\"fa fa-fw fa-briefcase\"></i>Packages</a></li><li><a href=\"home.html\"><i class=\"fa fa-fw fa-user\"></i>SignOut</a></li></ul></nav>");
                out.println("<div align=\"center\" style=\"margin-top: 250px;\">");
                out.println("<h2> Hello " + username + ". </h2>");
                out.println("<h3> You are successfully Signed In. Click the Button below to proceed. </h3>");
                stmt.execute("delete from signin_log_ecotourism");
                stmt.execute("insert into signin_log_ecotourism values(\'" + emailid + "\')");
                out.println("<a href=\"./home_inner.html\"> <input class=\"button\" type=\"button\" value=\"Go\"> </a>");
                out.println("</div>");
                out.println("</html>");
            }
            con.close();
            stmt.close();
        }
        catch(Exception e){try{PrintWriter out = response.getWriter();out.println(e);}catch(Exception e1){}}
    }
}
