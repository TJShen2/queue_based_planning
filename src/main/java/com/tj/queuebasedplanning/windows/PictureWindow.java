package com.tj.queuebasedplanning.windows;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Toolkit;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

public class PictureWindow extends JFrame {

	private JScrollPane contentScrollPane;
	private JPanel contentPane;
	private JLabel imageLabel = new JLabel();

	private File imageDir = new File("images");
	private File[] images = imageDir.listFiles();
	private int fileNumber = 0;
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	/**
	 * Create the frame.
	 */
	public PictureWindow() {
		this.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
			}
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					nextPicture(-1);
				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					nextPicture(1);
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
				throw new UnsupportedOperationException("Unimplemented method 'keyReleased'");
			}
		});
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(screenSize);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		CardLayout contentPaneLayout = new CardLayout();
		contentPane.setLayout(contentPaneLayout);

		contentScrollPane = new JScrollPane();

		displayPicture(images, fileNumber);

		imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		imageLabel.setMaximumSize(screenSize);

		contentPane.add(imageLabel);

		contentScrollPane.setViewportView(contentPane);
		setContentPane(contentScrollPane);
	}

	private void displayPicture(File[] images, int fileNumber) {
		try {
			BufferedImage bufferedImage = ImageIO.read(images[fileNumber]);

			Dimension bufferedImageDimensions = new Dimension(bufferedImage.getHeight(), bufferedImage.getWidth());
			double scaleRatio = Math.min(screenSize.getHeight()/bufferedImageDimensions.getWidth(), screenSize.getWidth()/bufferedImageDimensions.getHeight());

			Image image = bufferedImage.getScaledInstance((int) Math.round(bufferedImage.getWidth() * scaleRatio), (int) Math.round((bufferedImage.getHeight()) * scaleRatio), Image.SCALE_DEFAULT);
			imageLabel.setIcon(new ImageIcon(image));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void nextPicture(int positionChange) {
		if (fileNumber + positionChange < images.length && fileNumber + positionChange > 0) {
			fileNumber += positionChange;
			displayPicture(images, fileNumber);
		}
	}
}
