package com.example.queuebasedplanning.Panels;

import java.io.File;
import java.io.IOException;

import java.util.List;

import java.time.ZonedDateTime;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.awt.Image;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

import com.example.queuebasedplanning.QueueItem;
import com.example.queuebasedplanning.Windows.MainWindow;

import javax.imageio.ImageIO;

public class AddItemPanel extends JPanel {

    //From parent window
	private List<QueueItem> queueItems;
    private JPanel contentPane;
	private CardLayout contentPaneLayout;

    //Window components
    //Panels
    private JPanel infoEntryPanel;

    //Buttons
    private JButton addButton;
    private JButton cancelButton;

    //Text panes
    private JTextPane nameTextPane;
    private JTextPane detailsTextPane;

    public AddItemPanel(MainWindow parent) {
        contentPane = parent.getContentPane();
		contentPaneLayout = parent.getContentPaneLayout();
		queueItems = parent.getCurrentQueueItems();
        
        //Set up the main panel
        setBounds(100, 100, 960, 540);
		setBackground(new Color(255, 246, 187));
		setBorder(new EmptyBorder(5, 5, 5, 5));
		
        //Content pane constraints
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		gbl_contentPane.columnWeights = new double[]{0.0};
        setLayout(gbl_contentPane);
        
        //Set up panels
        infoEntryPanel = new JPanel();
        GridBagConstraints gbc_infoEntryPanel = new GridBagConstraints();
        gbc_infoEntryPanel.weighty = 1.0;
        gbc_infoEntryPanel.insets = new Insets(0, 0, 5, 5);
        gbc_infoEntryPanel.fill = GridBagConstraints.BOTH;
        gbc_infoEntryPanel.gridx = 0;
        gbc_infoEntryPanel.gridy = 3;
        infoEntryPanel.setLayout(new GridLayout(2, 2, 0, 25));

        JPanel cancelPanel = new JPanel();
        GridBagConstraints gbc_cancelPanel = new GridBagConstraints();
        gbc_cancelPanel.weightx = 1.0;
        gbc_cancelPanel.anchor = GridBagConstraints.SOUTHEAST;
        gbc_cancelPanel.gridx = 0;
        gbc_cancelPanel.gridy = 7;
        
        //Set up labels
        JLabel titleLabel = new JLabel("Add New Item");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_titleLabel = new GridBagConstraints();
        gbc_titleLabel.insets = new Insets(0, 0, 5, 5);
        gbc_titleLabel.gridx = 0;
        gbc_titleLabel.gridy = 1;

        JLabel nameLabel = new JLabel("Name of Item:");
        JLabel detailsLabel = new JLabel("Details:");
        
        //Text panes
        nameTextPane = new JTextPane();
        nameTextPane.setPreferredSize(new Dimension(200, 50));
        
        detailsTextPane = new JTextPane();
        detailsTextPane.setPreferredSize(new Dimension(200, 50));
        
        //Buttons
        addButton = new JButton("Add");
        GridBagConstraints gbc_addButton = new GridBagConstraints();
        gbc_addButton.insets = new Insets(0, 0, 5, 5);
        gbc_addButton.gridx = 0;
        gbc_addButton.gridy = 5;
        
        addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
                queueItems = parent.getCurrentQueueItems();
				QueueItem newItem = new QueueItem(nameTextPane.getText(), detailsTextPane.getText(), ZonedDateTime.now());
				queueItems.add(newItem);
                parent.setCurrentQueueItems(queueItems);

                QueuePanel queuePanel = parent.getQueuePanel();
				queuePanel.updateQueueList();
                queuePanel.updateButtonStates();
                parent.saveChanges();
                parent.setQueuePanel(queuePanel);

				Component[] infoEntryPanelComponents = infoEntryPanel.getComponents();

                int i = 0;
				for (Component comp : infoEntryPanelComponents) {
                    if (i % 2 != 0) {
                        try {
                            JTextPane textComponent = (JTextPane) comp;
                            textComponent.setText("");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    i++;
				}
                BufferedImage bufferedImage = null;
                try {
                    bufferedImage = ImageIO.read(new File("./src/main/java/images/5.jpeg"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Image image = bufferedImage.getScaledInstance(654, 980, Image.SCALE_DEFAULT);
                ImageIcon imageIcon = new ImageIcon(image, "wow");
                
                JOptionPane successPanel = new JOptionPane("Sucessfully added the item!", JOptionPane.INFORMATION_MESSAGE, JOptionPane.YES_NO_CANCEL_OPTION, imageIcon);
                successPanel.setMaximumSize(new Dimension(1920, 1080));
                JOptionPane.showInputDialog(successPanel);
                
                SuccessfullyAddedItemPanel messagePanel = new SuccessfullyAddedItemPanel(newItem.getName(), parent);

                contentPane.add(messagePanel);
                contentPaneLayout.addLayoutComponent(messagePanel, "Message Panel");
                contentPaneLayout.show(contentPane, "Message Panel");
			}
		});

        cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				contentPaneLayout.show(parent.getContentPane(), "Queue Panel");
                parent.setContentPaneLayout(contentPaneLayout);
			}
		});

        //Add widgets (buttons, labels, and text panes) to panels
        add(titleLabel, gbc_titleLabel);
        add(addButton, gbc_addButton);

        cancelPanel.add(cancelButton);
        infoEntryPanel.add(nameLabel);
        infoEntryPanel.add(nameTextPane);
        infoEntryPanel.add(detailsLabel);
        infoEntryPanel.add(detailsTextPane);

        //Add top-level containers
        add(cancelPanel, gbc_cancelPanel);
        add(infoEntryPanel, gbc_infoEntryPanel);
    }
}