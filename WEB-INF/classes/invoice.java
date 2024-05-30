import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.ArrayList;

public class invoice extends HttpServlet
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    {
        String invoice_date = null, name = null, emailid = null, place = null, transport = null, travel_date = null, traveller_name = null, traveller_aadhar = null;
        int invoice_id = 0, amount = 0, persons = 0, total = 0, traveller_age = 0;
        try
        {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:tsuki", "system", "youThot_777");
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from orders_ecotourism");
            while(rs.next())
            {
                invoice_id = rs.getInt(1);
                invoice_date = rs.getString(2);
                name = rs.getString(3);
                emailid = rs.getString(4);
                place = rs.getString(5);
                transport = rs.getString(6);
                travel_date = rs.getString(7);
                amount = rs.getInt(8);
                persons = rs.getInt(9);
                total = rs.getInt(10);
            }
            out.println("<html><head><title>Invoice</title><link rel=\"stylesheet\" href=\"invoice.css\"></head><body><header><h1>Invoice</h1></header><article><address contenteditable><p align=\"left\"><img src=\"images/logo.jpg\"></p></address><table class=\"meta\"><tr><th><span contenteditable>Invoice Id</span></th><td><span contenteditable>" + invoice_id + "</span></td></tr><tr><th><span contenteditable>Invoice Date</span></th><td><span contenteditable>" + invoice_date + "</span></td></tr><tr><th><span contenteditable>Customer Name</span></th><td><span contenteditable>" + name + "</span></td></tr><tr><th><span contenteditable>Customer Email</span></th><td><span contenteditable>" + emailid + "</span></td></tr></table><table class=\"inventory\"><thead><tr><th>Package</th><th>Items Included</th><th>Travel Date</th><th>Price per head</th></tr></thead><tbody><tr><td rowspan=\"2\"><span contenteditable>" + place + "</span></td><td><span contenteditable>" + transport + "</span></td><td rowspan=\"2\"><span contenteditable>" + travel_date + "</span></td><td rowspan=\"2\"><span contenteditable>Rs.</span><span contenteditable>" + (amount) + "/-</span></td></tr><tr><td><span contenteditable>Hotel</span></td></tr></tbody></table><table class=\"inventory\"><thead><tr><th>Person Name</th><th>Age</th><th>Aadhar Card Number</th></tr></thead><tbody>");
            for(int i = 1; i <= persons; i++)
            {
                traveller_name = request.getParameter("name_" + i);
                traveller_age = Integer.parseInt(request.getParameter("age_" + i));
                traveller_aadhar = request.getParameter("aadhar_" + i);
                out.println("<tr><td><span contenteditable>" + traveller_name + "</span></td><td ><span contenteditable>" + traveller_age + "</span></td><td ><span contenteditable>" + traveller_aadhar + "</span></td></tr>");
            }
            out.println("</tbody></table><table class=\"total\"><tr><th><span contenteditable>No.of Persons</span></th><td><span contenteditable>" + persons + "</span></td></tr><tr><th>Total</span></th><td><span contenteditable data-prefix>Rs.</span><span contenteditable>" + total + "/-</span></td></tr></table></article><div id=\"thankyou\"><p>Thank you for choosing Ecotourism<br><br>Have a nice journey!</p></div><div id=\"footer\" align=\"center\"><p>This is an auto generated invoice. All the package details have been mailed to you.<br>Click the button below to print this page.</p><br><input class=\"button\" type=\"submit\" id=\"print\" value=\"Print\" onclick=\"window.print()\"></input></div></body></html>");
            stmt.close();
            con.close();
        }
        catch(Exception e){try{PrintWriter out = response.getWriter();out.println(e);}catch(Exception e1){}}
    }
}
