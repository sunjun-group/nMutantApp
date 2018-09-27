package nMutantApp;

import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
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
				displayImage(orgPane, orgImgPath);
			}
			if (aveImgPath != null) {
				displayImage(avePane, aveImgPath);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void displayImage(JPanel panel, String imgPath) throws IOException {
		BufferedImage bImg = ImageIO.read(new File(imgPath));
		Image img = bImg;
		if (bImg.getWidth() < 150) {
			img = bImg.getScaledInstance(bImg.getWidth() * 5, bImg.getHeight() * 5, Image.SCALE_DEFAULT);
		}
		ImageIcon icon = new ImageIcon(img);
		
		JLabel label = new JLabel(icon);
		label.setToolTipText(imgPath);
		panel.removeAll();
		panel.add(label);

		label.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e){
		        if (e.isPopupTrigger())
		            doPop(e);
		    }

		    public void mouseReleased(MouseEvent e){
		        if (e.isPopupTrigger())
		            doPop(e);
		    }

		    private void doPop(MouseEvent e){
		        ImagePopUpMenu menu = new ImagePopUpMenu(imgPath);
		        menu.show(e.getComponent(), e.getX(), e.getY());
		    }
		});
	}
	
	class ImagePopUpMenu extends JPopupMenu {
		private static final long serialVersionUID = 1L;
		JMenuItem anItem;
	    public ImagePopUpMenu(String path){
	        anItem = new JMenuItem("Open File");
	        add(anItem);
	        anItem.addMouseListener(new MouseAdapter() {
				public void mouseReleased(MouseEvent e) {
					Desktop desktop = null;
					File file = new File(path);
					try {
						if (Desktop.isDesktopSupported()) {
							desktop = Desktop.getDesktop();
							desktop.browse(file.toURI());
						} else {
							System.out.println("desktop is not supported");
						}
					} catch (IOException ex) {
						
					}
				}

			});
	    }
	}

}
