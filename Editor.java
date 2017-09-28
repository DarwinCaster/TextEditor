import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Editor extends JPanel implements ActionListener {
	File file;
	JButton save = new JButton("Save");
	JButton saveClose = new JButton("Save and Close");
	JTextArea text = new JTextArea(20, 40);

	public Editor(String s) throws IOException {
		file = new File(s);
		save.addActionListener(this);
		saveClose.addActionListener(this);
		if (file.exists()) {
			try {
				BufferedReader input = new BufferedReader(new FileReader(file));
				String line = input.readLine();
				while (line != null) {
					text.append(line + "\n");
					line = input.readLine();
				}
				input.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		add(save);
		add(saveClose);
		add(text);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

		try {
			FileWriter out = new FileWriter(file);
			out.write(text.getText());
			out.close();
			if(arg0.getSource() == saveClose){
				Login login = (Login) getParent();
				login.cl.show(login, "fb");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}