import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class Browser extends JPanel implements ActionListener {
	JLabel label = new JLabel("File List");
	JButton newFile = new JButton("New File");
	JButton openFile = new JButton("Open File");
	JTextField newFileTF = new JTextField(10);
	ButtonGroup bg;
	File directory;

	public Browser(String dir) {
		directory = new File(dir);
		directory.mkdir();
		JPanel fileList = new JPanel(new GridLayout(directory.listFiles().length + 3, 1));
		fileList.add(label);
		bg = new ButtonGroup();
		for (File file : directory.listFiles()) {
			JRadioButton radio = new JRadioButton(file.getName());
			radio.setActionCommand(file.getName());
			bg.add(radio);
			fileList.add(radio);
		}
		JPanel newP = new JPanel();
		newP.add(newFileTF);
		newP.add(newFile);
		newFile.addActionListener(this);
		openFile.addActionListener(this);
		fileList.add(openFile);
		fileList.add(newP);
		add(fileList);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		Login login = (Login) getParent();
		if (arg0.getSource() == openFile){
			try {
				login.add(new Editor(directory.getName()+"\\"+bg.getSelection().getActionCommand()), "editor");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			login.cl.show(login, "editor");
		}
		if (arg0.getSource() == newFile){
			String file = directory.getName()+"\\"+newFileTF.getText()+".txt";
			if(newFileTF.getText().length() >0 && !(new File(file).exists()));
			try {
				login.add(new Editor(file), "Editor");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			login.cl.show(login, "editor");
		}

	}

}