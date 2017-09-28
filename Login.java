import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends JPanel implements ActionListener {
	JLabel userNameL = new JLabel("Username: ");
	JTextField userNameTF = new JTextField();
	JLabel passWordL = new JLabel("Password: ");
	JPasswordField passWordTF = new JPasswordField();
	JPanel loginP = new JPanel(new GridLayout(3, 2));
	JPanel panel = new JPanel();
	JButton login = new JButton("Login");
	JButton register = new JButton("Register");
	CardLayout cl;

	Login() {
		setLayout(new CardLayout());
		loginP.add(userNameL);
		loginP.add(userNameTF);
		loginP.add(passWordL);
		loginP.add(passWordTF);
		login.addActionListener(this);
		register.addActionListener(this);
		loginP.add(login);
		loginP.add(register);
		panel.add(loginP);
		add(panel, "login");
		cl = (CardLayout) getLayout();

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getSource() == login){
			try {
				BufferedReader input = new BufferedReader(new FileReader("passwords.txt"));
				String pass = null;
				String line = input.readLine();
				while(line != null){
					StringTokenizer st = new StringTokenizer(line);
					if(userNameTF.getText().equals(st.nextToken()))
					pass = st.nextToken();
					line = input.readLine();
				}
				input.close();
				MessageDigest md = MessageDigest.getInstance("SHA-256");
				md.update(new String(passWordTF.getPassword()).getBytes());
				byte byteData[] = md.digest();
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < byteData.length; i++) 
					sb.append(Integer.toString((byteData[i] & 0xFF) + 0x100, 16).substring(1));
				if(pass.equals(sb.toString())){
					add(new Browser(userNameTF.getText()), "fb");
					cl.show(this, "fb");
				}
					
				
				
			}
				catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NoSuchAlgorithmException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if (arg0.getSource() == register) {

			add(new Register(), "register");
			cl.show(this, "register");

		}
	}

	public static void main(String[] args) throws IOException {
		JFrame frame = new JFrame("Text Editor");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 500);
		Login login = new Login();
		frame.add(login);
		frame.setVisible(true);
	}
}