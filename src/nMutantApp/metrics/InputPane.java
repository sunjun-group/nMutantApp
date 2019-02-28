package nMutantApp.metrics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import nMutantApp.AttackFunction;
import nMutantApp.DataSet;
import nMutantApp.components.JComponentFactory;
import nMutantApp.components.Worker;
import nMutantApp.metrics.MetricsCalculator.MetricsOutputHandler;

public class InputPane extends JInternalFrame {
	private static final long serialVersionUID = 1L;
	JComboBox<DataSet> comboDataset;
	JTextField txtModel;
	JComboBox<AttackFunction> comboAttackFunction;
	JButton btnCalculMetrics = new JButton("Calculate Metrics");
	private MetricsOutputHandler callback;

	public InputPane(MetricsOutputHandler callback) {
		decorate();
		setVisible(true);
		this.callback = callback;
		registerHandler();
	}
	
	private void registerHandler() {
		btnCalculMetrics.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				btnCalculMetrics.setEnabled(false);
				MetricsCalculator calculator = new MetricsCalculator((DataSet)comboDataset.getSelectedItem(), 
						(AttackFunction)comboAttackFunction.getSelectedItem(), 
						txtModel.getText(), callback);
				Worker worker = new Worker(calculator);
				worker.addPropertyChangeListener(new PropertyChangeListener() {
					
					@Override
					public void propertyChange(PropertyChangeEvent evt) {
						btnCalculMetrics.setEnabled(true);
					}
				});
				worker.execute();
			}
		});
	}

	private void decorate() {
		setTitle("Input");
		getContentPane().setLayout(new BorderLayout());
		setBorder(new LineBorder(new Color(0, 0, 0)));
		
		GridBagLayout gb = new GridBagLayout();
		JPanel panel = new JPanel(gb);
		getContentPane().add(panel, BorderLayout.NORTH);
		
		/* Dataset */
		int row = 0;
		JLabel lblInput_1 = new JLabel("Dataset");
		lblInput_1.setToolTipText("Name of Target Datasets");
		panel.add(lblInput_1, JComponentFactory.gbc(0, row, 1, 1));
		
		comboDataset = new JComboBox<>();
		comboDataset.setModel(new DefaultComboBoxModel<>(DataSet.values()));
		panel.add(comboDataset, JComponentFactory.gbc(1, row, 3, 1));
		
		/* model */
		row++;
		JLabel lblModel = new JLabel("Model Name");
		lblModel.setToolTipText("Model name");
		panel.add(lblModel, JComponentFactory.gbc(0, row, 1, 1));
		
		txtModel = new JTextField();
		panel.add(txtModel, JComponentFactory.gbc(1, row, 3, 1));
		
		/* attack function */
		row++;
		JLabel lblAttack = new JLabel("Attack Type");
		lblAttack.setToolTipText("Attack type");
		panel.add(lblAttack, JComponentFactory.gbc(0, row, 1, 1));
		
		comboAttackFunction = new JComboBox<>(new DefaultComboBoxModel<>(AttackFunction.values()));
		panel.add(comboAttackFunction, JComponentFactory.gbc(1, row, 3, 1));
		
		/* button */
		row++;
		panel.add(btnCalculMetrics, JComponentFactory.gbc(1, row, 1, 1));
	}
	
}
