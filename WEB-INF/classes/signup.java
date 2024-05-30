import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;

public class signup extends HttpServlet
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    {
        String username = request.getParameter("name"), emailid = request.getParameter("emailid"), password = request.getParameter("password"), question = request.getParameter("question"), answer = request.getParameter("answer"), r_email;
        try
        {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:tsuki", "system", "youThot_777");
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from users_ecotourism");
            boolean exists = false;
            while(rs.next())
            {
                r_email = rs.getString(2);
                if(r_email.equals(emailid))
                {
                    exists = true;
                    break;
                }
            }
            if(!exists)
            {
                PreparedStatement pstmt = con.prepareStatement("insert into users_ecotourism values(?,?,?,?,?)");
                pstmt.setString(1,username);
                pstmt.setString(2,emailid);
                pstmt.setString(3,password);
                pstmt.setString(4,question);
                pstmt.setString(5,answer);
                pstmt.execute();
                pstmt.close();
                stmt.close();
                con.close();
                out.println("<html style=\"font-family: Kalam;\"><head></title><link rel=\"stylesheet\" href=\"button.css\"><link rel=\"stylesheet\" href=\"navbar.css\"><link href='https://fonts.googleapis.com/css?family=Kalam' rel='stylesheet'></head><nav id=\"navbar\"><div><a href=\"home.html\"><img id=\"logo\" src=\"images/logo.jpg\"></a></div><ul><li><a href=\"signin.html\"><i class=\"fa fa-fw fa-user\"></i>SignIn</a></li></ul></nav>");
                out.println("<div align=\"center\" style=\"margin-top: 250px;\">");
                out.println("<h2> Hello " + username + ". </h2>");
                out.println("<h3> You have successfully Signed Up. Click the Button below to Sign In. </h3>");
                out.println("<a href=\"./signin.html\"> <input class=\"button\" type=\"button\" value=\"Go\"> </a>");
                out.println("</div>");
                out.println("</html>");
            }
            else
            {
                out.println("<html style=\"font-family: Kalam;\"><head></title><link rel=\"stylesheet\" href=\"button.css\"><link rel=\"stylesheet\" href=\"navbar.css\"><link href='https://fonts.googleapis.com/css?family=Kalam' rel='stylesheet'></head><nav id=\"navbar\"><div><a href=\"home.html\"><img id=\"logo\" src=\"images/logo.jpg\"></a></div><ul><li><a href=\"signin.html\"><i class=\"fa fa-fw fa-user\"></i>SignIn</a></li></ul></nav>");
                out.println("<div align=\"center\" style=\"margin-top: 250px;\">");
                out.println("<h2> User with EmailID already Exists. </h2>");
                out.println("<h3> Click the Button below to try again. </h3>");
                out.println("<a href=\"./signin.html\"> <input class=\"button\" type=\"button\" value=\"Go\"> </a>");
                out.println("</div>");
                out.println("</html>");
            }
        }
        catch(Exception e){try{PrintWriter out = response.getWriter();out.println(e);}catch(Exception e1){}}
    }
}
