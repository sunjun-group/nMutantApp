package nMutantApp;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class OutputFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel orgPane;
	private JPanel avePane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OutputFrame frame = new OutputFrame();
					frame.setVisible(true);
					frame.diplayResultImages("E:/lyly/Projects/nMutant/python/nMutant/mt_result/integration/blackbox/mnist/adv_1.53743143815e+12_1_7_.png", 
							"E:/lyly/Projects/nMutant/python/nMutant/datasets/integration/mnist/2.png");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public OutputFrame() {
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel leftPane = new JPanel();
		contentPane.add(leftPane);
		JLabel lblOriginalImage = new JLabel("Original Input");
		leftPane.add(lblOriginalImage);
		
		orgPane = new JPanel();
		leftPane.add(orgPane);
		
		JPanel rightPane = new JPanel();
		contentPane.add(rightPane);
		JLabel lblA = new JLabel("Aversary Output");
		rightPane.add(lblA);
		
		avePane = new JPanel();
		rightPane.add(avePane);
	}

	public void diplayResultImages(String orgImgPath, String aveImgPath) {
		try {
			if (orgImgPath != null) {
				BufferedImage bImg = ImageIO.read(new File(orgImgPath));
				Image img = bImg;
				if (bImg.getWidth() < 150) {
					img = bImg.getScaledInstance(bImg.getWidth() * 5, bImg.getHeight() * 5, Image.SCALE_DEFAULT);
				}
				ImageIcon icon = new ImageIcon(img);
				
				JLabel label = new JLabel(icon);
				orgPane.removeAll();
				orgPane.add(label);
			}
			if (aveImgPath != null) {
				BufferedImage bImg = ImageIO.read(new File(aveImgPath));
				Image img = bImg;
				if (bImg.getWidth() < 150) {
					img = bImg.getScaledInstance(bImg.getWidth() * 5, bImg.getHeight() * 5, Image.SCALE_DEFAULT);
				}
				ImageIcon icon = new ImageIcon(img);
				
				JLabel label = new JLabel(icon);
				avePane.removeAll();
				avePane.add(label);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
