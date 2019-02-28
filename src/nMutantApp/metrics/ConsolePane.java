package nMutantApp.metrics;

import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class ConsolePane extends JInternalFrame {
	private static final long serialVersionUID = 1L;
	JTextArea textArea = new JTextArea();
	
	public ConsolePane() {
		setTitle("Console");
		setBorder(null);
		textArea.setEditable(false);
		JScrollPane scroll = new JScrollPane(textArea);
		getContentPane().add(scroll);
		setVisible(true);
	}
	
	public void appendText(String text) {
		 SwingUtilities.invokeLater(new Runnable(){ 
             public void run(){    
               textArea.append(text);
               textArea.setCaretPosition(textArea.getText().length() - 1);
             }
         });
	}
}
