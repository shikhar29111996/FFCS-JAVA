import java.sql.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.applet.*;
import java.awt.*;
public class Viewmarks {
    Connection con;
    Statement st;
    ResultSet rs;
    JFrame f=new JFrame("Marks");
        JLabel l1=new JLabel("Marks ");
        JTextField t1=new JTextField(25);
        
    public Viewmarks()
    {
        connect();
        frame();
    }
    
    public void frame()
    {
        
            f.setSize(400, 400);
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    f.setVisible(true);
	    JPanel p=new JPanel();
            p.add(l1);
            p.add(t1);
            
            f.add(p);
        }
        public void connect()
        {
            try
		{
		con =DriverManager.getConnection("jdbc:mysql://localhost:1527/Mini","root","root");
		 st =con.createStatement();
		 rs=st.executeQuery("select MARK from MARKS where REGISTERNO='"+Slogin.s+"';");
		 
			 //stu1.setText(rs.getString("subject1")+"  "+rs.getString("subject2")+"  "+rs.getString("subject3")+"  "+rs.getString("subject4")+"  "+rs.getString("subject5"));
				t1.setText(Integer.toString(rs.getInt("MARK")));
				
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
        }
    }

