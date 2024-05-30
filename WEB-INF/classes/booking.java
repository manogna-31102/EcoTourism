import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;

public class booking extends HttpServlet
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    {
        String transport = null, date = request.getParameter("booking_date"), invoice_date = null, name = null, emailid = null, place = null, travel_date = null, r_name = null, r_email = null;
        int persons = Integer.parseInt(request.getParameter("persons")), invoice_id = 0, amount = 0;
        try
        {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:tsuki", "system", "youThot_777");
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from signin_log_ecotourism");
            while(rs.next())
            {
                emailid = rs.getString(1);
            }
            rs = stmt.executeQuery("select sysdate from dual");
            while(rs.next())
            {
                invoice_date = rs.getString(1);
            }
            rs = stmt.executeQuery("select * from invoice_id_ecotourism");
            while(rs.next())
            {
                invoice_id = rs.getInt(1);
            }
            stmt.execute("update invoice_id_ecotourism set id = id + 1");
            rs = stmt.executeQuery("select * from users_ecotourism");
            while(rs.next())
            {
                r_name = rs.getString(1);
                r_email = rs.getString(2);
                if(r_email.equals(emailid))
                {
                    name = r_name;
                    break;
                }
            }
            place = request.getParameter("place");
            transport = request.getParameter("radio-choice");
            travel_date = request.getParameter("travel_date");
            persons = Integer.parseInt(request.getParameter("persons"));
            rs = stmt.executeQuery("select " + transport.toLowerCase() + " from amounts_ecotourism where place=\'" + place.toLowerCase() + "\'");
            while(rs.next())
            {
                amount = rs.getInt(1);
            }
            stmt.execute("delete from orders_ecotourism");
            stmt.execute("insert into orders_ecotourism values(" + (invoice_id+10000) + ",'" + invoice_date + "','" + name + "','" + emailid + "','" + place + "','" + transport + "','" + travel_date + "'," + (amount) + "," + persons + "," + (amount*persons) + ")");
            out.println("<html><head><title><Details></Details></title><link rel=\"stylesheet\" href=\"traveller_details.css\"><link rel=\"stylesheet\" href=\"button.css\"><link rel=\"stylesheet\" href=\"navbar.css\"><link href='https://fonts.googleapis.com/css?family=Kalam' rel='stylesheet'><link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css\"></head><body><nav id=\"navbar\"><div><a href=\"home_inner.html\"><img id=\"logo\" src=\"images/logo.jpg\"></a></div><ul><li><a href=\"home_inner.html\"><i class=\"fa fa-fw fa-home\"></i>Home</a></li><li><a href=\"packages.html\"><i class=\"fa fa-fw fa-briefcase\"></i>Packages</a></li><li><a href=\"home.html\"><i class=\"fa fa-fw fa-user\"></i>SignOut</a></li></ul></nav><div class=\"info\"><h2>Personal Details</h2><div class=\"container\"><form action=\"./invoice\">");
            for(int i=1; i <= persons; i++)
            {
                out.println("<label>Person " + i + ": </label><span>Name</span><input class=\"text\" type=\"text\" name=\"name_" + i + "\"><span>Age</span><input class=\"text\" type=\"number\" name=\"age_" + i + "\"><span>Aadhar</span><input class=\"text\" type=\"number\" name=\"aadhar_" + i + "\"><br><br>");
            }
            out.println("<center><input type=\"submit\" value=\"Submit\" class=\"button\"></center></form></div></body><script>var prevScrollpos = window.pageYOffset;window.onscroll = function() {var currentScrollPos = window.pageYOffset;if (prevScrollpos > currentScrollPos) {document.getElementById(\"navbar\").style.top = \"0\";} else {document.getElementById(\"navbar\").style.top = \"-70px\";}prevScrollpos = currentScrollPos;}</script></html>");
            stmt.close();
            con.close();
        }
        catch(Exception e){try{PrintWriter out = response.getWriter();out.println(e);}catch(Exception e1){}}
    }
}
