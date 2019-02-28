package nMutantApp.components;

import java.awt.GridBagConstraints;
import java.awt.Insets;

public class JComponentFactory {
	
	public static GridBagConstraints gbc(int x, int y, int weightx, int weigthy) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.weightx = weightx;
		gbc.weighty = weigthy;
		gbc.gridx = x;
		gbc.gridy = y;
		return gbc;
	}
}
