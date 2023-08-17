package ChattingApplication;
import java.awt.BorderLayout;

//import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;
import java.util.Calendar;
import java.text.*;
import java.net.*;
import java.io.*;

public class Server implements ActionListener{
	
	JTextField text;
	JPanel a1;
	static Box vertical= Box.createVerticalBox();
	static JFrame t= new JFrame();
	static DataOutputStream dout;
	
	Server(){
		
		t.setLayout(null);
		
		JPanel p1= new JPanel();
		p1.setBackground(new Color(7,138,94));
		p1.setBounds(0,0,400,70);
		p1.setLayout(null);
		t.add(p1);
		
		ImageIcon i1= new ImageIcon(ClassLoader.getSystemResource("icons/back.png"));
		Image i2= i1.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
		ImageIcon i3= new ImageIcon(i2);
		JLabel back = new JLabel(i3);
		back.setBounds(5,20,30,30);	
		p1.add(back);
		
		back.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent ae) {
				
				System.exit(0);
			}
		});
		
		
		ImageIcon i4= new ImageIcon(ClassLoader.getSystemResource("icons/A.jpg"));
		Image i5= i4.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT);
		ImageIcon i6= new ImageIcon(i5);
		JLabel profile = new JLabel(i6);
		profile.setBounds(35,3,70,70);	
		p1.add(profile);
		
		ImageIcon i7= new ImageIcon(ClassLoader.getSystemResource("icons/videocamera.png"));
		Image i8= i7.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
		ImageIcon i9= new ImageIcon(i8);
		JLabel videocamera = new JLabel(i9);
		videocamera.setBounds(270,20,30,30);	
		p1.add(videocamera);
		
		ImageIcon i10= new ImageIcon(ClassLoader.getSystemResource("icons/camera.png"));
		Image i11= i10.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
		ImageIcon i12= new ImageIcon(i11);
		JLabel camera = new JLabel(i12);
		camera.setBounds(330,20,30,30);	
		p1.add(camera);
		
		ImageIcon i13= new ImageIcon(ClassLoader.getSystemResource("icons/menu.png"));
		Image i14= i13.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
		ImageIcon i15= new ImageIcon(i14);
		JLabel menu = new JLabel(i15);
		menu.setBounds(370,20,30,30);	
		p1.add(menu);
		
		JLabel name= new JLabel("peter1");
		name.setBounds(110,10,100,20);
		name.setForeground(Color.WHITE);
		name.setFont(new Font("Tahoma",Font.BOLD,16));
		p1.add(name);
		
		JLabel status= new JLabel("Active");
		status.setBounds(110,35,100,20);
		status.setForeground(Color.WHITE);
		status.setFont(new Font("Tahoma",Font.BOLD,12));
		p1.add(status);
		
		a1= new JPanel();
		a1.setBounds(5, 75, 430, 600);
		a1.setBackground(Color.WHITE);
		a1.setLayout(new BoxLayout(a1, BoxLayout.Y_AXIS));
		t.add(a1);
		
		text= new JTextField();
		text.setBounds(10,700,310,35);
		text.setFont(new Font("Tahoma",Font.PLAIN,13));
		t.add(text);
		
		JButton send= new JButton("Send");
		send.setBounds(330, 700, 68, 35);
		send.setBackground(new Color(7,138,94));
		send.setForeground(Color.WHITE);
		send.setFont(new Font("SAN_SERIF",Font.PLAIN,13));
		send.addActionListener(this);
		t.add(send);
		
		
		t.setSize(400,750);
		t.setLocation(200,70);
		t.setUndecorated(true);
		t.getContentPane().setBackground(Color.WHITE);
		t.setVisible(true);
		
	}
	
	public void actionPerformed(ActionEvent ae) {
		
		String out = text.getText();
		//JLabel output= new JLabel(out);
	
		
		JPanel p2= FormatLabel(out);
		//p2.add(output);
		a1.setLayout(new BorderLayout());
		
		
		JPanel right= new JPanel(new BorderLayout());
		right.add(p2,BorderLayout.LINE_END);
		vertical.add(right);
		vertical.add(Box.createHorizontalStrut(8));
		
		a1.add(vertical,BorderLayout.PAGE_START);
		
		try {
			dout.writeUTF(out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 text.setText("");
		
		t.repaint();
		t.invalidate();
		t.validate();
	}
	
	public static JPanel FormatLabel(String out) {
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		
		JLabel output = new JLabel("<html><p style=\"width: 150px\">" +out+ "</p></html>");
		output.setPreferredSize(new Dimension(200, 20));	
		output.setFont(new Font("Tahoma",Font.PLAIN,14));
		output.setBackground(new Color(37,211,102));
		output.setOpaque(true);
		output.setBorder(new EmptyBorder(15,15,15,15));
		panel.add(output);
		
		Calendar cal= Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		
		JLabel time= new JLabel();
		time.setText(sdf.format(cal.getTime()));
		panel.add(time);
		
		return panel;
	}
	
	public static void main(String args[]) {
		
		new Server();
		
		try {  
			ServerSocket skt= new ServerSocket(6001);
			
			while(true) {
				
				Socket s= skt.accept();
				DataInputStream din= new DataInputStream(s.getInputStream());
				
				dout= new DataOutputStream(s.getOutputStream());
				
				while(true) {
					
					String msg= din.readUTF();
					JPanel panel= FormatLabel(msg);
					JPanel left= new JPanel(new BorderLayout());
					left.add(panel,BorderLayout.LINE_START);
					vertical.add(left);
					t.validate();
				}
			}
			
		}
		
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}
