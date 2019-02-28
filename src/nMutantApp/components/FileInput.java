package nMutantApp.components;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;

import sav.common.core.utils.StringUtils;

public class FileInput {
	JLabel lbl;
	JTextField textField = new JTextField();
	JButton btnBrowse = new JButton("Browse");
	JFileChooser fc = new JFileChooser();
	boolean directoryMode;
	Component parent;

	public FileInput(String label, boolean directoryMode, Component parent) {
		this.parent = parent;
		lbl = new JLabel(label);
		
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onSelectFile(textField, true);
			}
		});
	}

	protected void onSelectFile(JTextField txtOutputDest, boolean b) {
		if (!StringUtils.isEmpty(textField.getText())) {
			fc.setSelectedFile(new File(textField.getText()));
		} else {
			fc.setSelectedFile(null);
		}
		int fileSelectionMode = directoryMode ? JFileChooser.DIRECTORIES_ONLY : JFileChooser.FILES_ONLY;

		fc.setFileSelectionMode(fileSelectionMode);
		int returnVal = fc.showOpenDialog(parent);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			textField.setText(file.getPath());
		}
	}

	public JLabel getLbl() {
		return lbl;
	}

	public JTextField getTextField() {
		return textField;
	}

	public JButton getBtnBrowse() {
		return btnBrowse;
	}
}
