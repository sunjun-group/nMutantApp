package nMutantApp;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.NumberFormat;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import nMutantApp.NMutant.NMutantOutput;
import sav.common.core.SavException;
import sav.common.core.utils.StringUtils;

public class NMutantWindow extends JPanel {
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private NMutant nMutant = new NMutant();
	private JFileChooser fc;
	private JComboBox<AttackFunction> comboAttackFunction;
	private JComboBox<DataSet> comboDataset;
	private JTextField txtSample;
	private JFormattedTextField txtTarget;
	private JTextField txtModelPath;
	private JTextField txtOutputDest;
	private JFormattedTextField txtNbClasses;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NMutantWindow window = new NMutantWindow();
					window.frame.setVisible(true);
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
		NMutantParams defaultParams = NMutantParams.getDefault();
		comboAttackFunction.setSelectedItem(defaultParams.getAttackFunction());
		comboDataset.setSelectedItem(defaultParams.getDataset());
		txtSample.setText(defaultParams.getSamplePath());
		txtTarget.setValue(defaultParams.getTarget());
		txtModelPath.setText(defaultParams.getModelPath());
		txtOutputDest.setText(defaultParams.getStorePath());
		txtNbClasses.setValue(defaultParams.getNbClasses());
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		fc = new JFileChooser();

		frame = new JFrame("nMutant");
		frame.setBounds(100, 100, 596, 422);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblInput = new JLabel("Attack Function");
		lblInput.setBounds(23, 42, 116, 14);
		frame.getContentPane().add(lblInput);

		comboAttackFunction = new JComboBox<>();
		comboAttackFunction.setModel(new DefaultComboBoxModel<AttackFunction>(AttackFunction.values()));
		comboAttackFunction.setBounds(149, 39, 133, 20);
		frame.getContentPane().add(comboAttackFunction);

		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBounds(10, 70, 554, 238);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		/* Dataset */
		JLabel lblInput_1 = new JLabel("Dataset");
		lblInput_1.setToolTipText("The name of datasets");
		lblInput_1.setBounds(10, 20, 95, 14);
		panel.add(lblInput_1);

		comboDataset = new JComboBox<>();
		comboDataset.setModel(new DefaultComboBoxModel<>(DataSet.values()));
		comboDataset.setBounds(141, 17, 133, 20);
		panel.add(comboDataset);

		/* Sample */
		JLabel lblSample = new JLabel("Sample");
		lblSample.setBounds(10, 55, 121, 14);
		panel.add(lblSample);

		txtSample = new JTextField();
		txtSample.setBounds(141, 52, 286, 20);
		panel.add(txtSample);
		txtSample.setColumns(10);

		JButton btnBrowse = new JButton("Browse");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onSelectFile(txtSample, false);
			}
		});
		btnBrowse.setBounds(447, 51, 95, 23);
		panel.add(btnBrowse);

		/* Output Destination path */
		JLabel lblStorePath = new JLabel("Output Destination");
		lblStorePath.setBounds(10, 160, 121, 14);
		panel.add(lblStorePath);

		txtOutputDest = new JTextField();
		txtOutputDest.setBounds(141, 157, 286, 20);
		panel.add(txtOutputDest);
		txtOutputDest.setColumns(10);

		JButton btnOutputDest = new JButton("Browse");
		btnOutputDest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onSelectFile(txtOutputDest, true);
			}
		});
		btnOutputDest.setBounds(447, 156, 95, 23);
		panel.add(btnOutputDest);

		/* Model path */
		JLabel lblModel = new JLabel("Model");
		lblModel.setBounds(10, 125, 46, 14);
		panel.add(lblModel);

		txtModelPath = new JTextField();
		txtModelPath.setBounds(141, 122, 286, 20);
		panel.add(txtModelPath);
		txtModelPath.setColumns(10);

		JButton btnModelPath = new JButton("Browse");
		btnModelPath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onSelectFile(txtModelPath, true);
			}
		});
		btnModelPath.setBounds(447, 121, 95, 23);
		panel.add(btnModelPath);

		/* Target */
		JLabel lblTarget = new JLabel("Target");
		lblTarget.setBounds(10, 90, 46, 14);
		panel.add(lblTarget);

		txtTarget = new JFormattedTextField(NumberFormat.getNumberInstance());
		txtTarget.setBounds(141, 87, 86, 20);
		panel.add(txtTarget);
		txtTarget.setColumns(10);

		/* Nb_classes */
		JLabel lblNewLabel = new JLabel("Nb_classes");
		lblNewLabel.setBounds(10, 195, 86, 14);
		panel.add(lblNewLabel);

		txtNbClasses = new JFormattedTextField(NumberFormat.getNumberInstance());
		txtNbClasses.setBounds(141, 192, 86, 20);
		panel.add(txtNbClasses);
		txtNbClasses.setColumns(10);

		JButton btnRun = new JButton("Run");
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					NMutantOutput output = nMutant.run(initNMutantParams());
					
					EventQueue.invokeLater(new Runnable() {
						public void run() {
							try {
								OutputFrame frame = new OutputFrame();
								frame.setVisible(true);
								frame.diplayResultImages(
										output.getOrgImgPath(),
										output.getAveImgPath());
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
				} catch (SavException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		btnRun.setBounds(236, 330, 75, 23);
		frame.getContentPane().add(btnRun);
	}

	protected NMutantParams initNMutantParams() {
		NMutantParams params = new NMutantParams();
		params.setAttackFunction((AttackFunction) comboAttackFunction.getSelectedItem());
		params.setDataset((DataSet) comboDataset.getSelectedItem());
		params.setSamplePath(txtSample.getText());
		params.setTarget((int) txtTarget.getValue());
		params.setModelPath(txtModelPath.getText());
		params.setStorePath(txtOutputDest.getText());
		params.setNbClasses((int) txtNbClasses.getValue());
		return params;
	}

	private void onSelectFile(JTextField textField, boolean directoryMode) {
		if (!StringUtils.isEmpty(textField.getText())) {
			fc.setSelectedFile(new File(textField.getText()));
		} else {
			fc.setSelectedFile(null);
		}
		int fileSelectionMode = directoryMode ? JFileChooser.DIRECTORIES_ONLY : JFileChooser.FILES_ONLY;

		fc.setFileSelectionMode(fileSelectionMode);
		int returnVal = fc.showOpenDialog(NMutantWindow.this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			textField.setText(file.getPath());
		}
	}
}
