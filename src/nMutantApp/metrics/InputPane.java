package nMutantApp.metrics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import nMutantApp.AttackFunction;
import nMutantApp.DataSet;
import nMutantApp.components.FileInput;
import nMutantApp.components.JComponentFactory;
import nMutantApp.components.Worker;
import nMutantApp.metrics.MetricsCalculator.MetricsOutputHandler;

public class InputPane extends JInternalFrame {
	private static final long serialVersionUID = 1L;
	private MetricsOutputHandler callback;

	JComboBox<DataSet> comboDataset;
	JTextField txtModel;
	JComboBox<AttackFunction> comboAttackFunction;
	JButton btnCalculMetrics = new JButton("d");
//	FileInput trainData;
//	FileInput testData;
	FileInput dataSetFolder;

	public InputPane(MetricsOutputHandler callback) {
		decorate();
		setVisible(true);
		this.callback = callback;
		registerHandler();
	}

	private void registerHandler() {
		btnCalculMetrics.setAction(new AbstractAction("Calculate Metrics") {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				btnCalculMetrics.setEnabled(false);
				MetricsCalculator calculator = new MetricsCalculator(callback, (DataSet) comboDataset.getSelectedItem(),
						(AttackFunction) comboAttackFunction.getSelectedItem(), txtModel.getText(), 
						/*trainData.getTextField().getText(), testData.getTextField().getText()*/
						dataSetFolder.getTextField().getText());
				Worker worker = new Worker(calculator);
				worker.addPropertyChangeListener(new PropertyChangeListener() {

					@Override
					public void propertyChange(PropertyChangeEvent evt) {
						if (Worker.PROPERTY_NAME.equals(evt.getPropertyName())
								&& Boolean.TRUE.equals(evt.getNewValue())) {
							btnCalculMetrics.setEnabled(true);
						}
					}
				});
				worker.execute();
			}

			@Override
			public void setEnabled(boolean newValue) {
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
		GridBagConstraints gbc = JComponentFactory.gbc(0, row, 1);
		gbc.insets = new Insets(30, 5, 5, 5);
		panel.add(lblInput_1, gbc);

		comboDataset = new JComboBox<>();
		comboDataset.setModel(new DefaultComboBoxModel<>(DataSet.values()));
		gbc = JComponentFactory.gbc(1, row, 2);
		gbc.insets = new Insets(30, 5, 5, 5);
		panel.add(comboDataset, gbc);

		/* model */
		row++;
		JLabel lblModel = new JLabel("Model Name");
		lblModel.setToolTipText("Model name");
		panel.add(lblModel, JComponentFactory.gbc(0, row, 1));

		txtModel = new JTextField();
		panel.add(txtModel, JComponentFactory.gbc(1, row, 2));

		/* attack function */
		row++;
		JLabel lblAttack = new JLabel("Attack Type");
		lblAttack.setToolTipText("Attack type");
		panel.add(lblAttack, JComponentFactory.gbc(0, row, 1));

		comboAttackFunction = new JComboBox<>(new DefaultComboBoxModel<>(AttackFunction.values()));
		panel.add(comboAttackFunction, JComponentFactory.gbc(1, row, 2));

//		/* train data */
//		row++;
//		trainData = new FileInput("Training Set", true, this);
//		panel.add(trainData.getLbl(), JComponentFactory.gbc(0, row, 1));
//		panel.add(trainData.getTextField(), JComponentFactory.gbc(1, row, 2));
//		row++;
//		gbc = JComponentFactory.gbc(2, row, 1);
//		gbc.insets = new Insets(0, 0, 0, 5);
//		panel.add(trainData.getBtnBrowse(), gbc);
//
//		/* test data */
//		row++;
//		testData = new FileInput("Testing Set", true, this);
//		panel.add(testData.getLbl(), JComponentFactory.gbc(0, row, 1));
//		panel.add(testData.getTextField(), JComponentFactory.gbc(1, row, 2));
//		row++;
//		gbc = JComponentFactory.gbc(2, row, 1);
//		gbc.insets = new Insets(0, 0, 0, 5);
//		panel.add(testData.getBtnBrowse(), gbc);
		
		/* data set folder */
		row++;
		dataSetFolder = new FileInput("DataSet folder", true, this);
		dataSetFolder.getLbl().setToolTipText(
				"DataSet folder must include all neccessary input data for calculating metrics, such as: train_data, test_data and its associated 'va' (??Pexin??) folders");
		panel.add(dataSetFolder.getLbl(), JComponentFactory.gbc(0, row, 1));
		panel.add(dataSetFolder.getTextField(), JComponentFactory.gbc(1, row, 2));
		row++;
		gbc = JComponentFactory.gbc(2, row, 1);
		gbc.insets = new Insets(0, 0, 0, 5);
		panel.add(dataSetFolder.getBtnBrowse(), gbc);

		row++;
		panel.add(new JSeparator(SwingConstants.HORIZONTAL), JComponentFactory.gbc(0, row++, 3));
		panel.add(btnCalculMetrics, JComponentFactory.gbc(1, row, 1));
	}

}
