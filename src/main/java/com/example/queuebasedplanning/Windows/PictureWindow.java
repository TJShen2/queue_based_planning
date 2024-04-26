package com.example.queuebasedplanning.Windows;

import java.awt.Dimension;
import java.awt.EventQueue;
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
import java.awt.CardLayout;

public class PictureWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PictureWindow frame = new PictureWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PictureWindow() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(0, 0, 1920, 1080);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);

		File imageDir = new File("./src/main/java/images");
		File[] images = imageDir.listFiles();
		int fileNumber = Math.round((float) Math.random()*images.length) - 1;

		Dimension maxDimensions = new Dimension(1536, 864);
		double scaleRatio;
		BufferedImage bufferedImage;
		try {
			bufferedImage = ImageIO.read(images[fileNumber]);
			
			Dimension bufferedImageDimensions = new Dimension(bufferedImage.getHeight(), bufferedImage.getWidth());
			scaleRatio = Math.min(maxDimensions.getHeight()/bufferedImageDimensions.getHeight(), maxDimensions.getWidth()/bufferedImageDimensions.getWidth());
			
			contentPane.setLayout(new CardLayout(0, 0));
			Image image = bufferedImage.getScaledInstance((int) Math.round(bufferedImage.getWidth()*scaleRatio), (int) Math.round(bufferedImage.getHeight()*scaleRatio), Image.SCALE_DEFAULT);
			JLabel imageLabel = new JLabel(new ImageIcon(image));
			contentPane.add(imageLabel, "name_289491710534840");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
