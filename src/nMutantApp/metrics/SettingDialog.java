package nMutantApp.metrics;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.commons.io.IOUtils;

import nMutantApp.ProjectConfiguration;
import nMutantApp.components.FileInput;
import nMutantApp.components.JComponentFactory;

public class SettingDialog extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final String settingFile = ProjectConfiguration.getAbsolutePath("/nmutant.conf");
	public static final String PY_HOME_KEY = "Python_home:";
	FileInput fileInput;
	
	public SettingDialog() {
		super();
		setTitle("Settings");
		decorate();
	}

	public void open() {
		fileInput.getTextField().setText(ProjectConfiguration.pythonHome);
		setVisible(true);
	}

	private void decorate() {
		setSize(500, 150);
		
		JPanel panel = new JPanel(new GridBagLayout());
		getContentPane().add(panel, BorderLayout.NORTH);
		fileInput = new FileInput("Python Executable: ", false, SettingDialog.this);
		
		panel.add(fileInput.getLbl(), JComponentFactory.gbc(0, 0, 1));
		panel.add(fileInput.getTextField(), JComponentFactory.gbc(1, 0, 3));
		panel.add(fileInput.getBtnBrowse(), JComponentFactory.gbc(4, 0, 1));
		
		JPanel bottomPanel = new JPanel(new GridBagLayout());
		JLabel lbl = new JLabel("");
		bottomPanel.add(lbl, JComponentFactory.gbc(0, 0, 3));
		JButton btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				writeFile(settingFile, PY_HOME_KEY + fileInput.getTextField().getText(),
						false);
				ProjectConfiguration.pythonHome = fileInput.getTextField().getText();
				SettingDialog.this.dispose();
			}
		});
		bottomPanel.add(btnOk, JComponentFactory.gbc(2, 0, 1));
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SettingDialog.this.dispose();
			}
		});
		bottomPanel.add(btnCancel, JComponentFactory.gbc(3, 0, 1));
		getContentPane().add(bottomPanel, BorderLayout.SOUTH);
		
	}

	public static void writeFile(String file, String log, boolean append) {
		if (log == null) {
			return;
		}
		FileWriter writer = null;
		try {
			writer = new FileWriter(file, append);
			writer.write(log);
		} catch (Exception e) {
			// ignore
		} finally {
			IOUtils.closeQuietly(writer);
		}

	}
}
