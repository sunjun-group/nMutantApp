package nMutantApp.metrics;

import javax.swing.JInternalFrame;
import javax.swing.JTextArea;

public class ConsolePane extends JInternalFrame {
	private static final long serialVersionUID = 1L;
	JTextArea textArea = new JTextArea();
	
	public ConsolePane() {
		setTitle("Console");
		setBorder(null);
		
		textArea.setEditable(false);
		getContentPane().add(textArea);
		
		setVisible(true);
	}
}
