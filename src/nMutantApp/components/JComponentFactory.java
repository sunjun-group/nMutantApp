package nMutantApp.components;

import java.awt.GridBagConstraints;
import java.awt.Insets;

public class JComponentFactory {
	
	public static GridBagConstraints gbc(int x, int y, int gridWidth) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridwidth = gridWidth;
		gbc.gridx = x;
		gbc.gridy = y;
		return gbc;
	}
	
	public static GridBagConstraints gbc(int x, int y, int gridWidth, double weightx) {
		GridBagConstraints gbc = gbc(x, y, gridWidth);
		gbc.weightx = weightx;
		return gbc;
	}
}
