package com.example.queuebasedplanning.Panels;

import java.util.LinkedHashMap;

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

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

import com.example.queuebasedplanning.QueueItem;
import com.example.queuebasedplanning.Windows.MainWindow;

public class AddItemPanel extends JPanel {

    //From parent window
	private LinkedHashMap<String, QueueItem> queueItems;
	private CardLayout contentPaneLayout;

    private JPanel infoEntryPanel;

    private JButton addButton;
    private JButton backToQueueButton;

    private JTextPane nameTextPane;
    private JTextPane detailsTextPane;

    public AddItemPanel(MainWindow parent) {
		contentPaneLayout = parent.getContentPaneLayout();
		queueItems = parent.getQueueItems();
        
        //Set up the panel
        setBounds(100, 100, 960, 540);
		setBackground(new Color(255, 246, 187));
		setBorder(new EmptyBorder(5, 5, 5, 5));
		
        //Content pane constraints
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		gbl_contentPane.columnWeights = new double[]{0.0};
        setLayout(gbl_contentPane);
        
        //Panels
        infoEntryPanel = new JPanel();
        GridBagConstraints gbc_infoEntryPanel = new GridBagConstraints();
        gbc_infoEntryPanel.weighty = 1.0;
        gbc_infoEntryPanel.insets = new Insets(0, 0, 5, 5);
        gbc_infoEntryPanel.fill = GridBagConstraints.BOTH;
        gbc_infoEntryPanel.gridx = 0;
        gbc_infoEntryPanel.gridy = 3;
        add(infoEntryPanel, gbc_infoEntryPanel);
        infoEntryPanel.setLayout(new GridLayout(2, 2, 0, 25));

        JPanel backToQueuePanel = new JPanel();
        GridBagConstraints gbc_backToQueuePanel = new GridBagConstraints();
        gbc_backToQueuePanel.weightx = 1.0;
        gbc_backToQueuePanel.anchor = GridBagConstraints.SOUTHEAST;
        gbc_backToQueuePanel.gridx = 0;
        gbc_backToQueuePanel.gridy = 7;
        add(backToQueuePanel, gbc_backToQueuePanel);
        
        //Labels
        JLabel titleLabel = new JLabel("Add New Item");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_titleLabel = new GridBagConstraints();
        gbc_titleLabel.insets = new Insets(0, 0, 5, 5);
        gbc_titleLabel.gridx = 0;
        gbc_titleLabel.gridy = 1;
        add(titleLabel, gbc_titleLabel);

        JLabel nameLabel = new JLabel("Name of Item:");
        JLabel detailsLabel = new JLabel("Details:");
        
        //Text panes
        nameTextPane = new JTextPane();
        nameTextPane.setPreferredSize(new Dimension(200, 50));
        
        detailsTextPane = new JTextPane();
        detailsTextPane.setPreferredSize(new Dimension(200, 50));
        
        //Add labels and text panes to infoEntryPanel
        infoEntryPanel.add(nameLabel);
        infoEntryPanel.add(nameTextPane);
        infoEntryPanel.add(detailsLabel);
        infoEntryPanel.add(detailsTextPane);
        
        //Buttons
        addButton = new JButton("Add");
        GridBagConstraints gbc_addButton = new GridBagConstraints();
        gbc_addButton.insets = new Insets(0, 0, 5, 5);
        gbc_addButton.gridx = 0;
        gbc_addButton.gridy = 5;
        add(addButton, gbc_addButton);

        addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
                queueItems = parent.getQueueItems();
				QueueItem newItem = new QueueItem(nameTextPane.getText(), detailsTextPane.getText());
				queueItems.put(newItem.name, newItem);
                parent.setQueueItems(queueItems);
                QueuePanel queuePanel = parent.getQueuePanel();
				queuePanel.updateQueueList();
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
			}
		});
        backToQueueButton = new JButton("Back to Queue");
        backToQueuePanel.add(backToQueueButton);
        
		backToQueueButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				contentPaneLayout.show(parent.getContentPane(), "Queue Panel");
                parent.setContentPaneLayout(contentPaneLayout);
			}
		});  
    }
}