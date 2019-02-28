package nMutantApp.metrics;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;

public class NMutantWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	InputPane inputPane = new InputPane();
	OutputPane outputPane = new OutputPane();
	ConsolePane consolePane = new ConsolePane();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NMutantWindow window = new NMutantWindow();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public NMutantWindow() {
		initialize();
		setDefaultValue();
	}

	private void setDefaultValue() {
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setTitle("nMutant");
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JMenu mnSettings = new JMenu("Settings");
		menuBar.add(mnSettings);
		
		JMenuItem mntmOpenSettings = new JMenuItem("Open Settings");
		mnSettings.add(mntmOpenSettings);
		mntmOpenSettings.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SettingDialog settingsDialog = new SettingDialog();
				settingsDialog.open();
			}
		});
		/* content */
		inputPane.setMinimumSize(new Dimension(200, 50));
		inputPane.setPreferredSize(new Dimension(200, 50));
		inputPane.setMaximumSize(new Dimension(200, 2147483647));
		
		JSplitPane sp1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, outputPane, consolePane);
		sp1.setResizeWeight(0.65);
		JSplitPane sp2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, inputPane, sp1);
		sp2.setResizeWeight(0.05);
		
		getContentPane().add(sp2);
	}
}
