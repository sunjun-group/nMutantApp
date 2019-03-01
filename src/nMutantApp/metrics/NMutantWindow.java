package nMutantApp.metrics;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;

import nMutantApp.metrics.MetricsCalculator.MetricsOutputHandler;

public class NMutantWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	InputPane inputPane;
	OutputPane outputPane;
	ConsolePane consolePane;

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
		outputPane = new OutputPane();
		consolePane = new ConsolePane();
		MetricsOutputHandler callback = new MetricsOutputHandler() {
			
			@Override
			public void printOutConsole(String line) {
				consolePane.appendText(line);
			}

			@Override
			public void enterCalculateMetrics() {
				outputPane.textArea.setText("");
				consolePane.textArea.setText("");
			}

			@Override
			public void printOutResult(String output) {
				outputPane.appendText(output);
			}
		};
		inputPane = new InputPane(callback);
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
		inputPane.setMinimumSize(new Dimension(300, 50));
		inputPane.setPreferredSize(new Dimension(300, 50));
		inputPane.setMaximumSize(new Dimension(300, 2147483647));
		
		JSplitPane sp1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, outputPane, consolePane);
		sp1.setResizeWeight(0.65);
		JSplitPane sp2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, inputPane, sp1);
		sp2.setResizeWeight(0.05);
		
		getContentPane().add(sp2);
	}
}
